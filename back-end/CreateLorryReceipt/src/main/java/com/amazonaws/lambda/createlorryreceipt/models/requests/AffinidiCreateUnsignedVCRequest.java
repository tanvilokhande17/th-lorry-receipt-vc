package com.amazonaws.lambda.createlorryreceipt.models.requests;

public class AffinidiCreateUnsignedVCRequest {

	private String type;

	private AffinidiCreateUnsignedVCDetails data;

	private String holderDid;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AffinidiCreateUnsignedVCDetails getData() {
		return data;
	}

	public void setData(AffinidiCreateUnsignedVCDetails data) {
		this.data = data;
	}

	public String getHolderDid() {
		return holderDid;
	}

	public void setHolderDid(String holderDid) {
		this.holderDid = holderDid;
	}
}
