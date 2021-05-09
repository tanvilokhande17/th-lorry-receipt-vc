package com.amazonaws.lambda.actionOnLorryReceipts.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.actionOnLorryReceipt.configs.MySqlConfig;
import com.amazonaws.lambda.actionOnLorryReceipts.models.daos.LorryReceipt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LorryReceiptRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptRepository.class);

	ObjectMapper mapper = new ObjectMapper();

	public LorryReceipt getLorryReceiptById(int id) throws SQLException {

		LOGGER.info("LorryReceiptRepository : getLorryReceiptById, id :- {}", id);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from LORRYRECEIPT where id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildLorryReceipt(resultSet);
	}

	public void updateLorryReceipt(LorryReceipt lorryReceipt) throws SQLException, JsonProcessingException {

		LOGGER.info("LorryReceiptRepository : updateLorryReceipt, lorryReceipt :- {}", mapper.writeValueAsString(lorryReceipt));
		Connection connection = MySqlConfig.getConnection();

		String query = "Update LORRYRECEIPT SET receiptNumber=?, status=?, pickUpConsignerSignatureTime=?, pickUpDriverSignatureTime=?, deliveryDriverSignatureTime=?, deliveryConsigneeSignatureTime=? WHERE id=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, lorryReceipt.getReceiptNumber());
		statement.setString(2, lorryReceipt.getStatus());

		if (lorryReceipt.getPickUpConsignerSignatureTime() != null) {
			statement.setDate(3, new Date(lorryReceipt.getPickUpConsignerSignatureTime().getTime()));
		} else {
			statement.setDate(3, null);
		}

		if (lorryReceipt.getPickUpDriverSignatureTime() != null) {
			statement.setDate(4, new Date(lorryReceipt.getPickUpDriverSignatureTime().getTime()));
		} else {
			statement.setDate(4, null);
		}

		if (lorryReceipt.getDeliveryDriverSignatureTime() != null) {
			statement.setDate(5, new Date(lorryReceipt.getDeliveryDriverSignatureTime().getTime()));
		} else {
			statement.setDate(5, null);
		}

		if (lorryReceipt.getDeliveryConsigneeSignatureTime() != null) {
			statement.setDate(6, new Date(lorryReceipt.getDeliveryConsigneeSignatureTime().getTime()));
		} else {
			statement.setDate(6, null);
		}

		statement.setInt(7, lorryReceipt.getId());

		LOGGER.info("PreparedStatement :- {}", statement);

		statement.executeUpdate();
	}

	private LorryReceipt buildLorryReceipt(ResultSet resultSet) throws SQLException {

		LorryReceipt lorryReceipt = null;

		if (resultSet != null && resultSet.next()) {

			int id = resultSet.getInt("id");
			String receiptNumber = resultSet.getString("receiptNumber");
			String status = resultSet.getString("status");
			Date pickUpConsignerSignatureTime = resultSet.getDate("pickUpConsignerSignatureTime");
			Date pickUpDriverSignatureTime = resultSet.getDate("pickUpDriverSignatureTime");
			Date deliveryDriverSignatureTime = resultSet.getDate("deliveryDriverSignatureTime");
			Date deliveryConsigneeSignatureTime = resultSet.getDate("deliveryConsigneeSignatureTime");
			Date createdAt = resultSet.getDate("createdAt");

			lorryReceipt = new LorryReceipt();
			lorryReceipt.setId(id);
			lorryReceipt.setReceiptNumber(receiptNumber);
			lorryReceipt.setStatus(status);
			if (pickUpConsignerSignatureTime != null) {
				lorryReceipt.setPickUpConsignerSignatureTime(new java.util.Date(pickUpConsignerSignatureTime.getTime()));
			}
			if (pickUpDriverSignatureTime != null) {
				lorryReceipt.setPickUpDriverSignatureTime(new java.util.Date(pickUpDriverSignatureTime.getTime()));
			}
			if (deliveryDriverSignatureTime != null) {
				lorryReceipt.setDeliveryDriverSignatureTime(new java.util.Date(deliveryDriverSignatureTime.getTime()));
			}
			if (deliveryConsigneeSignatureTime != null) {
				lorryReceipt.setDeliveryConsigneeSignatureTime(new java.util.Date(deliveryConsigneeSignatureTime.getTime()));
			}
			if (createdAt != null) {
				lorryReceipt.setCreatedAt(new java.util.Date(createdAt.getTime()));
			}
		}

		return lorryReceipt;
	}
}
