package com.amazonaws.lambda.actionOnLorryReceipts.models.requests;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class AffinidiStoreVCRequest {

	private List<JsonNode> data;

	public List<JsonNode> getData() {
		return data;
	}

	public void setData(List<JsonNode> data) {
		this.data = data;
	}
}
