package com.amazonaws.lambda.actionOnLorryReceipts.models.requests;

public class ActionOnLorryReceiptRequestBody {

	private int lorryReceiptId;

	private String action;

	private String role;

	public int getLorryReceiptId() {
		return lorryReceiptId;
	}

	public void setLorryReceiptId(int lorryReceiptId) {
		this.lorryReceiptId = lorryReceiptId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
