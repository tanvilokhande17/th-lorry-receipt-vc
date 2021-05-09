package com.amazonaws.lambda.shareOnLorryReceiptVerification.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.shareOnLorryReceiptVerification.configs.MySqlConfig;
import com.amazonaws.lambda.shareOnLorryReceiptVerification.models.daos.LorryReceiptUserRole;

public class LorryReceiptUserRoleRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptUserRoleRepository.class);

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
