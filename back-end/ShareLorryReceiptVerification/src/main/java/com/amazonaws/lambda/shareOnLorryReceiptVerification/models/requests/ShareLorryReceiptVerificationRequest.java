package com.amazonaws.lambda.shareOnLorryReceiptVerification.models.requests;

import java.util.Map;

public class ShareLorryReceiptVerificationRequest {

	private ShareLorryReceiptVerificationRequestBody body;

	private Map<String, String> headers;

	public ShareLorryReceiptVerificationRequestBody getBody() {
		return body;
	}

	public void setBody(ShareLorryReceiptVerificationRequestBody body) {
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
