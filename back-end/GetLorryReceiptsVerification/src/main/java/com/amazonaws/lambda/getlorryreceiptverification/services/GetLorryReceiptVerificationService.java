package com.amazonaws.lambda.getlorryreceiptverification.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.getlorryreceiptverification.LambdaFunctionHandler;
import com.amazonaws.lambda.getlorryreceiptverification.constants.Constants;
import com.amazonaws.lambda.getlorryreceiptverification.models.daos.LorryReceipt;
import com.amazonaws.lambda.getlorryreceiptverification.models.daos.LorryReceiptUserRole;
import com.amazonaws.lambda.getlorryreceiptverification.models.daos.LorryReceiptVerification;
import com.amazonaws.lambda.getlorryreceiptverification.models.requests.GetLorryReceiptVerificationRequest;
import com.amazonaws.lambda.getlorryreceiptverification.models.responses.GetLorryReceiptVerificationResponse;
import com.amazonaws.lambda.getlorryreceiptverification.repositories.LorryReceiptRepository;
import com.amazonaws.lambda.getlorryreceiptverification.repositories.LorryReceiptUserRoleRepository;
import com.amazonaws.lambda.getlorryreceiptverification.repositories.LorryReceiptVerificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetLorryReceiptVerificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private LorryReceiptRepository lorryReceiptRepository = new LorryReceiptRepository();
	private LorryReceiptUserRoleRepository lorryReceiptUserRoleRepository = new LorryReceiptUserRoleRepository();
	private LorryReceiptVerificationRepository lorryReceiptVerificationRepository = new LorryReceiptVerificationRepository();

	ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public GetLorryReceiptVerificationResponse processGetLorryReceiptVerificationRequest(GetLorryReceiptVerificationRequest getLorryReceiptVerificationRequest) {
		String message = "";
		GetLorryReceiptVerificationResponse getLorryReceiptVerificationResponse = new GetLorryReceiptVerificationResponse();

		try {
			String passKey = getLorryReceiptVerificationRequest.getPasskey();

			LorryReceiptVerification lorryReceiptVerification = lorryReceiptVerificationRepository.getLorryReceiptVerificationByPassKey(CryptoUtility.encrypt(passKey));
			LOGGER.info("lorryReceiptVerification :- {}", mapper.writeValueAsString(lorryReceiptVerification));
			if (lorryReceiptVerification != null) {

				LorryReceiptUserRole lorryReceiptUserRole = lorryReceiptUserRoleRepository.getLorryReceiptUserRoleById(lorryReceiptVerification.getLorryReceiptUserRoleId());
				LOGGER.info("lorryReceiptUserRole :- {}", mapper.writeValueAsString(lorryReceiptUserRole));

				LorryReceipt lorryReceipt = lorryReceiptRepository.getLorryReceiptById(lorryReceiptUserRole.getLorryReceiptId());
				LOGGER.info("lorryReceipt :- {}", mapper.writeValueAsString(lorryReceipt));

				LOGGER.info("Fetching VC from URL");
				String output = getHttpCall(lorryReceiptVerification.getVcUrl());

				getLorryReceiptVerificationResponse.setLorryReceipt(lorryReceipt);
				getLorryReceiptVerificationResponse.setVcDetails(mapper.readValue(output, Map.class));
				message = "LorryReceiptVerification passKey " + passKey + " verified succesfully";

			} else {
				message = "LorryReceiptVerification not found for passKey :- " + passKey;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			getLorryReceiptVerificationResponse.setMessage(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			getLorryReceiptVerificationResponse.setMessage(e.getMessage());
		}
		LOGGER.info(message);
		getLorryReceiptVerificationResponse.setMessage(message);
		return getLorryReceiptVerificationResponse;
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
}