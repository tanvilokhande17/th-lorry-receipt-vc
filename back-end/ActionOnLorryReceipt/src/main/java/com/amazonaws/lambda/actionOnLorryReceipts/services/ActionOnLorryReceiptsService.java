package com.amazonaws.lambda.actionOnLorryReceipts.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.actionOnLorryReceipt.configs.ApplicationProperties;
import com.amazonaws.lambda.actionOnLorryReceipt.constants.Constants;
import com.amazonaws.lambda.actionOnLorryReceipts.models.daos.LorryReceipt;
import com.amazonaws.lambda.actionOnLorryReceipts.models.daos.LorryReceiptUserRole;
import com.amazonaws.lambda.actionOnLorryReceipts.models.daos.User;
import com.amazonaws.lambda.actionOnLorryReceipts.models.daos.UserRole;
import com.amazonaws.lambda.actionOnLorryReceipts.models.entities.ActionOnLorryReceiptVerificationCheckDetails;
import com.amazonaws.lambda.actionOnLorryReceipts.models.requests.ActionOnLorryReceiptRequest;
import com.amazonaws.lambda.actionOnLorryReceipts.models.requests.ActionOnLorryReceiptRequestBody;
import com.amazonaws.lambda.actionOnLorryReceipts.models.requests.AffinidiStoreVCRequest;
import com.amazonaws.lambda.actionOnLorryReceipts.models.responses.ActionOnLorryReceiptResponse;
import com.amazonaws.lambda.actionOnLorryReceipts.repositories.LorryReceiptRepository;
import com.amazonaws.lambda.actionOnLorryReceipts.repositories.LorryReceiptUserRoleRepository;
import com.amazonaws.lambda.actionOnLorryReceipts.repositories.UserRepository;
import com.amazonaws.lambda.actionOnLorryReceipts.repositories.UserRoleRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionOnLorryReceiptsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionOnLorryReceiptsService.class);

	private static String affinidiUrlStore = ApplicationProperties.getProperty("affinidi.url.store");
	private static String affinidiApikeyHeader = ApplicationProperties.getProperty("affinidi.apikey.header");
	private static String affinidiApikeyValue = ApplicationProperties.getProperty("affinidi.apikey.value");

	private UserRepository userRepository = new UserRepository();
	private UserRoleRepository userRoleRepository = new UserRoleRepository();
	private LorryReceiptRepository lorryReceiptRepository = new LorryReceiptRepository();
	private LorryReceiptUserRoleRepository lorryReceiptUserRoleRepository = new LorryReceiptUserRoleRepository();

	ObjectMapper mapper = new ObjectMapper();

	public ActionOnLorryReceiptResponse processActionOnLorryReceiptRequest(ActionOnLorryReceiptRequest actionOnLorryReceiptRequest) {

		ActionOnLorryReceiptResponse actionOnLorryReceiptResponse = new ActionOnLorryReceiptResponse();
		try {
			ActionOnLorryReceiptRequestBody actionOnLorryReceiptRequestBody = actionOnLorryReceiptRequest.getBody();
			Map<String, String> headers = actionOnLorryReceiptRequest.getHeaders();
			String action = actionOnLorryReceiptRequestBody.getAction().toUpperCase();
			String role = actionOnLorryReceiptRequestBody.getRole().toUpperCase();

			String message = checkRoleForAction(role, action);
			if (message.equals("")) {
				String mobileNumber = getMobileNumberFromAuthToken(headers);

				ActionOnLorryReceiptVerificationCheckDetails actionOnLorryReceiptVerificationCheckDetails = checkActionOnLorryReceiptVerification(mobileNumber, role, actionOnLorryReceiptRequestBody.getLorryReceiptId());
				message = actionOnLorryReceiptVerificationCheckDetails.getMessage();
				if (message.equals("")) {

					LorryReceipt lorryReceipt = actionOnLorryReceiptVerificationCheckDetails.getLorryReceipt();
					message = checkStatusForAction(role, action, lorryReceipt.getStatus().toUpperCase());
					if (message.equals("")) {

						message = doAction(action, role, lorryReceipt, actionOnLorryReceiptVerificationCheckDetails.getLorryReceiptUserRole(), headers.get(Constants.REQUEST_HEADER_AUTHORIZATION));
						if (message.equals("")) {
							message = "Action performed successfully";
						}
					}
					actionOnLorryReceiptResponse.setActionPayload(actionOnLorryReceiptRequestBody);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			actionOnLorryReceiptResponse.setMessage(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			actionOnLorryReceiptResponse.setMessage(e.getMessage());
		}
		return actionOnLorryReceiptResponse;
	}

	private String getMobileNumberFromAuthToken(Map<String, String> headers) throws JsonProcessingException {

		Map<String, String> payloadMap = getPayloadMap(headers.get(Constants.REQUEST_HEADER_AUTHORIZATION).replace("Bearer ", ""));
		LOGGER.info("payloadMap :- {}", mapper.writeValueAsString(payloadMap));
		return payloadMap.get("username");
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getPayloadMap(String jwtToken) throws JsonProcessingException {

		DecodedJWT decodedJWT = JWT.decode(jwtToken);
		LOGGER.info("decodedJWT :- {}", mapper.writeValueAsString(decodedJWT));

		Base64 base64Url = new Base64(true);
		String payload = new String(base64Url.decode(decodedJWT.getPayload()));
		LOGGER.info("payload :- {}", payload);

		return mapper.readValue(payload, HashMap.class);
	}

	private String checkRoleForAction(String role, String action) {

		if (Constants.LORRYRECEIPT_ACTION_CANCELLED.equals(action) && !(Constants.USER_ROLE_TRANSPORTER.equals(role) || Constants.USER_ROLE_CONSIGNER.equals(role))) {
			return role + " role not expected for " + action + " action.";
		}
		if (Constants.LORRYRECEIPT_ACTION_PICKUP.equals(action) && !(Constants.USER_ROLE_CONSIGNER.equals(role) || Constants.USER_ROLE_DRIVER.equals(role))) {
			return role + " role not expected for " + action + " action.";
		}
		if (Constants.LORRYRECEIPT_ACTION_DELAYED.equals(action) && !Constants.USER_ROLE_DRIVER.equals(role)) {
			return role + " role not expected for " + action + " action.";
		}
		if (Constants.LORRYRECEIPT_ACTION_INTRANSIT.equals(action) && !Constants.USER_ROLE_DRIVER.equals(role)) {
			return role + " role not expected for " + action + " action.";
		}
		if (Constants.LORRYRECEIPT_ACTION_DELIVERY.equals(action) && !(Constants.USER_ROLE_DRIVER.equals(role) || Constants.USER_ROLE_CONSIGNEE.equals(role))) {
			return role + " role not expected for " + action + " action.";
		}
		if (Constants.LORRYRECEIPT_ACTION_DECLINED.equals(action) && !Constants.USER_ROLE_CONSIGNEE.equals(role)) {
			return role + " role not expected for " + action + " action.";
		}
		return "";
	}

	private ActionOnLorryReceiptVerificationCheckDetails checkActionOnLorryReceiptVerification(String mobileNumber, String role, int lorryReceiptId) throws SQLException, JsonProcessingException {

		ActionOnLorryReceiptVerificationCheckDetails actionOnLorryReceiptVerificationCheckDetails = new ActionOnLorryReceiptVerificationCheckDetails();
		User loggedInUser = userRepository.getUserByMobileNumber(mobileNumber);
		LOGGER.info("loggedInUser :- {}", mapper.writeValueAsString(loggedInUser));
		if (loggedInUser != null) {

			UserRole loggedInUserRole = userRoleRepository.getUserRoleByUserIdAndRole(loggedInUser.getId(), role);
			LOGGER.info("loggedInUserRole :- {}", mapper.writeValueAsString(loggedInUserRole));
			if (loggedInUserRole != null) {

				LorryReceipt lorryReceipt = lorryReceiptRepository.getLorryReceiptById(lorryReceiptId);
				LOGGER.info("lorryReceipt :- {}", mapper.writeValueAsString(lorryReceipt));
				if (lorryReceipt != null) {

					LorryReceiptUserRole lorryReceiptUserRole = lorryReceiptUserRoleRepository.getLorryReceiptUserRolesByUserRoleIdAndReceiptId(loggedInUserRole.getId(), lorryReceipt.getId());
					LOGGER.info("lorryReceiptUserRole :- {}", mapper.writeValueAsString(lorryReceiptUserRole));
					if (lorryReceiptUserRole != null) {
						actionOnLorryReceiptVerificationCheckDetails.setLorryReceipt(lorryReceipt);
						actionOnLorryReceiptVerificationCheckDetails.setLorryReceiptUserRole(lorryReceiptUserRole);
						actionOnLorryReceiptVerificationCheckDetails.setMessage("");
					} else {
						actionOnLorryReceiptVerificationCheckDetails.setMessage(role + " LorryReceiptUserRole not found for lorryReceiptId :- " + lorryReceiptId + ", mobileNumber :- " + mobileNumber);
					}
				} else {
					actionOnLorryReceiptVerificationCheckDetails.setMessage("LorryReceipt not found for id :- " + lorryReceiptId);
				}
			} else {
				actionOnLorryReceiptVerificationCheckDetails.setMessage(role + " Role not found for mobileNumber :- " + mobileNumber);
			}
		} else {
			actionOnLorryReceiptVerificationCheckDetails.setMessage("User not found for mobileNumber :- " + mobileNumber);
		}

		return actionOnLorryReceiptVerificationCheckDetails;
	}

	private String checkStatusForAction(String role, String action, String status) {

		if (Constants.LORRYRECEIPT_ACTION_CANCELLED.equals(action) && !Constants.LORRYRECEIPT_STATUS_BOOKED.equals(status)) {
			return action + " not allowed at " + status + " status.";
		}
		if (Constants.LORRYRECEIPT_ACTION_PICKUP.equals(action) && Constants.USER_ROLE_CONSIGNER.equals(role) && !Constants.LORRYRECEIPT_STATUS_BOOKED.equals(status)) {
			return role + " " + action + " not allowed at " + status + " status.";
		}
		if (Constants.LORRYRECEIPT_ACTION_PICKUP.equals(action) && Constants.USER_ROLE_DRIVER.equals(role) && !Constants.LORRYRECEIPT_STATUS_PICKEDUP.equals(status)) {
			return role + " " + action + " not allowed at " + status + " status.";
		}
		if (Constants.LORRYRECEIPT_ACTION_DELAYED.equals(action) && !Constants.LORRYRECEIPT_STATUS_INTRANSIT.equals(status)) {
			return action + " not allowed at " + status + " status.";
		}
		if (Constants.LORRYRECEIPT_ACTION_INTRANSIT.equals(action) && !Constants.LORRYRECEIPT_STATUS_DELAYED.equals(status)) {
			return action + " not allowed at " + status + " status.";
		}
		if (Constants.LORRYRECEIPT_ACTION_DELIVERY.equals(action) && Constants.USER_ROLE_DRIVER.equals(role) && !Constants.LORRYRECEIPT_STATUS_INTRANSIT.equals(status)) {
			return role + " " + action + " not allowed at " + status + " status.";
		}
		if (Constants.LORRYRECEIPT_ACTION_DELIVERY.equals(action) && Constants.USER_ROLE_CONSIGNEE.equals(role) && !Constants.LORRYRECEIPT_STATUS_DELIVERED.equals(status)) {
			return role + " " + action + " not allowed at " + status + " status.";
		}
		if (Constants.LORRYRECEIPT_ACTION_DECLINED.equals(action) && !Constants.LORRYRECEIPT_STATUS_DELIVERED.equals(status)) {
			return action + " not allowed at " + status + " status.";
		}
		return "";
	}

	private String doAction(String action, String role, LorryReceipt lorryReceipt, LorryReceiptUserRole lorryReceiptUserRole, String authToken) throws SQLException, IOException {

		String message = "";
		switch (action) {
		case Constants.LORRYRECEIPT_ACTION_APPROVE:
			approveLorryReceipt(lorryReceiptUserRole, authToken);
			break;

		case Constants.LORRYRECEIPT_ACTION_CANCELLED:
			cancelLorryReceipt(lorryReceipt);
			break;

		case Constants.LORRYRECEIPT_ACTION_PICKUP:
			markPickUpLorryReceipt(lorryReceipt, role);
			break;

		case Constants.LORRYRECEIPT_ACTION_DELAYED:
			markDelayLorryReceipt(lorryReceipt);
			break;

		case Constants.LORRYRECEIPT_ACTION_INTRANSIT:
			markInTransitLorryReceipt(lorryReceipt);
			break;

		case Constants.LORRYRECEIPT_ACTION_DELIVERY:
			markDeliveryLorryReceipt(lorryReceipt, role);
			break;

		case Constants.LORRYRECEIPT_ACTION_DECLINED:
			markDeclinedLorryReceipt(lorryReceipt);
			break;

		default:
			message = "Invalid action :- " + action;
			break;
		}
		return message;
	}

	private void approveLorryReceipt(LorryReceiptUserRole lorryReceiptUserRole, String authToken) throws IOException, SQLException {

		LOGGER.info("Fetching VC from URL");
		String output = getHttpCall(lorryReceiptUserRole.getVcUrl());

		LOGGER.info("Storing VC");
		List<JsonNode> affinidiSignedVCs = new ArrayList<JsonNode>();
		affinidiSignedVCs.add(mapper.readTree(output));
		AffinidiStoreVCRequest affinidiStoreVCRequest = new AffinidiStoreVCRequest();
		affinidiStoreVCRequest.setData(affinidiSignedVCs);
		LOGGER.info("AffinidiStoreVCRequest :- {}", mapper.writeValueAsString(affinidiStoreVCRequest));
		output = postHttpCall(affinidiUrlStore, mapper.writeValueAsString(affinidiStoreVCRequest), authToken);

		lorryReceiptUserRole.setVCStored(true);
		lorryReceiptUserRole.setVcUrl(null);
		lorryReceiptUserRoleRepository.updateLorryReceiptUserRole(lorryReceiptUserRole);
	}

	private void cancelLorryReceipt(LorryReceipt lorryReceipt) throws SQLException, JsonProcessingException {

		lorryReceipt.setStatus(Constants.LORRYRECEIPT_STATUS_CANCELLED);
		lorryReceiptRepository.updateLorryReceipt(lorryReceipt);
	}

	private void markPickUpLorryReceipt(LorryReceipt lorryReceipt, String role) throws SQLException, JsonProcessingException {

		if (role.equals(Constants.USER_ROLE_CONSIGNER)) {
			lorryReceipt.setStatus(Constants.LORRYRECEIPT_STATUS_PICKEDUP);
			lorryReceipt.setPickUpConsignerSignatureTime(new Date());
		}
		if (role.equals(Constants.USER_ROLE_DRIVER)) {
			lorryReceipt.setStatus(Constants.LORRYRECEIPT_STATUS_INTRANSIT);
			lorryReceipt.setPickUpDriverSignatureTime(new Date());
		}
		lorryReceiptRepository.updateLorryReceipt(lorryReceipt);
	}

	private void markDelayLorryReceipt(LorryReceipt lorryReceipt) throws SQLException, JsonProcessingException {

		lorryReceipt.setStatus(Constants.LORRYRECEIPT_STATUS_DELAYED);
		lorryReceiptRepository.updateLorryReceipt(lorryReceipt);
	}

	private void markInTransitLorryReceipt(LorryReceipt lorryReceipt) throws SQLException, JsonProcessingException {

		lorryReceipt.setStatus(Constants.LORRYRECEIPT_STATUS_INTRANSIT);
		lorryReceiptRepository.updateLorryReceipt(lorryReceipt);
	}

	private void markDeliveryLorryReceipt(LorryReceipt lorryReceipt, String role) throws SQLException, JsonProcessingException {

		if (role.equals(Constants.USER_ROLE_DRIVER)) {
			lorryReceipt.setStatus(Constants.LORRYRECEIPT_STATUS_DELIVERED);
			lorryReceipt.setDeliveryDriverSignatureTime(new Date());
		}
		if (role.equals(Constants.USER_ROLE_CONSIGNEE)) {
			lorryReceipt.setStatus(Constants.LORRYRECEIPT_STATUS_COMPLETED);
			lorryReceipt.setDeliveryConsigneeSignatureTime(new Date());
		}
		lorryReceiptRepository.updateLorryReceipt(lorryReceipt);
	}

	private void markDeclinedLorryReceipt(LorryReceipt lorryReceipt) throws SQLException, JsonProcessingException {

		lorryReceipt.setStatus("DECLINED");
		lorryReceiptRepository.updateLorryReceipt(lorryReceipt);
	}

	private String getHttpCall(String httpURL) throws IOException {

		LOGGER.info("getHttpCall, httpURL :- {}", httpURL);

		URL url = new URL(httpURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(Constants.API_METHOD_GET);
		conn.setRequestProperty(Constants.REQUEST_HEADER_ACCEPT, Constants.CONTENT_TYPE_APPLICATION_JSON);

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		StringBuilder builder = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			builder.append(line).append(System.lineSeparator());
			line = br.readLine();
		}
		br.close();
		String output = builder.toString();
		LOGGER.info("Output from Server :- {}", output);

		conn.disconnect();
		return output;
	}

	private String postHttpCall(String httpURL, String inputJson, String authToken) throws IOException {

		LOGGER.info("postHttpCall, httpURL :- {}, inputJson :- {}", httpURL, inputJson);

		URL url = new URL(httpURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(Constants.API_METHOD_POST);
		conn.setRequestProperty(Constants.REQUEST_HEADER_ACCEPT, Constants.CONTENT_TYPE_APPLICATION_JSON);
		conn.setRequestProperty(Constants.REQUEST_HEADER_CONTENT_TYPE, Constants.CONTENT_TYPE_APPLICATION_JSON);
		conn.setRequestProperty(affinidiApikeyHeader, affinidiApikeyValue);
		if (authToken != null) {
			conn.setRequestProperty(Constants.REQUEST_HEADER_AUTHORIZATION, authToken);
		}

		OutputStream os = conn.getOutputStream();
		os.write(inputJson.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		StringBuilder builder = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			builder.append(line).append(System.lineSeparator());
			line = br.readLine();
		}
		br.close();
		String output = builder.toString();
		LOGGER.info("Output from Server :- {}", output);

		conn.disconnect();
		return output;
	}
}