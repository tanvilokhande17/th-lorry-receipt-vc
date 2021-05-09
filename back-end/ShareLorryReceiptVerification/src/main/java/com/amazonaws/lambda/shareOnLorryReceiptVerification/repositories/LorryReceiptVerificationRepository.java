package com.amazonaws.lambda.shareOnLorryReceiptVerification.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.shareOnLorryReceiptVerification.configs.MySqlConfig;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos.LorryReceiptVerification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LorryReceiptVerificationRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptVerificationRepository.class);

	ObjectMapper mapper = new ObjectMapper();

	public LorryReceiptVerification getLorryReceiptVerificationByLorryReceiptUserRoleId(int lorryReceiptUserRoleId) throws SQLException {

		LOGGER.info("LorryReceiptVerificationRepository : getLorryReceiptVerificationByLorryReceiptUserRoleId, lorryReceiptUserRoleId :- {}", lorryReceiptUserRoleId);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from LORRYRECEIPTVERIFICATION where lorryReceiptUserRoleId = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, lorryReceiptUserRoleId);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildLorryReceiptVerification(resultSet);
	}

	public LorryReceiptVerification saveLorryReceiptVerification(LorryReceiptVerification lorryReceiptVerification) throws SQLException, JsonProcessingException {

		LOGGER.info("LorryReceiptVerificationRepository : saveLorryReceiptVerification, lorryReceiptVerification :- {}", mapper.writeValueAsString(lorryReceiptVerification));
		Connection connection = MySqlConfig.getConnection();

		String query = "Insert Into LORRYRECEIPTVERIFICATION (lorryReceiptUserRoleId, vcUrl, passKey) VALUES(?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, lorryReceiptVerification.getLorryReceiptUserRoleId());
		statement.setString(2, lorryReceiptVerification.getVcUrl());
		statement.setString(3, lorryReceiptVerification.getPassKey());

		LOGGER.info("PreparedStatement :- {}", statement);

		int affectedRows = statement.executeUpdate();
		if (affectedRows == 0) {
			throw new SQLException("Creating LorryReceiptVerification failed, no rows affected.");
		}

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			lorryReceiptVerification.setId(generatedKeys.getInt(1));
		} else {
			throw new SQLException("Creating LorryReceiptVerification failed, no ID obtained.");
		}

		return lorryReceiptVerification;
	}

	public void updateLorryReceiptVerification(LorryReceiptVerification lorryReceiptVerification) throws SQLException {

		LOGGER.info("LorryReceiptVerificationRepository : updateLorryReceiptVerification, lorryReceiptVerification :- {}", lorryReceiptVerification);
		Connection connection = MySqlConfig.getConnection();

		String query = "Update LORRYRECEIPTVERIFICATION SET lorryReceiptUserRoleId=?, vcUrl=?, passKey=? WHERE id=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, lorryReceiptVerification.getLorryReceiptUserRoleId());
		statement.setString(2, lorryReceiptVerification.getVcUrl());
		statement.setString(3, lorryReceiptVerification.getPassKey());
		statement.setInt(4, lorryReceiptVerification.getId());

		LOGGER.info("PreparedStatement :- {}", statement);

		statement.executeUpdate();
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
