package com.amazonaws.lambda.actionOnLorryReceipt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.actionOnLorryReceipts.models.requests.ActionOnLorryReceiptRequest;
import com.amazonaws.lambda.actionOnLorryReceipts.models.responses.ActionOnLorryReceiptResponse;
import com.amazonaws.lambda.actionOnLorryReceipts.services.ActionOnLorryReceiptsService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler implements RequestHandler<ActionOnLorryReceiptRequest, ActionOnLorryReceiptResponse> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private ActionOnLorryReceiptsService actionOnLorryReceiptsService = new ActionOnLorryReceiptsService();

	public LambdaFunctionHandler() {
	}

	@Override
	public ActionOnLorryReceiptResponse handleRequest(ActionOnLorryReceiptRequest actionOnLorryReceiptRequest, Context context) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Received request :- {}", mapper.writeValueAsString(actionOnLorryReceiptRequest));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return actionOnLorryReceiptsService.processActionOnLorryReceiptRequest(actionOnLorryReceiptRequest);
	}
}