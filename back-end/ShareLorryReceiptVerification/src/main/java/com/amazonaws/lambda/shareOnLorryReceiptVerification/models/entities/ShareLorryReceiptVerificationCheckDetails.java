package com.amazonaws.lambda.shareOnLorryReceiptVerification.models.entities;

import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos.LorryReceiptUserRole;

public class ShareLorryReceiptVerificationCheckDetails {

	String message;

	LorryReceiptUserRole lorryReceiptUserRole;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LorryReceiptUserRole getLorryReceiptUserRole() {
		return lorryReceiptUserRole;
	}

	public void setLorryReceiptUserRole(LorryReceiptUserRole lorryReceiptUserRole) {
		this.lorryReceiptUserRole = lorryReceiptUserRole;
	}
}
