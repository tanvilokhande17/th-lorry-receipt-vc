package com.amazonaws.lambda.getlorryreceiptverification.models.daos;

public class LorryReceiptUserRole {

	private int id;

	private int lorryReceiptId;

	private int userRoleId;

	private String vcId;

	private boolean isVCStored;

	private String vcUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLorryReceiptId() {
		return lorryReceiptId;
	}

	public void setLorryReceiptId(int lorryReceiptId) {
		this.lorryReceiptId = lorryReceiptId;
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
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
