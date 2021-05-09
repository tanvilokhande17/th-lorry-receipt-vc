package com.amazonaws.lambda.getlorryreceipts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.getlorryreceipts.models.requests.GetLorryReceiptsRequest;
import com.amazonaws.lambda.getlorryreceipts.models.responses.GetLorryReceiptsResponse;
import com.amazonaws.lambda.getlorryreceipts.services.GetLorryReceiptsService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler implements RequestHandler<GetLorryReceiptsRequest, GetLorryReceiptsResponse> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private GetLorryReceiptsService getLorryReceiptsService = new GetLorryReceiptsService();

	public LambdaFunctionHandler() {
	}

	@Override
	public GetLorryReceiptsResponse handleRequest(GetLorryReceiptsRequest getLorryReceiptsRequest, Context context) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Received request :- {}", mapper.writeValueAsString(getLorryReceiptsRequest));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return getLorryReceiptsService.processGetLorryReceiptsRequest(getLorryReceiptsRequest);
	}
}