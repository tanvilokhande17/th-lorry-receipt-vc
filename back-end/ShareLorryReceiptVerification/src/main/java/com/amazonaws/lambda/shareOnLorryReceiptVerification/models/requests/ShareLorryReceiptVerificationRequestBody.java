package com.amazonaws.lambda.shareOnLorryReceiptVerification.models.requests;

public class ShareLorryReceiptVerificationRequestBody {

	private int lorryReceiptId;

	private String role;

	public int getLorryReceiptId() {
		return lorryReceiptId;
	}

	public void setLorryReceiptId(int lorryReceiptId) {
		this.lorryReceiptId = lorryReceiptId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
