package com.amazonaws.lambda.getlorryreceiptverification.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.getlorryreceiptverification.configs.MySqlConfig;
import com.amazonaws.lambda.getlorryreceiptverification.models.daos.LorryReceiptUserRole;

public class LorryReceiptUserRoleRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptUserRoleRepository.class);

	public LorryReceiptUserRole getLorryReceiptUserRoleById(int lorryReceipUserRoletId) throws SQLException {

		LOGGER.info("LorryReceiptUserRoleRepository : getLorryReceiptUserRoleById, lorryReceipUserRoletId :- {}", lorryReceipUserRoletId);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from LORRYRECEIPTUSERROLE where id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, lorryReceipUserRoletId);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildLorryReceiptUserRole(resultSet);
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
