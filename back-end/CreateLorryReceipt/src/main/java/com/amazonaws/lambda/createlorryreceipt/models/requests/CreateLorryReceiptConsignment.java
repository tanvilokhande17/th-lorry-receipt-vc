package com.amazonaws.lambda.createlorryreceipt.models.requests;

public class CreateLorryReceiptConsignment {

	private String quantity;

	private String weight;

	private String description;

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
