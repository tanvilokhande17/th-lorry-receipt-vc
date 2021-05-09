package com.amazonaws.lambda.createlorryreceipt.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.createlorryreceipt.configs.MySqlConfig;
import com.amazonaws.lambda.createlorryreceipt.models.daos.LorryReceiptUserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LorryReceiptUserRoleRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptUserRoleRepository.class);

	ObjectMapper mapper = new ObjectMapper();

	public LorryReceiptUserRole saveLorryReceiptUserRole(LorryReceiptUserRole lorryReceiptUserRole) throws SQLException, JsonProcessingException {

		LOGGER.info("LorryReceiptUserRoleRepository : saveLorryReceiptUserRole, lorryReceiptUserRole :- {}", mapper.writeValueAsString(lorryReceiptUserRole));
		Connection connection = MySqlConfig.getConnection();

		String query = "Insert Into LORRYRECEIPTUSERROLE (lorryReceiptId, userRoleId, vcId, isVCStored, vcUrl) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, lorryReceiptUserRole.getLorryReceiptId());
		statement.setInt(2, lorryReceiptUserRole.getUserRoleId());
		statement.setString(3, lorryReceiptUserRole.getVcId());
		if (lorryReceiptUserRole.isVCStored()) {
			statement.setInt(4, 1);
		} else {
			statement.setInt(4, 0);
		}
		statement.setString(5, lorryReceiptUserRole.getVcUrl());

		LOGGER.info("PreparedStatement :- {}", statement);

		int affectedRows = statement.executeUpdate();
		if (affectedRows == 0) {
			throw new SQLException("Creating LorryReceiptUserRole failed, no rows affected.");
		}

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			lorryReceiptUserRole.setId(generatedKeys.getInt(1));
		} else {
			throw new SQLException("Creating LorryReceiptUserRole failed, no ID obtained.");
		}

		return lorryReceiptUserRole;
	}

	public void updateLorryReceiptUserRole(LorryReceiptUserRole lorryReceiptUserRole) throws SQLException, JsonProcessingException {

		LOGGER.info("LorryReceiptUserRoleRepository : updateLorryReceiptUserRole, lorryReceiptUserRole :- {}", mapper.writeValueAsString(lorryReceiptUserRole));
		Connection connection = MySqlConfig.getConnection();

		String query = "Update LORRYRECEIPTUSERROLE SET lorryReceiptId=?, userRoleId=?, vcId=?, isVCStored=?, vcUrl=? WHERE id=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, lorryReceiptUserRole.getLorryReceiptId());
		statement.setInt(2, lorryReceiptUserRole.getUserRoleId());
		statement.setString(3, lorryReceiptUserRole.getVcId());
		if (lorryReceiptUserRole.isVCStored()) {
			statement.setInt(4, 1);
		} else {
			statement.setInt(4, 0);
		}
		statement.setString(5, lorryReceiptUserRole.getVcUrl());
		statement.setInt(6, lorryReceiptUserRole.getId());

		LOGGER.info("PreparedStatement :- {}", statement);

		statement.executeUpdate();
	}
}
