package com.amazonaws.lambda.createlorryreceipt.models.responses;

public class AffinidiSignVCResponse {

	private AffinidiSignedVC signedCredential;

	public AffinidiSignedVC getSignedCredential() {
		return signedCredential;
	}

	public void setSignedCredential(AffinidiSignedVC signedCredential) {
		this.signedCredential = signedCredential;
	}
}
