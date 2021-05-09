package com.amazonaws.lambda.createlorryreceipt.models.requests;

import java.util.List;

public class AffinidiCreateUnsignedVCDetails {

	private String receiptNumber;

	private UnsignedVCEntity transporter;

	private UnsignedVCEntity consigner;

	private UnsignedVCEntity consignee;

	private UnsignedVCEntity driver;

	private String vehicleNumber;

	private String loadingAddress;

	private String unloadingAddress;

	private String date;

	private List<UnsignedVCConsignment> consignments;

	private UnsignedVCFreightCharge freightCharge;

	private String totalWeight;

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public UnsignedVCEntity getTransporter() {
		return transporter;
	}

	public void setTransporter(UnsignedVCEntity transporter) {
		this.transporter = transporter;
	}

	public UnsignedVCEntity getConsigner() {
		return consigner;
	}

	public void setConsigner(UnsignedVCEntity consigner) {
		this.consigner = consigner;
	}

	public UnsignedVCEntity getConsignee() {
		return consignee;
	}

	public void setConsignee(UnsignedVCEntity consignee) {
		this.consignee = consignee;
	}

	public UnsignedVCEntity getDriver() {
		return driver;
	}

	public void setDriver(UnsignedVCEntity driver) {
		this.driver = driver;
	}

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<UnsignedVCConsignment> getConsignments() {
		return consignments;
	}

	public void setConsignments(List<UnsignedVCConsignment> consignments) {
		this.consignments = consignments;
	}

	public UnsignedVCFreightCharge getFreightCharge() {
		return freightCharge;
	}

	public void setFreightCharge(UnsignedVCFreightCharge freightCharge) {
		this.freightCharge = freightCharge;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
}
