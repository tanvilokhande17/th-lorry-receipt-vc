package com.amazonaws.lambda.getlorryreceiptverification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.getlorryreceiptverification.models.requests.GetLorryReceiptVerificationRequest;
import com.amazonaws.lambda.getlorryreceiptverification.models.responses.GetLorryReceiptVerificationResponse;
import com.amazonaws.lambda.getlorryreceiptverification.services.GetLorryReceiptVerificationService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler implements RequestHandler<GetLorryReceiptVerificationRequest, GetLorryReceiptVerificationResponse> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private GetLorryReceiptVerificationService getLorryReceiptVerificationService = new GetLorryReceiptVerificationService();

	public LambdaFunctionHandler() {
	}

	@Override
	public GetLorryReceiptVerificationResponse handleRequest(GetLorryReceiptVerificationRequest getLorryReceiptVerificationRequest, Context context) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Received request :- {}", mapper.writeValueAsString(getLorryReceiptVerificationRequest));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return getLorryReceiptVerificationService.processGetLorryReceiptVerificationRequest(getLorryReceiptVerificationRequest);
	}
}