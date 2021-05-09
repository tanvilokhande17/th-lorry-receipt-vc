package com.amazonaws.lambda.createlorryreceipt.services;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.createlorryreceipt.LambdaFunctionHandler;
import com.amazonaws.lambda.createlorryreceipt.configs.ApplicationProperties;
import com.amazonaws.lambda.createlorryreceipt.constants.Constants;
import com.amazonaws.lambda.createlorryreceipt.models.daos.LorryReceipt;
import com.amazonaws.lambda.createlorryreceipt.models.daos.LorryReceiptUserRole;
import com.amazonaws.lambda.createlorryreceipt.models.daos.User;
import com.amazonaws.lambda.createlorryreceipt.models.daos.UserRole;
import com.amazonaws.lambda.createlorryreceipt.models.entities.CheckLorryReceiptParticipantsDetails;
import com.amazonaws.lambda.createlorryreceipt.models.exceptions.NotFoundException;
import com.amazonaws.lambda.createlorryreceipt.models.requests.AffinidiCreateUnsignedVCDetails;
import com.amazonaws.lambda.createlorryreceipt.models.requests.AffinidiCreateUnsignedVCRequest;
import com.amazonaws.lambda.createlorryreceipt.models.requests.AffinidiSignVCRequest;
import com.amazonaws.lambda.createlorryreceipt.models.requests.AffinidiStoreVCRequest;
import com.amazonaws.lambda.createlorryreceipt.models.requests.CreateLorryReceiptConsignment;
import com.amazonaws.lambda.createlorryreceipt.models.requests.CreateLorryReceiptDetails;
import com.amazonaws.lambda.createlorryreceipt.models.requests.CreateLorryReceiptRequest;
import com.amazonaws.lambda.createlorryreceipt.models.requests.CreateLorryReceiptRequestBody;
import com.amazonaws.lambda.createlorryreceipt.models.requests.UnsignedVCConsignment;
import com.amazonaws.lambda.createlorryreceipt.models.requests.UnsignedVCEntity;
import com.amazonaws.lambda.createlorryreceipt.models.requests.UnsignedVCFreightCharge;
import com.amazonaws.lambda.createlorryreceipt.models.responses.AffinidiCreateUnsignedVCResponse;
import com.amazonaws.lambda.createlorryreceipt.models.responses.AffinidiShareVCResponse;
import com.amazonaws.lambda.createlorryreceipt.models.responses.AffinidiSignVCResponse;
import com.amazonaws.lambda.createlorryreceipt.models.responses.AffinidiSignedVC;
import com.amazonaws.lambda.createlorryreceipt.models.responses.AffinidiStoreVCResponse;
import com.amazonaws.lambda.createlorryreceipt.models.responses.AffinidiUnsignedVC;
import com.amazonaws.lambda.createlorryreceipt.models.responses.CreateLorryReceiptResponse;
import com.amazonaws.lambda.createlorryreceipt.repositories.LorryReceiptRepository;
import com.amazonaws.lambda.createlorryreceipt.repositories.LorryReceiptUserRoleRepository;
import com.amazonaws.lambda.createlorryreceipt.repositories.UserRepository;
import com.amazonaws.lambda.createlorryreceipt.repositories.UserRoleRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateLorryReceiptService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private static String affinidiUrlUnsigned = ApplicationProperties.getProperty("affinidi.url.unsigned");
	private static String affinidiUrlSign = ApplicationProperties.getProperty("affinidi.url.sign");
	private static String affinidiUrlStore = ApplicationProperties.getProperty("affinidi.url.store");
	private static String affinidiUrlShare = ApplicationProperties.getProperty("affinidi.url.share");
	private static String affinidiApikeyHeader = ApplicationProperties.getProperty("affinidi.apikey.header");
	private static String affinidiApikeyValue = ApplicationProperties.getProperty("affinidi.apikey.value");
	private static String affinidiCredentialType = ApplicationProperties.getProperty("affinidi.credential.type");

	private UserRepository userRepository = new UserRepository();
	private UserRoleRepository userRoleRepository = new UserRoleRepository();
	private LorryReceiptRepository lorryReceiptRepository = new LorryReceiptRepository();
	private LorryReceiptUserRoleRepository lorryReceiptUserRoleRepository = new LorryReceiptUserRoleRepository();

	ObjectMapper mapper = new ObjectMapper();

	public CreateLorryReceiptResponse processCreateLorryReceiptRequest(CreateLorryReceiptRequest createLorryReceiptRequest) {

		CreateLorryReceiptResponse createLorryReceiptResponse = new CreateLorryReceiptResponse();
		try {

			CreateLorryReceiptRequestBody createLorryReceiptRequestBody = createLorryReceiptRequest.getBody();
			CheckLorryReceiptParticipantsDetails checkLorryReceiptParticipantsDetails = checkLorryReceiptParticipants(createLorryReceiptRequestBody);
			LOGGER.info("checkLorryReceiptParticipantsDetails :- {}", mapper.writeValueAsString(checkLorryReceiptParticipantsDetails));

			String errorMessage = checkLorryReceiptParticipantsDetails.getErrorMessage();
			User transporter = checkLorryReceiptParticipantsDetails.getTransporter();
			User consigner = checkLorryReceiptParticipantsDetails.getConsigner();
			User consignee = checkLorryReceiptParticipantsDetails.getConsignee();
			User driver = checkLorryReceiptParticipantsDetails.getDriver();
			if (errorMessage.equals("")) {

				LorryReceipt lorryReceipt = createLorryReceiptInDB(createLorryReceiptRequest);
				List<LorryReceiptUserRole> lorryReceiptUserRoles = createLorryReceiptUserRoleMappingInDB(lorryReceipt, checkLorryReceiptParticipantsDetails.getTransporterRole(), checkLorryReceiptParticipantsDetails.getConsignerRole(), checkLorryReceiptParticipantsDetails.getConsigneeRole(), checkLorryReceiptParticipantsDetails.getDriverRole());
				createVCInAffinidi(lorryReceipt, createLorryReceiptRequest, lorryReceiptUserRoles, transporter, consigner, consignee, driver);

				createLorryReceiptResponse.setLorryReceipt(lorryReceipt);
				createLorryReceiptResponse.setTransporter(transporter);
				createLorryReceiptResponse.setConsigner(consigner);
				createLorryReceiptResponse.setConsignee(consignee);
				createLorryReceiptResponse.setDriver(driver);
			}
			createLorryReceiptResponse.setErrorMessage(errorMessage);
		} catch (SQLException e) {
			e.printStackTrace();
			createLorryReceiptResponse.setErrorMessage(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			createLorryReceiptResponse.setErrorMessage(e.getMessage());
		}

		return createLorryReceiptResponse;
	}

	private CheckLorryReceiptParticipantsDetails checkLorryReceiptParticipants(CreateLorryReceiptRequestBody createLorryReceiptRequestBody) throws SQLException {

		CheckLorryReceiptParticipantsDetails checkLorryReceiptParticipantsDetails = new CheckLorryReceiptParticipantsDetails();
		try {
			User transporter = userRepository.getUserByMobileNumber(createLorryReceiptRequestBody.getTransporterMobileNumber());
			User consigner = userRepository.getUserByMobileNumber(createLorryReceiptRequestBody.getConsignerMobileNumber());
			User consignee = userRepository.getUserByMobileNumber(createLorryReceiptRequestBody.getConsigneeMobileNumber());
			User driver = userRepository.getUserByMobileNumber(createLorryReceiptRequestBody.getDriverMobileNumber());

			checkLorryReceiptParticipantsDetails.setTransporter(transporter);
			checkLorryReceiptParticipantsDetails.setConsigner(consigner);
			checkLorryReceiptParticipantsDetails.setConsignee(consignee);
			checkLorryReceiptParticipantsDetails.setDriver(driver);
			checkLorryReceiptParticipantsDetails.setTransporterRole(getParticipantUserRoleByRole(transporter, Constants.USER_ROLE_TRANSPORTER));
			checkLorryReceiptParticipantsDetails.setConsignerRole(getParticipantUserRoleByRole(consigner, Constants.USER_ROLE_CONSIGNER));
			checkLorryReceiptParticipantsDetails.setConsigneeRole(getParticipantUserRoleByRole(consignee, Constants.USER_ROLE_CONSIGNEE));
			checkLorryReceiptParticipantsDetails.setDriverRole(getParticipantUserRoleByRole(driver, Constants.USER_ROLE_DRIVER));
			checkLorryReceiptParticipantsDetails.setErrorMessage("");

		} catch (NotFoundException e) {
			checkLorryReceiptParticipantsDetails.setErrorMessage(e.getMessage());
		}

		return checkLorryReceiptParticipantsDetails;
	}

	private UserRole getParticipantUserRoleByRole(User user, String role) throws NotFoundException, SQLException {

		UserRole userRole = null;
		if (user != null) {
			userRole = userRoleRepository.getUserRoleByUserIdAndRole(user.getId(), role);
			userRole.setUserDid(user.getDid());
		}
		if (userRole == null) {
			throw new NotFoundException(role + " Role not found for mobileNumber :- " + user.getMobileNumber());
		}
		return userRole;
	}

	private LorryReceipt createLorryReceiptInDB(CreateLorryReceiptRequest createLorryReceiptRequest) throws SQLException, JsonProcessingException {

		int maxLorryId = lorryReceiptRepository.getMaxLorryReceiptId();
		String receiptNumber = Constants.LORRYRECEIPT_NUMBER_PREFIX + String.format("%07d", maxLorryId + 1);

		LorryReceipt lorryReceipt = new LorryReceipt();
		lorryReceipt.setReceiptNumber(receiptNumber);
		lorryReceipt.setStatus(Constants.LORRYRECEIPT_STATUS_BOOKED);
		lorryReceipt.setPickUpConsignerSignatureTime(null);
		lorryReceipt.setPickUpDriverSignatureTime(null);
		lorryReceipt.setDeliveryDriverSignatureTime(null);
		lorryReceipt.setDeliveryConsigneeSignatureTime(null);

		LOGGER.info("Lorry Receipt :- {}", mapper.writeValueAsString(lorryReceipt));
		return lorryReceiptRepository.saveLorryReceipt(lorryReceipt);
	}

	private List<LorryReceiptUserRole> createLorryReceiptUserRoleMappingInDB(LorryReceipt lorryReceipt, UserRole transporterRole, UserRole consignerRole, UserRole consigneeRole, UserRole driverRole) throws SQLException, JsonProcessingException {

		List<LorryReceiptUserRole> lorryReceiptUserRoles = new ArrayList<LorryReceiptUserRole>();

		LorryReceiptUserRole lorryReceiptTransporterUserRole = buildLorryReceiptUserRole(transporterRole, lorryReceipt);
		LOGGER.info("LorryReceiptUserRole, Transporter :- {}", mapper.writeValueAsString(lorryReceiptTransporterUserRole));
		lorryReceiptTransporterUserRole = lorryReceiptUserRoleRepository.saveLorryReceiptUserRole(lorryReceiptTransporterUserRole);
		lorryReceiptTransporterUserRole.setUserDid(transporterRole.getUserDid());
		lorryReceiptUserRoles.add(lorryReceiptTransporterUserRole);

		LorryReceiptUserRole lorryReceiptConsignerUserRole = buildLorryReceiptUserRole(consignerRole, lorryReceipt);
		LOGGER.info("LorryReceiptUserRole, Consigner :- {}", mapper.writeValueAsString(lorryReceiptConsignerUserRole));
		lorryReceiptConsignerUserRole = lorryReceiptUserRoleRepository.saveLorryReceiptUserRole(lorryReceiptConsignerUserRole);
		lorryReceiptConsignerUserRole.setUserDid(consignerRole.getUserDid());
		lorryReceiptUserRoles.add(lorryReceiptConsignerUserRole);

		LorryReceiptUserRole lorryReceiptConsigneeUserRole = buildLorryReceiptUserRole(consigneeRole, lorryReceipt);
		LOGGER.info("LorryReceiptUserRole, Consignee :- {}", mapper.writeValueAsString(lorryReceiptConsigneeUserRole));
		lorryReceiptConsigneeUserRole = lorryReceiptUserRoleRepository.saveLorryReceiptUserRole(lorryReceiptConsigneeUserRole);
		lorryReceiptConsigneeUserRole.setUserDid(consigneeRole.getUserDid());
		lorryReceiptUserRoles.add(lorryReceiptConsigneeUserRole);

		LorryReceiptUserRole lorryReceiptDriverUserRole = buildLorryReceiptUserRole(driverRole, lorryReceipt);
		LOGGER.info("LorryReceiptUserRole, Driver :- {}", mapper.writeValueAsString(lorryReceiptDriverUserRole));
		lorryReceiptDriverUserRole = lorryReceiptUserRoleRepository.saveLorryReceiptUserRole(lorryReceiptDriverUserRole);
		lorryReceiptDriverUserRole.setUserDid(driverRole.getUserDid());
		lorryReceiptUserRoles.add(lorryReceiptDriverUserRole);

		return lorryReceiptUserRoles;
	}

	private LorryReceiptUserRole buildLorryReceiptUserRole(UserRole userRole, LorryReceipt lorryReceipt) {

		LorryReceiptUserRole lorryReceiptUserRole = new LorryReceiptUserRole();
		lorryReceiptUserRole.setLorryReceiptId(lorryReceipt.getId());
		lorryReceiptUserRole.setUserRoleId(userRole.getId());
		lorryReceiptUserRole.setVcId(null);
		lorryReceiptUserRole.setVCStored(false);
		lorryReceiptUserRole.setVcUrl(null);

		return lorryReceiptUserRole;
	}

	private void createVCInAffinidi(LorryReceipt lorryReceipt, CreateLorryReceiptRequest createLorryReceiptRequest, List<LorryReceiptUserRole> lorryReceiptUserRoles, User transporter, User consigner, User consignee, User driver) throws JsonProcessingException, IOException, SQLException {

		Map<String, String> headers = createLorryReceiptRequest.getHeaders();
		AffinidiCreateUnsignedVCDetails data = buildAffinidiCreateUnsignedVCDetails(lorryReceipt, createLorryReceiptRequest, transporter, consigner, consignee, driver);

		for (LorryReceiptUserRole lorryReceiptUserRole : lorryReceiptUserRoles) {

			AffinidiCreateUnsignedVCResponse createUnsignedVCResponse = createUnsignedVC(lorryReceiptUserRole, data, headers.get(Constants.REQUEST_HEADER_AUTHORIZATION));
			AffinidiSignVCResponse signCVResponse = signUnsignedVC(createUnsignedVCResponse.getUnsignedVC(), headers.get(Constants.REQUEST_HEADER_AUTHORIZATION));
			AffinidiStoreVCResponse storeVCResponse = storeSignedVC(signCVResponse.getSignedCredential(), headers.get(Constants.REQUEST_HEADER_AUTHORIZATION));

			String vcId = storeVCResponse.getCredentialIds().get(0);
			lorryReceiptUserRole.setVcId(vcId);

			if (lorryReceiptUserRole.getUserDid().equals(transporter.getDid())) {
				lorryReceiptUserRole.setVCStored(true);
				lorryReceiptUserRole.setVcUrl(null);

			} else {

				AffinidiShareVCResponse shareVCResponse = shareStoredVC(vcId, headers.get(Constants.REQUEST_HEADER_AUTHORIZATION));
				lorryReceiptUserRole.setVCStored(false);
				lorryReceiptUserRole.setVcUrl(shareVCResponse.getSharingUrl());
			}

			LOGGER.info("Updating LorryReceiptUserRole :- {}", mapper.writeValueAsString(lorryReceiptUserRole));
			lorryReceiptUserRoleRepository.updateLorryReceiptUserRole(lorryReceiptUserRole);
		}
	}

	private AffinidiCreateUnsignedVCDetails buildAffinidiCreateUnsignedVCDetails(LorryReceipt lorryReceipt, CreateLorryReceiptRequest createLorryReceiptRequest, User transporter, User consigner, User consignee, User driver) {

		CreateLorryReceiptRequestBody createLorryReceiptRequestBody = createLorryReceiptRequest.getBody();
		CreateLorryReceiptDetails createLorryReceiptDetails = createLorryReceiptRequestBody.getDetails();
		List<UnsignedVCConsignment> affiidiConsignments = new ArrayList<UnsignedVCConsignment>();
		for (CreateLorryReceiptConsignment consignment : createLorryReceiptDetails.getConsignments()) {

			UnsignedVCConsignment affiidiConsignment = new UnsignedVCConsignment();
			affiidiConsignment.setQuantity(consignment.getQuantity());
			affiidiConsignment.setWeight(consignment.getWeight());
			affiidiConsignment.setDescription(consignment.getDescription());
			affiidiConsignments.add(affiidiConsignment);
		}

		UnsignedVCFreightCharge freightCharge = new UnsignedVCFreightCharge();
		freightCharge.setFreight(createLorryReceiptDetails.getFreight());
		freightCharge.setAdvance(createLorryReceiptDetails.getAdvance());
		freightCharge.setToPay(createLorryReceiptDetails.getToPay());

		AffinidiCreateUnsignedVCDetails affinidiCreateUnsignedVCDetails = new AffinidiCreateUnsignedVCDetails();
		affinidiCreateUnsignedVCDetails.setReceiptNumber(lorryReceipt.getReceiptNumber());
		affinidiCreateUnsignedVCDetails.setTransporter(buildUnsignedVCEntity(transporter));
		affinidiCreateUnsignedVCDetails.setConsigner(buildUnsignedVCEntity(consigner));
		affinidiCreateUnsignedVCDetails.setConsignee(buildUnsignedVCEntity(consignee));
		affinidiCreateUnsignedVCDetails.setDriver(buildUnsignedVCEntity(driver));
		affinidiCreateUnsignedVCDetails.setVehicleNumber(createLorryReceiptDetails.getVehicleNumber());
		affinidiCreateUnsignedVCDetails.setLoadingAddress(createLorryReceiptDetails.getLoadingAddress());
		affinidiCreateUnsignedVCDetails.setUnloadingAddress(createLorryReceiptDetails.getUnloadingAddress());
		affinidiCreateUnsignedVCDetails.setDate(String.valueOf(new Date()));
		affinidiCreateUnsignedVCDetails.setConsignments(affiidiConsignments);
		affinidiCreateUnsignedVCDetails.setFreightCharge(freightCharge);
		affinidiCreateUnsignedVCDetails.setTotalWeight(createLorryReceiptDetails.getTotalWeight());

		return affinidiCreateUnsignedVCDetails;
	}

	private UnsignedVCEntity buildUnsignedVCEntity(User user) {

		UnsignedVCEntity unsignedVCEntity = new UnsignedVCEntity();
		unsignedVCEntity.setName(user.getFullName());
		unsignedVCEntity.setAddress(user.getAddress());
		unsignedVCEntity.setMobileNumber(user.getMobileNumber());
		return unsignedVCEntity;
	}

	private AffinidiCreateUnsignedVCResponse createUnsignedVC(LorryReceiptUserRole lorryReceiptUserRole, AffinidiCreateUnsignedVCDetails data, String authToken) throws JsonProcessingException, IOException {

		LOGGER.info("Creating unsigned VC, LorryReceiptUserRole :- {}", mapper.writeValueAsString(lorryReceiptUserRole));
		AffinidiCreateUnsignedVCRequest affinidiCreateUnsignedVCRequest = new AffinidiCreateUnsignedVCRequest();
		affinidiCreateUnsignedVCRequest.setType(affinidiCredentialType);
		affinidiCreateUnsignedVCRequest.setHolderDid(lorryReceiptUserRole.getUserDid());
		affinidiCreateUnsignedVCRequest.setData(data);
		LOGGER.info("AffinidiCreateUnsignedVCRequest :- {}", mapper.writeValueAsString(affinidiCreateUnsignedVCRequest));
		String output = postHttpCall(affinidiUrlUnsigned, mapper.writeValueAsString(affinidiCreateUnsignedVCRequest), authToken);
		AffinidiCreateUnsignedVCResponse createUnsignedVCResponse = mapper.readValue(output, AffinidiCreateUnsignedVCResponse.class);
		LOGGER.info("AffinidiCreateUnsignedVCResponse :- {}", mapper.writeValueAsString(createUnsignedVCResponse));
		return createUnsignedVCResponse;
	}

	private AffinidiSignVCResponse signUnsignedVC(AffinidiUnsignedVC affinidiUnsignedVC, String authToken) throws JsonProcessingException, IOException {

		LOGGER.info("Signing VC, AffinidiUnsignedVC :- {}", mapper.writeValueAsString(affinidiUnsignedVC));
		AffinidiSignVCRequest affinidiSignVCRequest = new AffinidiSignVCRequest();
		affinidiSignVCRequest.setUnsignedCredential(affinidiUnsignedVC);
		LOGGER.info("AffinidiSignVCRequest :- {}", mapper.writeValueAsString(affinidiSignVCRequest));
		String output = postHttpCall(affinidiUrlSign, mapper.writeValueAsString(affinidiSignVCRequest), authToken);
		AffinidiSignVCResponse signCVResponse = mapper.readValue(output, AffinidiSignVCResponse.class);
		LOGGER.info("AffinidiSignVCResponse :- {}", mapper.writeValueAsString(signCVResponse));
		return signCVResponse;
	}

	private AffinidiStoreVCResponse storeSignedVC(AffinidiSignedVC affinidiSignedVC, String authToken) throws JsonProcessingException, IOException {

		LOGGER.info("Storing VC");
		List<AffinidiSignedVC> affinidiSignedVCs = new ArrayList<AffinidiSignedVC>();
		affinidiSignedVCs.add(affinidiSignedVC);
		AffinidiStoreVCRequest affinidiStoreVCRequest = new AffinidiStoreVCRequest();
		affinidiStoreVCRequest.setData(affinidiSignedVCs);
		LOGGER.info("AffinidiStoreVCRequest :- {}", mapper.writeValueAsString(affinidiStoreVCRequest));
		String output = postHttpCall(affinidiUrlStore, mapper.writeValueAsString(affinidiStoreVCRequest), authToken);
		AffinidiStoreVCResponse storeVCResponse = mapper.readValue(output, AffinidiStoreVCResponse.class);
		LOGGER.info("AffinidiStoreVCResponse :- {}", mapper.writeValueAsString(storeVCResponse));
		return storeVCResponse;
	}

	private AffinidiShareVCResponse shareStoredVC(String vcId, String authToken) throws JsonParseException, JsonMappingException, IOException {

		String url = affinidiUrlShare;
		url = url.replace("{vcId}", vcId);
		LOGGER.info("Sharing VC");
		Map<String, String> payload = new HashMap<String, String>();
		payload.put("ttl", "0");
		String output = postHttpCall(url, mapper.writeValueAsString(payload), authToken);
		AffinidiShareVCResponse shareVCResponse = mapper.readValue(output, AffinidiShareVCResponse.class);
		LOGGER.info("AffinidiShareVCResponse :- {}", mapper.writeValueAsString(shareVCResponse));
		return shareVCResponse;
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
