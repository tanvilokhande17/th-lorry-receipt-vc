package com.amazonaws.lambda.signin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.signin.models.requests.SignInRequest;
import com.amazonaws.lambda.signin.models.responses.SignInResponse;
import com.amazonaws.lambda.signin.services.SignInService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler implements RequestHandler<SignInRequest, SignInResponse> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private SignInService signUpService = new SignInService();

	public LambdaFunctionHandler() {
	}

	@Override
	public SignInResponse handleRequest(SignInRequest signInRequest, Context context) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Received request :- {}", mapper.writeValueAsString(signInRequest));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return signUpService.processSignInRequest(signInRequest);
	}
}