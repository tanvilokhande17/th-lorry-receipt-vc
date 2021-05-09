package com.amazonaws.lambda.createlorryreceipt.models.requests;

public class UnsignedVCFreightCharge {

	private String freight;

	private String advance;

	private String toPay;

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getAdvance() {
		return advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}

	public String getToPay() {
		return toPay;
	}

	public void setToPay(String toPay) {
		this.toPay = toPay;
	}
}
