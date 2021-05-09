package com.amazonaws.lambda.shareOnLorryReceiptVerification.models.responses;

public class AffinidiShareVCResponse {

	private String qrCode;

	private String sharingUrl;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getSharingUrl() {
		return sharingUrl;
	}

	public void setSharingUrl(String sharingUrl) {
		this.sharingUrl = sharingUrl;
	}
}
