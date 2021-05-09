package com.amazonaws.lambda.signin.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.signin.LambdaFunctionHandler;
import com.amazonaws.lambda.signin.configs.ApplicationProperties;
import com.amazonaws.lambda.signin.constants.Constants;
import com.amazonaws.lambda.signin.models.daos.User;
import com.amazonaws.lambda.signin.models.daos.UserRole;
import com.amazonaws.lambda.signin.models.requests.AffinidiSignInRequest;
import com.amazonaws.lambda.signin.models.requests.SignInRequest;
import com.amazonaws.lambda.signin.models.responses.AffinidiSignInResponse;
import com.amazonaws.lambda.signin.models.responses.SignInResponse;
import com.amazonaws.lambda.signin.repositories.UserRepository;
import com.amazonaws.lambda.signin.repositories.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SignInService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private static String affinidiUrlLogin = ApplicationProperties.getProperty("affinidi.url.login");
	private static String affinidiApikeyHeader = ApplicationProperties.getProperty("affinidi.apikey.header");
	private static String affinidiApikeyValue = ApplicationProperties.getProperty("affinidi.apikey.value");

	private UserRepository userRepository = new UserRepository();
	private UserRoleRepository userRoleRepository = new UserRoleRepository();

	ObjectMapper mapper = new ObjectMapper();

	public SignInResponse processSignInRequest(SignInRequest signInRequest) {

		SignInResponse signInResponse = new SignInResponse();
		try {

			String message = "";
			User user = userRepository.getUserByMobileNumber(signInRequest.getMobileNumber());
			LOGGER.info("User from database :- {}", mapper.writeValueAsString(user));
			if (user != null) {

				UserRole userRole = userRoleRepository.getUserRoleByUserIdAndRole(user.getId(), signInRequest.getRole());
				LOGGER.info("UserRole from database :- {}", mapper.writeValueAsString(userRole));
				if (userRole != null) {

					AffinidiSignInResponse affinidiSignInResponse = signInInAffinidi(signInRequest);
					LOGGER.info("AffinidiSignInResponse :- {}", mapper.writeValueAsString(affinidiSignInResponse));

					signInResponse.setUser(user);
					signInResponse.setRole(signInRequest.getRole());
					signInResponse.setAccessToken(affinidiSignInResponse.getAccessToken());
					message = "Sign In successful";
				}

				if (userRole == null) {
					message = "User is not mapped to the role :- " + signInRequest.getRole();
				}

			} else {
				message = "User not found for the mobileNumber :- " + signInRequest.getMobileNumber();
			}

			LOGGER.info(message);
			signInResponse.setMessage(message);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LOGGER.info("SignInResponse :- {}", signInResponse);
		return signInResponse;
	}

	private AffinidiSignInResponse signInInAffinidi(SignInRequest signInRequest) throws IOException {

		LOGGER.info("Running signUpInAffinidi");

		AffinidiSignInRequest affinidiSignInRequest = new AffinidiSignInRequest();
		affinidiSignInRequest.setUsername(signInRequest.getMobileNumber());
		affinidiSignInRequest.setPassword(signInRequest.getPassword());
		ObjectMapper mapper = new ObjectMapper();
		String affinidiSignInRequestJson = mapper.writeValueAsString(affinidiSignInRequest);
		LOGGER.info("affinidiSignInRequestJson :- {}", affinidiSignInRequestJson);

		String affinidiResponseOutput = postHttpCall(affinidiUrlLogin, affinidiSignInRequestJson);
		return mapper.readValue(affinidiResponseOutput, AffinidiSignInResponse.class);
	}

	private String postHttpCall(String httpURL, String inputJson) throws IOException {

		LOGGER.info("postHttpCall, httpURL :- {}, inputJson :- {}", httpURL, inputJson);

		URL url = new URL(httpURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(Constants.API_METHOD_POST);
		conn.setRequestProperty(Constants.REQUEST_HEADER_ACCEPT, Constants.CONTENT_TYPE_APPLICATION_JSON);
		conn.setRequestProperty(Constants.REQUEST_HEADER_CONTENT_TYPE, Constants.CONTENT_TYPE_APPLICATION_JSON);
		conn.setRequestProperty(affinidiApikeyHeader, affinidiApikeyValue);

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
