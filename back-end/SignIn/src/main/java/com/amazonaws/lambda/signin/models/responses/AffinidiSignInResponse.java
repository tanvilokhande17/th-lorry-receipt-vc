package com.amazonaws.lambda.signin.models.responses;

public class AffinidiSignInResponse {

	private String accessToken;

	private String did;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
}
