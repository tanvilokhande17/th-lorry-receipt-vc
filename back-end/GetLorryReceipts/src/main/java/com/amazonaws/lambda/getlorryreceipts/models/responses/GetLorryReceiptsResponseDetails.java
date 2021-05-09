package com.amazonaws.lambda.getlorryreceipts.models.responses;

import com.amazonaws.lambda.getlorryreceipts.models.daos.LorryReceipt;
import com.amazonaws.lambda.getlorryreceipts.models.daos.User;

public class GetLorryReceiptsResponseDetails {

	private LorryReceipt lorryReceipt;

	private User transporter;

	private User consigner;

	private User consignee;

	private User driver;

	private String vcId;

	private boolean isVCStored;

	private String vcUrl;

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

	public String getVcId() {
		return vcId;
	}

	public void setVcId(String vcId) {
		this.vcId = vcId;
	}

	public boolean isVCStored() {
		return isVCStored;
	}

	public void setVCStored(boolean isVCStored) {
		this.isVCStored = isVCStored;
	}

	public String getVcUrl() {
		return vcUrl;
	}

	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}
}