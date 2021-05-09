package com.amazonaws.lambda.createlorryreceipt.models.requests;

import java.util.Map;

public class CreateLorryReceiptRequest {

	private CreateLorryReceiptRequestBody body;

	private Map<String, String> headers;

	public CreateLorryReceiptRequestBody getBody() {
		return body;
	}

	public void setBody(CreateLorryReceiptRequestBody body) {
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
