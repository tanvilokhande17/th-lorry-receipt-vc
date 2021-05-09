package com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos;

public class LorryReceiptVerification {

	private int id;

	private int lorryReceiptUserRoleId;

	private String vcUrl;

	private String passKey;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLorryReceiptUserRoleId() {
		return lorryReceiptUserRoleId;
	}

	public void setLorryReceiptUserRoleId(int lorryReceiptUserRoleId) {
		this.lorryReceiptUserRoleId = lorryReceiptUserRoleId;
	}

	public String getVcUrl() {
		return vcUrl;
	}

	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}

	public String getPassKey() {
		return passKey;
	}

	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}
}
