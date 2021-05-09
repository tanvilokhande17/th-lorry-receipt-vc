package com.amazonaws.lambda.createlorryreceipt.models.entities;

import com.amazonaws.lambda.createlorryreceipt.models.daos.User;
import com.amazonaws.lambda.createlorryreceipt.models.daos.UserRole;

public class CheckLorryReceiptParticipantsDetails {

	private User transporter;

	private User consigner;

	private User consignee;

	private User driver;

	private UserRole transporterRole;

	private UserRole consignerRole;

	private UserRole consigneeRole;

	private UserRole driverRole;

	private String errorMessage;

	public User getTransporter() {
		return transporter;
	}

	public void setTransporter(User transporter) {
		this.transporter = transporter;
	}

	public User getConsigner() {
		return consigner;
	}

	public void setConsigner(User consigner) {
		this.consigner = consigner;
	}

	public User getConsignee() {
		return consignee;
	}

	public void setConsignee(User consignee) {
		this.consignee = consignee;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	public UserRole getTransporterRole() {
		return transporterRole;
	}

	public void setTransporterRole(UserRole transporterRole) {
		this.transporterRole = transporterRole;
	}

	public UserRole getConsignerRole() {
		return consignerRole;
	}

	public void setConsignerRole(UserRole consignerRole) {
		this.consignerRole = consignerRole;
	}

	public UserRole getConsigneeRole() {
		return consigneeRole;
	}

	public void setConsigneeRole(UserRole consigneeRole) {
		this.consigneeRole = consigneeRole;
	}

	public UserRole getDriverRole() {
		return driverRole;
	}

	public void setDriverRole(UserRole driverRole) {
		this.driverRole = driverRole;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
