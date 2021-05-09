package com.amazonaws.lambda.createlorryreceipt.models.requests;

import java.util.List;

public class CreateLorryReceiptDetails {

	private String vehicleNumber;

	private String loadingAddress;

	private String unloadingAddress;

	private List<CreateLorryReceiptConsignment> consignments;

	private String freight;

	private String advance;

	private String toPay;

	private String totalWeight;

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getLoadingAddress() {
		return loadingAddress;
	}

	public void setLoadingAddress(String loadingAddress) {
		this.loadingAddress = loadingAddress;
	}

	public String getUnloadingAddress() {
		return unloadingAddress;
	}

	public void setUnloadingAddress(String unloadingAddress) {
		this.unloadingAddress = unloadingAddress;
	}

	public List<CreateLorryReceiptConsignment> getConsignments() {
		return consignments;
	}

	public void setConsignments(List<CreateLorryReceiptConsignment> consignments) {
		this.consignments = consignments;
	}

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

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
}
