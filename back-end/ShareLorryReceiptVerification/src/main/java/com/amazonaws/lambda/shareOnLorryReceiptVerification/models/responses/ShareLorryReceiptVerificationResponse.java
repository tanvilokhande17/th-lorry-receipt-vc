package com.amazonaws.lambda.shareOnLorryReceiptVerification.models.responses;

public class ShareLorryReceiptVerificationResponse {

	private String qrCode;

	private String message;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}