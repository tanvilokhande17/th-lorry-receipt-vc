package com.amazonaws.lambda.getlorryreceipts.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.getlorryreceipts.configs.MySqlConfig;
import com.amazonaws.lambda.getlorryreceipts.models.daos.LorryReceipt;

public class LorryReceiptRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptRepository.class);

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
