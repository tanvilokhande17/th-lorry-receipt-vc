package com.amazonaws.lambda.createlorryreceipt.models.requests;

public class CreateLorryReceiptRequestBody {

	private String transporterMobileNumber;

	private String consignerMobileNumber;

	private String consigneeMobileNumber;

	private String driverMobileNumber;

	private CreateLorryReceiptDetails details;

	public String getTransporterMobileNumber() {
		return transporterMobileNumber;
	}

	public void setTransporterMobileNumber(String transporterMobileNumber) {
		this.transporterMobileNumber = transporterMobileNumber;
	}

	public String getConsignerMobileNumber() {
		return consignerMobileNumber;
	}

	public void setConsignerMobileNumber(String consignerMobileNumber) {
		this.consignerMobileNumber = consignerMobileNumber;
	}

	public String getConsigneeMobileNumber() {
		return consigneeMobileNumber;
	}

	public void setConsigneeMobileNumber(String consigneeMobileNumber) {
		this.consigneeMobileNumber = consigneeMobileNumber;
	}

	public String getDriverMobileNumber() {
		return driverMobileNumber;
	}

	public void setDriverMobileNumber(String driverMobileNumber) {
		this.driverMobileNumber = driverMobileNumber;
	}

	public CreateLorryReceiptDetails getDetails() {
		return details;
	}

	public void setDetails(CreateLorryReceiptDetails details) {
		this.details = details;
	}
}
