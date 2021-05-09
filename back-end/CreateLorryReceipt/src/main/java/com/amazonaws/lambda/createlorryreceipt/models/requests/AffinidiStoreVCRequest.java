package com.amazonaws.lambda.createlorryreceipt.models.requests;

import java.util.List;

import com.amazonaws.lambda.createlorryreceipt.models.responses.AffinidiSignedVC;

public class AffinidiStoreVCRequest {

	private List<AffinidiSignedVC> data;

	public List<AffinidiSignedVC> getData() {
		return data;
	}

	public void setData(List<AffinidiSignedVC> data) {
		this.data = data;
	}
}
