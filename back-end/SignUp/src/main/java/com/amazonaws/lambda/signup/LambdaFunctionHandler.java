package com.amazonaws.lambda.signup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.signup.models.daos.User;
import com.amazonaws.lambda.signup.models.requests.SignUpRequest;
import com.amazonaws.lambda.signup.services.SignUpService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler implements RequestHandler<SignUpRequest, User> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private SignUpService signUpService = new SignUpService();

	public LambdaFunctionHandler() {
	}

	@Override
	public User handleRequest(SignUpRequest signUpRequest, Context context) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Received request :- {}", mapper.writeValueAsString(signUpRequest));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return signUpService.processSignUpRequest(signUpRequest);
	}
}