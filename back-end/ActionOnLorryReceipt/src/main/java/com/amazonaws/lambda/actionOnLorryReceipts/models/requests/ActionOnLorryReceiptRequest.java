package com.amazonaws.lambda.actionOnLorryReceipts.models.requests;

import java.util.Map;

public class ActionOnLorryReceiptRequest {

	private ActionOnLorryReceiptRequestBody body;

	private Map<String, String> headers;

	public ActionOnLorryReceiptRequestBody getBody() {
		return body;
	}

	public void setBody(ActionOnLorryReceiptRequestBody body) {
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
