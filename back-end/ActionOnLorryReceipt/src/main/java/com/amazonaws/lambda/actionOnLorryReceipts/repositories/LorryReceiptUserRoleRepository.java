package com.amazonaws.lambda.actionOnLorryReceipts.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.actionOnLorryReceipt.configs.MySqlConfig;
import com.amazonaws.lambda.actionOnLorryReceipts.models.daos.LorryReceiptUserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LorryReceiptUserRoleRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptUserRoleRepository.class);

	ObjectMapper mapper = new ObjectMapper();

	public LorryReceiptUserRole getLorryReceiptUserRolesByUserRoleIdAndReceiptId(int userRoleId, int lorryReceiptId) throws SQLException {

		LOGGER.info("LorryReceiptUserRoleRepository : getLorryReceiptUserRolesByUserRoleIdAndReceiptId, userRoleId :- {}, lorryReceiptId :- {}", userRoleId, lorryReceiptId);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from LORRYRECEIPTUSERROLE where userRoleId = ? AND lorryReceiptId = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, userRoleId);
		statement.setInt(2, lorryReceiptId);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildLorryReceiptUserRole(resultSet);
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

	private LorryReceiptUserRole buildLorryReceiptUserRole(ResultSet resultSet) throws SQLException {

		LorryReceiptUserRole lorryReceiptUserRole = null;

		if (resultSet != null && resultSet.next()) {

			int id = resultSet.getInt("id");
			int lorryReceiptId = resultSet.getInt("lorryReceiptId");
			int userRoleId = resultSet.getInt("userRoleId");
			String vcId = resultSet.getString("vcId");
			boolean isVCStored = false;
			if (resultSet.getInt("isVCStored") != 0) {
				isVCStored = true;
			}
			String vcUrl = resultSet.getString("vcUrl");

			lorryReceiptUserRole = new LorryReceiptUserRole();
			lorryReceiptUserRole.setId(id);
			lorryReceiptUserRole.setLorryReceiptId(lorryReceiptId);
			lorryReceiptUserRole.setUserRoleId(userRoleId);
			lorryReceiptUserRole.setVcId(vcId);
			lorryReceiptUserRole.setVCStored(isVCStored);
			lorryReceiptUserRole.setVcUrl(vcUrl);
		}

		return lorryReceiptUserRole;
	}
}
