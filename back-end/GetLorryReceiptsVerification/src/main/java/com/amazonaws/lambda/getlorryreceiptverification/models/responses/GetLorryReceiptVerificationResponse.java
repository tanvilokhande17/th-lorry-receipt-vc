package com.amazonaws.lambda.getlorryreceiptverification.models.responses;

import java.util.Map;

import com.amazonaws.lambda.getlorryreceiptverification.models.daos.LorryReceipt;

public class GetLorryReceiptVerificationResponse {

	private LorryReceipt lorryReceipt;

	private Map<String, Object> vcDetails;

	private String message;

	public LorryReceipt getLorryReceipt() {
		return lorryReceipt;
	}

	public void setLorryReceipt(LorryReceipt lorryReceipt) {
		this.lorryReceipt = lorryReceipt;
	}

	public Map<String, Object> getVcDetails() {
		return vcDetails;
	}

	public void setVcDetails(Map<String, Object> vcDetails) {
		this.vcDetails = vcDetails;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}