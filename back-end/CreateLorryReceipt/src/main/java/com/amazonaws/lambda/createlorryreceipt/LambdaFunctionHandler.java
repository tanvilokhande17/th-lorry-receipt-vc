package com.amazonaws.lambda.createlorryreceipt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.createlorryreceipt.models.requests.CreateLorryReceiptRequest;
import com.amazonaws.lambda.createlorryreceipt.models.responses.CreateLorryReceiptResponse;
import com.amazonaws.lambda.createlorryreceipt.services.CreateLorryReceiptService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler implements RequestHandler<CreateLorryReceiptRequest, CreateLorryReceiptResponse> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private CreateLorryReceiptService createLorryReceiptService = new CreateLorryReceiptService();

	public LambdaFunctionHandler() {
	}

	@Override
	public CreateLorryReceiptResponse handleRequest(CreateLorryReceiptRequest createLorryReceiptRequest, Context context) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Received request :- {}", mapper.writeValueAsString(createLorryReceiptRequest));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return createLorryReceiptService.processCreateLorryReceiptRequest(createLorryReceiptRequest);
	}
}