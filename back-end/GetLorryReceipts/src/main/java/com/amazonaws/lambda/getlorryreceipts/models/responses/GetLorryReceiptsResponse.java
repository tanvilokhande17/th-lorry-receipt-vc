package com.amazonaws.lambda.getlorryreceipts.models.responses;

import java.util.List;

public class GetLorryReceiptsResponse {

	private List<GetLorryReceiptsResponseDetails> lorryReceipts;

	private String errorMessage;

	public List<GetLorryReceiptsResponseDetails> getLorryReceipts() {
		return lorryReceipts;
	}

	public void setLorryReceipts(List<GetLorryReceiptsResponseDetails> lorryReceipts) {
		this.lorryReceipts = lorryReceipts;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}