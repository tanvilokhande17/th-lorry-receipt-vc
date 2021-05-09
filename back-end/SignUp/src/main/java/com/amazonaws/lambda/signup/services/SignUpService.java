package com.amazonaws.lambda.signup.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.signup.LambdaFunctionHandler;
import com.amazonaws.lambda.signup.configs.ApplicationProperties;
import com.amazonaws.lambda.signup.constants.Constants;
import com.amazonaws.lambda.signup.models.daos.User;
import com.amazonaws.lambda.signup.models.daos.UserRole;
import com.amazonaws.lambda.signup.models.requests.AffinidiSignUpRequest;
import com.amazonaws.lambda.signup.models.requests.SignUpRequest;
import com.amazonaws.lambda.signup.models.responses.AffinidiSignUpResponse;
import com.amazonaws.lambda.signup.repositories.UserRepository;
import com.amazonaws.lambda.signup.repositories.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SignUpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private static String affinidiUrlSignup = ApplicationProperties.getProperty("affinidi.url.signup");
	private static String affinidiApikeyHeader = ApplicationProperties.getProperty("affinidi.apikey.header");
	private static String affinidiApikeyValue = ApplicationProperties.getProperty("affinidi.apikey.value");

	private UserRepository userRepository = new UserRepository();
	private UserRoleRepository userRoleRepository = new UserRoleRepository();

	ObjectMapper mapper = new ObjectMapper();

	public User processSignUpRequest(SignUpRequest signUpRequest) {

		User user = null;
		try {

			user = userRepository.getUserByMobileNumber(signUpRequest.getMobileNumber());
			LOGGER.info("User from database :- {}", mapper.writeValueAsString(user));
			if (user != null) {

				UserRole userRole = userRoleRepository.getUserRoleByUserIdAndRole(user.getId(), signUpRequest.getRole());
				LOGGER.info("UserRole from database :- {}", mapper.writeValueAsString(user));
				if (userRole == null) {
					createNewUserRole(user, signUpRequest);
				}

			} else {
				user = createNewUser(signUpRequest);
				createNewUserRole(user, signUpRequest);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return user;
	}

	private User createNewUser(SignUpRequest signUpRequest) throws SQLException, IOException {

		AffinidiSignUpResponse affinidiSignUpResponse = signUpInAffinidi(signUpRequest);
		LOGGER.info("AffinidiSignUpResponse :- {}", mapper.writeValueAsString(affinidiSignUpResponse));

		User user = new User();
		user.setFullName(signUpRequest.getFullName());
		user.setAddress(signUpRequest.getAddress());
		user.setMobileNumber(signUpRequest.getMobileNumber());
		user.setDid(affinidiSignUpResponse.getDid());

		user = userRepository.saveUser(user);
		LOGGER.info("New User :- {}", mapper.writeValueAsString(user));
		return user;
	}

	private void createNewUserRole(User user, SignUpRequest signUpRequest) throws SQLException {

		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		userRole.setRole(signUpRequest.getRole());
		userRoleRepository.saveUserRole(userRole);
	}

	private AffinidiSignUpResponse signUpInAffinidi(SignUpRequest signUpRequest) throws IOException {

		LOGGER.info("Running signUpInAffinidi");

		AffinidiSignUpRequest affinidiSignUpRequest = new AffinidiSignUpRequest();
		affinidiSignUpRequest.setUsername(signUpRequest.getMobileNumber());
		affinidiSignUpRequest.setPassword(signUpRequest.getPassword());
		ObjectMapper mapper = new ObjectMapper();
		String affinidiSignUpRequestJson = mapper.writeValueAsString(affinidiSignUpRequest);
		LOGGER.info("affinidiSignUpRequestJson :- {}", affinidiSignUpRequestJson);

		String affinidiResponseOutput = postHttpCall(affinidiUrlSignup, affinidiSignUpRequestJson);
		return mapper.readValue(affinidiResponseOutput, AffinidiSignUpResponse.class);
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
