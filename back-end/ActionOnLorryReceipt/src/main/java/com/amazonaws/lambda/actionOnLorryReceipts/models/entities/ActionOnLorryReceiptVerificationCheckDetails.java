package com.amazonaws.lambda.actionOnLorryReceipts.models.entities;

import com.amazonaws.lambda.actionOnLorryReceipts.models.daos.LorryReceipt;
import com.amazonaws.lambda.actionOnLorryReceipts.models.daos.LorryReceiptUserRole;

public class ActionOnLorryReceiptVerificationCheckDetails {

	String message;

	LorryReceipt lorryReceipt;

	LorryReceiptUserRole lorryReceiptUserRole;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LorryReceipt getLorryReceipt() {
		return lorryReceipt;
	}

	public void setLorryReceipt(LorryReceipt lorryReceipt) {
		this.lorryReceipt = lorryReceipt;
	}

	public LorryReceiptUserRole getLorryReceiptUserRole() {
		return lorryReceiptUserRole;
	}

	public void setLorryReceiptUserRole(LorryReceiptUserRole lorryReceiptUserRole) {
		this.lorryReceiptUserRole = lorryReceiptUserRole;
	}
}
