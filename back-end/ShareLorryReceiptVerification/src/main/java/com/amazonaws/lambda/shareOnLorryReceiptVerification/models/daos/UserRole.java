package com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos;

public class UserRole {

	private int id;

	private int userId;

	private String userDid;

	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserDid() {
		return userDid;
	}

	public void setUserDid(String userDid) {
		this.userDid = userDid;
	}
}
