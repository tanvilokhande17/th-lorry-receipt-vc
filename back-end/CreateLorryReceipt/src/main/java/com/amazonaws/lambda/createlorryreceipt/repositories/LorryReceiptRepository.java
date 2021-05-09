package com.amazonaws.lambda.createlorryreceipt.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.createlorryreceipt.configs.MySqlConfig;
import com.amazonaws.lambda.createlorryreceipt.models.daos.LorryReceipt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LorryReceiptRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptRepository.class);

	ObjectMapper mapper = new ObjectMapper();

	public int getMaxLorryReceiptId() throws SQLException {

		LOGGER.info("LorryReceiptRepository : getMaxLorryReceiptId");
		Connection connection = MySqlConfig.getConnection();

		String query = "Select max(id) max_id from LORRYRECEIPT";
		PreparedStatement statement = connection.prepareStatement(query);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		int maxId = 0;
		if (resultSet.next()) {
			maxId = resultSet.getInt("max_id");
		}

		return maxId;
	}

	public LorryReceipt saveLorryReceipt(LorryReceipt lorryReceipt) throws SQLException, JsonProcessingException {

		LOGGER.info("LorryReceiptRepository : saveLorryReceipt, lorryReceipt :- {}", mapper.writeValueAsString(lorryReceipt));
		Connection connection = MySqlConfig.getConnection();

		String query = "Insert Into LORRYRECEIPT (receiptNumber, status, pickUpConsignerSignatureTime, pickUpDriverSignatureTime, deliveryDriverSignatureTime, deliveryConsigneeSignatureTime) VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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

		LOGGER.info("PreparedStatement :- {}", statement);

		int affectedRows = statement.executeUpdate();
		if (affectedRows == 0) {
			throw new SQLException("Creating LorryReceipt failed, no rows affected.");
		}

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			lorryReceipt.setId(generatedKeys.getInt(1));
		} else {
			throw new SQLException("Creating LorryReceipt failed, no ID obtained.");
		}

		return lorryReceipt;
	}
}
