package com.amazonaws.lambda.getlorryreceiptverification.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.getlorryreceiptverification.configs.MySqlConfig;
import com.amazonaws.lambda.getlorryreceiptverification.models.daos.LorryReceiptVerification;

public class LorryReceiptVerificationRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptVerificationRepository.class);

	public LorryReceiptVerification getLorryReceiptVerificationByPassKey(String passKey) throws SQLException {

		LOGGER.info("LorryReceiptVerificationRepository : getLorryReceiptVerificationByPassKey, passKey :- {}", passKey);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from LORRYRECEIPTVERIFICATION where passKey = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, passKey);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildLorryReceiptVerification(resultSet);
	}

	private LorryReceiptVerification buildLorryReceiptVerification(ResultSet resultSet) throws SQLException {

		LorryReceiptVerification lorryReceiptVerification = null;

		if (resultSet != null && resultSet.next()) {

			int id = resultSet.getInt("id");
			int lorryReceiptUserRoleId = resultSet.getInt("lorryReceiptUserRoleId");
			String vcUrl = resultSet.getString("vcUrl");
			String passKey = resultSet.getString("passKey");

			lorryReceiptVerification = new LorryReceiptVerification();
			lorryReceiptVerification.setId(id);
			lorryReceiptVerification.setLorryReceiptUserRoleId(lorryReceiptUserRoleId);
			lorryReceiptVerification.setVcUrl(vcUrl);
			lorryReceiptVerification.setPassKey(passKey);
		}

		return lorryReceiptVerification;
	}
}
