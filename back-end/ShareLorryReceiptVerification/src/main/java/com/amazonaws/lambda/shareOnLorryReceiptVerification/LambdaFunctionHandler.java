package com.amazonaws.lambda.shareOnLorryReceiptVerification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.requests.ShareLorryReceiptVerificationRequest;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.responses.ShareLorryReceiptVerificationResponse;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.services.ShareLorryReceiptVerificationService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler implements RequestHandler<ShareLorryReceiptVerificationRequest, ShareLorryReceiptVerificationResponse> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaFunctionHandler.class);

	private ShareLorryReceiptVerificationService shareLorryReceiptVerificationService = new ShareLorryReceiptVerificationService();

	public LambdaFunctionHandler() {
	}

	@Override
	public ShareLorryReceiptVerificationResponse handleRequest(ShareLorryReceiptVerificationRequest shareLorryReceiptVerificationRequest, Context context) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Received request :- {}", mapper.writeValueAsString(shareLorryReceiptVerificationRequest));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return shareLorryReceiptVerificationService.processShareLorryReceiptVerificationRequest(shareLorryReceiptVerificationRequest);
	}
}