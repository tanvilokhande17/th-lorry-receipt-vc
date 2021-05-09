package com.amazonaws.lambda.getlorryreceipts.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.getlorryreceipts.LambdaFunctionHandler;
import com.amazonaws.lambda.getlorryreceipts.constants.Constants;
import com.amazonaws.lambda.getlorryreceipts.models.daos.LorryReceipt;
import com.amazonaws.lambda.getlorryreceipts.models.daos.LorryReceiptUserRole;
import com.amazonaws.lambda.getlorryreceipts.models.daos.User;
import com.amazonaws.lambda.getlorryreceipts.models.daos.UserRole;
import com.amazonaws.lambda.getlorryreceipts.models.entities.LoggedInUserCheckDetails;
import com.amazonaws.lambda.getlorryreceipts.models.requests.GetLorryReceiptsRequest;
import com.amazonaws.lambda.getlorryreceipts.models.responses.GetLorryReceiptsResponse;
import com.amazonaws.lambda.getlorryreceipts.models.responses.GetLorryReceiptsResponseDetails;
import com.amazonaws.lambda.getlorryreceipts.repositories.LorryReceiptRepository;
import com.amazonaws.lambda.getlorryreceipts.repositories.LorryReceiptUserRoleRepository;
import com.amazonaws.lambda.getlorryreceipts.repositories.UserRepository;
import com.amazonaws.lambda.getlorryreceipts.repositories.UserRoleRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetLorryReceiptsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private UserRepository userRepository = new UserRepository();
	private UserRoleRepository userRoleRepository = new UserRoleRepository();
	private LorryReceiptRepository lorryReceiptRepository = new LorryReceiptRepository();
	private LorryReceiptUserRoleRepository lorryReceiptUserRoleRepository = new LorryReceiptUserRoleRepository();

	ObjectMapper mapper = new ObjectMapper();

	public GetLorryReceiptsResponse processGetLorryReceiptsRequest(GetLorryReceiptsRequest getLorryReceiptsRequest) {

		GetLorryReceiptsResponse getLorryReceiptsResponse = new GetLorryReceiptsResponse();
		try {
			Map<String, String> headers = getLorryReceiptsRequest.getHeader();
			Map<String, String> params = getLorryReceiptsRequest.getQuerystring();

			Map<String, String> payloadMap = getPayloadMap(headers.get(Constants.REQUEST_HEADER_AUTHORIZATION).replace("Bearer ", ""));
			LOGGER.info("payloadMap :- {}", mapper.writeValueAsString(payloadMap));

			String mobileNumber = payloadMap.get("username");
			String role = params.get("role");

			LoggedInUserCheckDetails loggedInUserCheckDetails = checkLoggedInUser(mobileNumber, role);
			LOGGER.info("loggedInUserCheckDetails :- {}", mapper.writeValueAsString(loggedInUserCheckDetails));

			String message = loggedInUserCheckDetails.getMessage();
			User loggedInUser = loggedInUserCheckDetails.getLoggedInUser();
			UserRole loggedInUserRole = loggedInUserCheckDetails.getLoggedInUserRole();

			if (message.equals("")) {
				List<GetLorryReceiptsResponseDetails> getLorryReceiptsResponseDetailsList = getLorryReceiptDetailsList(loggedInUser, loggedInUserRole);
				LOGGER.info("getLorryReceiptsResponseDetailsList :- {}", mapper.writeValueAsString(getLorryReceiptsResponseDetailsList));

				getLorryReceiptsResponse.setLorryReceipts(getLorryReceiptsResponseDetailsList);

			} else {
				LOGGER.info(message);
				getLorryReceiptsResponse.setErrorMessage(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			getLorryReceiptsResponse.setErrorMessage(e.getMessage());

		} catch (IOException e) {
			e.printStackTrace();
			getLorryReceiptsResponse.setErrorMessage(e.getMessage());
		}

		return getLorryReceiptsResponse;
	}

	private LoggedInUserCheckDetails checkLoggedInUser(String mobileNumber, String role) throws SQLException, JsonProcessingException {

		String message = "";
		UserRole loggedInUserRole = null;
		User loggedInUser = userRepository.getUserByMobileNumber(mobileNumber);
		LOGGER.info("loggedInUser :- {}", mapper.writeValueAsString(loggedInUser));
		if (loggedInUser == null) {
			message = "User not found for mobileNumber :- " + mobileNumber;

		} else {

			loggedInUserRole = userRoleRepository.getUserRoleByUserIdAndRole(loggedInUser.getId(), role);
			LOGGER.info("loggedInUserRole :- {}", mapper.writeValueAsString(loggedInUserRole));
			if (loggedInUserRole == null) {
				message = role + " Role not found for mobileNumber :- " + mobileNumber;
			}
		}

		LoggedInUserCheckDetails loggedInUserCheckDetails = new LoggedInUserCheckDetails();
		loggedInUserCheckDetails.setMessage(message);
		loggedInUserCheckDetails.setLoggedInUser(loggedInUser);
		loggedInUserCheckDetails.setLoggedInUserRole(loggedInUserRole);
		return loggedInUserCheckDetails;
	}

	private List<GetLorryReceiptsResponseDetails> getLorryReceiptDetailsList(User loggedInUser, UserRole loggedInUserRole) throws SQLException, JsonProcessingException {

		List<LorryReceiptUserRole> lorryReceiptUserRoles = lorryReceiptUserRoleRepository.getLorryReceiptUserRolesByUserRoleId(loggedInUserRole.getId());
		LOGGER.info("lorryReceiptUserRoles :- {}", mapper.writeValueAsString(lorryReceiptUserRoles));

		List<GetLorryReceiptsResponseDetails> getLorryReceiptsResponseDetailsList = new ArrayList<GetLorryReceiptsResponseDetails>();
		for (LorryReceiptUserRole lorryReceiptUserRole : lorryReceiptUserRoles) {

			GetLorryReceiptsResponseDetails getLorryReceiptsResponseDetails = new GetLorryReceiptsResponseDetails();

			LorryReceipt lorryReceipt = lorryReceiptRepository.getLorryReceiptById(lorryReceiptUserRole.getLorryReceiptId());
			LOGGER.info("lorryReceipt :- {}", mapper.writeValueAsString(lorryReceipt));
			getLorryReceiptsResponseDetails.setLorryReceipt(lorryReceipt);

			List<LorryReceiptUserRole> lorryReceiptAllUserRoles = lorryReceiptUserRoleRepository.getLorryReceiptUserRolesByLorryReceiptId(lorryReceipt.getId());
			LOGGER.info("lorryReceiptAllUserRoles :- {}", mapper.writeValueAsString(lorryReceiptAllUserRoles));

			for (LorryReceiptUserRole lorryReceiptAllUserRole : lorryReceiptAllUserRoles) {

				UserRole userRole = userRoleRepository.getUserRoleById(lorryReceiptAllUserRole.getUserRoleId());
				LOGGER.info("userRole :- {}", mapper.writeValueAsString(userRole));

				populateGetLorryReceiptsResponseDetails(getLorryReceiptsResponseDetails, loggedInUser, loggedInUserRole, userRole, lorryReceiptAllUserRole);
			}

			getLorryReceiptsResponseDetailsList.add(getLorryReceiptsResponseDetails);
		}

		return getLorryReceiptsResponseDetailsList;
	}

	private void populateGetLorryReceiptsResponseDetails(GetLorryReceiptsResponseDetails getLorryReceiptsResponseDetails, User loggedInUser, UserRole loggedInUserRole, UserRole userRole, LorryReceiptUserRole lorryReceiptAllUserRole) throws SQLException, JsonProcessingException {

		User user = null;
		if (userRole.getRole().toUpperCase().equals(loggedInUserRole.getRole().toUpperCase())) {
			getLorryReceiptsResponseDetails.setVcId(lorryReceiptAllUserRole.getVcId());
			getLorryReceiptsResponseDetails.setVCStored(lorryReceiptAllUserRole.isVCStored());
			getLorryReceiptsResponseDetails.setVcUrl(lorryReceiptAllUserRole.getVcUrl());
			user = loggedInUser;

		} else {
			user = userRepository.getUserById(userRole.getUserId());
			LOGGER.info("user :- {}", mapper.writeValueAsString(user));
		}

		switch (userRole.getRole().toUpperCase()) {

		case Constants.USER_ROLE_TRANSPORTER:
			getLorryReceiptsResponseDetails.setTransporter(user);
			break;

		case Constants.USER_ROLE_CONSIGNER:
			getLorryReceiptsResponseDetails.setConsigner(user);
			break;

		case Constants.USER_ROLE_CONSIGNEE:
			getLorryReceiptsResponseDetails.setConsignee(user);
			break;

		case Constants.USER_ROLE_DRIVER:
			getLorryReceiptsResponseDetails.setDriver(user);
			break;
		}
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
}