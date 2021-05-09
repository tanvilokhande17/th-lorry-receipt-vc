package com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos;

import java.util.Date;

public class LorryReceipt {

	private int id;

	private String receiptNumber;

	private String status;

	private Date pickUpConsignerSignatureTime;

	private Date pickUpDriverSignatureTime;

	private Date deliveryDriverSignatureTime;

	private Date deliveryConsigneeSignatureTime;

	private Date createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPickUpConsignerSignatureTime() {
		return pickUpConsignerSignatureTime;
	}

	public void setPickUpConsignerSignatureTime(Date pickUpConsignerSignatureTime) {
		this.pickUpConsignerSignatureTime = pickUpConsignerSignatureTime;
	}

	public Date getPickUpDriverSignatureTime() {
		return pickUpDriverSignatureTime;
	}

	public void setPickUpDriverSignatureTime(Date pickUpDriverSignatureTime) {
		this.pickUpDriverSignatureTime = pickUpDriverSignatureTime;
	}

	public Date getDeliveryDriverSignatureTime() {
		return deliveryDriverSignatureTime;
	}

	public void setDeliveryDriverSignatureTime(Date deliveryDriverSignatureTime) {
		this.deliveryDriverSignatureTime = deliveryDriverSignatureTime;
	}

	public Date getDeliveryConsigneeSignatureTime() {
		return deliveryConsigneeSignatureTime;
	}

	public void setDeliveryConsigneeSignatureTime(Date deliveryConsigneeSignatureTime) {
		this.deliveryConsigneeSignatureTime = deliveryConsigneeSignatureTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
