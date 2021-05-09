package com.amazonaws.lambda.createlorryreceipt.models.responses;

import com.amazonaws.lambda.createlorryreceipt.models.daos.LorryReceipt;
import com.amazonaws.lambda.createlorryreceipt.models.daos.User;

public class CreateLorryReceiptResponse {

	private LorryReceipt lorryReceipt;

	private User transporter;

	private User consigner;

	private User consignee;

	private User driver;

	private String errorMessage;

	public LorryReceipt getLorryReceipt() {
		return lorryReceipt;
	}

	public void setLorryReceipt(LorryReceipt lorryReceipt) {
		this.lorryReceipt = lorryReceipt;
	}

	public User getTransporter() {
		return transporter;
	}

	public void setTransporter(User transporter) {
		this.transporter = transporter;
	}

	public User getConsigner() {
		return consigner;
	}

	public void setConsigner(User consigner) {
		this.consigner = consigner;
	}

	public User getConsignee() {
		return consignee;
	}

	public void setConsignee(User consignee) {
		this.consignee = consignee;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}