package com.amazonaws.lambda.shareOnLorryReceiptVerification.services;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.shareOnLorryReceiptVerification.configs.ApplicationProperties;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.constants.Constants;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos.LorryReceipt;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos.LorryReceiptUserRole;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos.LorryReceiptVerification;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos.User;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos.UserRole;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.entities.ShareLorryReceiptVerificationCheckDetails;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.requests.ShareLorryReceiptVerificationRequest;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.requests.ShareLorryReceiptVerificationRequestBody;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.responses.AffinidiShareVCResponse;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.responses.ShareLorryReceiptVerificationResponse;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.repositories.LorryReceiptRepository;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.repositories.LorryReceiptUserRoleRepository;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.repositories.LorryReceiptVerificationRepository;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.repositories.UserRepository;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.repositories.UserRoleRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ShareLorryReceiptVerificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShareLorryReceiptVerificationService.class);

	private static String affinidiUrlShare = ApplicationProperties.getProperty("affinidi.url.share");
	private static String affinidiApikeyHeader = ApplicationProperties.getProperty("affinidi.apikey.header");
	private static String affinidiApikeyValue = ApplicationProperties.getProperty("affinidi.apikey.value");
	private static String qrTextUrl = ApplicationProperties.getProperty("qr.text.url");

	private UserRepository userRepository = new UserRepository();
	private UserRoleRepository userRoleRepository = new UserRoleRepository();
	private LorryReceiptRepository lorryReceiptRepository = new LorryReceiptRepository();
	private LorryReceiptUserRoleRepository lorryReceiptUserRoleRepository = new LorryReceiptUserRoleRepository();
	private LorryReceiptVerificationRepository lorryReceiptVerificationRepository = new LorryReceiptVerificationRepository();

	ObjectMapper mapper = new ObjectMapper();

	public ShareLorryReceiptVerificationResponse processShareLorryReceiptVerificationRequest(ShareLorryReceiptVerificationRequest shareLorryReceiptVerificationRequest) {

		ShareLorryReceiptVerificationResponse shareLorryReceiptVerificationResponse = new ShareLorryReceiptVerificationResponse();
		String message = "";
		String qrCode = null;

		try {
			Map<String, String> headers = shareLorryReceiptVerificationRequest.getHeaders();

			ShareLorryReceiptVerificationCheckDetails shareLorryReceiptVerificationCheckDetails = checkShareLorryReceiptVerificationRequest(shareLorryReceiptVerificationRequest);
			message = shareLorryReceiptVerificationCheckDetails.getMessage();

			if (message.equals("")) {
				qrCode = shareLorryReceiptVerification(shareLorryReceiptVerificationCheckDetails.getLorryReceiptUserRole(), headers.get(Constants.REQUEST_HEADER_AUTHORIZATION));
				message = "LorryReceipt shared for verification successfully";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			message = e.getMessage();

		} catch (IOException e) {
			e.printStackTrace();
			message = e.getMessage();
		}

		shareLorryReceiptVerificationResponse.setMessage(message);
		shareLorryReceiptVerificationResponse.setQrCode(qrCode);
		return shareLorryReceiptVerificationResponse;
	}

	private ShareLorryReceiptVerificationCheckDetails checkShareLorryReceiptVerificationRequest(ShareLorryReceiptVerificationRequest shareLorryReceiptVerificationRequest) throws SQLException, JsonProcessingException {

		ShareLorryReceiptVerificationCheckDetails shareLorryReceiptVerificationCheckDetails = new ShareLorryReceiptVerificationCheckDetails();
		String message = "";

		ShareLorryReceiptVerificationRequestBody shareLorryReceiptVerificationRequestBody = shareLorryReceiptVerificationRequest.getBody();
		Map<String, String> headers = shareLorryReceiptVerificationRequest.getHeaders();
		String role = shareLorryReceiptVerificationRequestBody.getRole().toUpperCase();

		Map<String, String> payloadMap = getPayloadMap(headers.get(Constants.REQUEST_HEADER_AUTHORIZATION).replace("Bearer ", ""));
		LOGGER.info("payloadMap :- {}", mapper.writeValueAsString(payloadMap));

		String mobileNumber = payloadMap.get("username");
		User loggedInUser = userRepository.getUserByMobileNumber(mobileNumber);
		LOGGER.info("loggedInUser :- {}", mapper.writeValueAsString(loggedInUser));
		if (loggedInUser != null) {

			UserRole loggedInUserRole = userRoleRepository.getUserRoleByUserIdAndRole(loggedInUser.getId(), role);
			LOGGER.info("loggedInUserRole :- {}", mapper.writeValueAsString(loggedInUserRole));
			if (loggedInUserRole != null) {

				LorryReceipt lorryReceipt = lorryReceiptRepository.getLorryReceiptById(shareLorryReceiptVerificationRequestBody.getLorryReceiptId());
				LOGGER.info("lorryReceipt :- {}", mapper.writeValueAsString(lorryReceipt));
				if (lorryReceipt != null) {

					LorryReceiptUserRole lorryReceiptUserRole = lorryReceiptUserRoleRepository.getLorryReceiptUserRolesByUserRoleIdAndReceiptId(loggedInUserRole.getId(), lorryReceipt.getId());
					LOGGER.info("lorryReceiptUserRole :- {}", mapper.writeValueAsString(lorryReceiptUserRole));

					if (lorryReceiptUserRole != null) {
						shareLorryReceiptVerificationCheckDetails.setLorryReceiptUserRole(lorryReceiptUserRole);
						if (!lorryReceiptUserRole.isVCStored()) {
							message = "Lorry Receipt must be approved before sharing for verification.";
						}
					} else {
						message = role + " LorryReceiptUserRole not found for lorryReceiptId :- " + shareLorryReceiptVerificationRequestBody.getLorryReceiptId() + ", mobileNumber :- " + mobileNumber;
					}
				} else {
					message = "LorryReceipt not found for id :- " + shareLorryReceiptVerificationRequestBody.getLorryReceiptId();
				}
			} else {
				message = role + " Role not found for mobileNumber :- " + mobileNumber;
			}
		} else {
			message = "User not found for mobileNumber :- " + mobileNumber;
		}
		shareLorryReceiptVerificationCheckDetails.setMessage(message);
		return shareLorryReceiptVerificationCheckDetails;
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

	private String shareLorryReceiptVerification(LorryReceiptUserRole lorryReceiptUserRole, String authToken) throws IOException, SQLException {

		int lorryReceiptUserRoleId = lorryReceiptUserRole.getId();
		String passKey = RandomGenerationUtility.generateKey(lorryReceiptUserRoleId);

		LOGGER.info("Fetching LorryReceiptVerification from DB");
		LorryReceiptVerification lorryReceiptVerification = lorryReceiptVerificationRepository.getLorryReceiptVerificationByLorryReceiptUserRoleId(lorryReceiptUserRoleId);
		if (lorryReceiptVerification == null) {

			AffinidiShareVCResponse shareVCResponse = shareStoredVC(lorryReceiptUserRole.getVcId(), authToken);

			LOGGER.info("Storing LorryReceiptVerification in DB");
			lorryReceiptVerification = new LorryReceiptVerification();
			lorryReceiptVerification.setLorryReceiptUserRoleId(lorryReceiptUserRoleId);
			lorryReceiptVerification.setVcUrl(shareVCResponse.getSharingUrl());
			lorryReceiptVerification.setPassKey(CryptoUtility.encrypt(passKey));
			lorryReceiptVerificationRepository.saveLorryReceiptVerification(lorryReceiptVerification);

		} else {

			lorryReceiptVerification.setPassKey(CryptoUtility.encrypt(passKey));
			lorryReceiptVerificationRepository.updateLorryReceiptVerification(lorryReceiptVerification);
		}

		String qrData = qrTextUrl + passKey;
		return crateQRCode(qrData);
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
			conn.setRequestProperty("Authorization", authToken);
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

	@SuppressWarnings("rawtypes")
	private String crateQRCode(String content) throws IOException {

		if (content != null && !content.equals("")) {

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
			hints.put(EncodeHintType.CHARACTER_SET, Constants.CHARACTER_SET_UTF8);
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.MARGIN, Constants.QR_ENCODE_MARGIN);

			try {
				QRCodeWriter writer = new QRCodeWriter();
				BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, Constants.QR_ENCODE_WIDTH, Constants.QR_ENCODE_HEIGHT, hints);

				BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
				ImageIO.write(bufferedImage, Constants.IMAGE_EXTENSION_PNG, os);
				String resultImage = new String(Constants.QR_DATA_PREFIX + Base64.encodeBase64String(os.toByteArray()));

				return resultImage;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}