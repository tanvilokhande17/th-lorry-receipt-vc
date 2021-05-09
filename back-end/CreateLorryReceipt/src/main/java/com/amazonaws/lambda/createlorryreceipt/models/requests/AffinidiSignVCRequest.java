package com.amazonaws.lambda.createlorryreceipt.models.requests;

import com.amazonaws.lambda.createlorryreceipt.models.responses.AffinidiUnsignedVC;

public class AffinidiSignVCRequest {

	private AffinidiUnsignedVC unsignedCredential;

	public AffinidiUnsignedVC getUnsignedCredential() {
		return unsignedCredential;
	}

	public void setUnsignedCredential(AffinidiUnsignedVC unsignedCredential) {
		this.unsignedCredential = unsignedCredential;
	}
}
