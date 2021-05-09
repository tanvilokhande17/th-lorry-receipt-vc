package com.amazonaws.lambda.actionOnLorryReceipts.models.responses;

import com.amazonaws.lambda.actionOnLorryReceipts.models.requests.ActionOnLorryReceiptRequestBody;

public class ActionOnLorryReceiptResponse {

	private ActionOnLorryReceiptRequestBody actionPayload;

	private String message;

	public ActionOnLorryReceiptRequestBody getActionPayload() {
		return actionPayload;
	}

	public void setActionPayload(ActionOnLorryReceiptRequestBody actionPayload) {
		this.actionPayload = actionPayload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}