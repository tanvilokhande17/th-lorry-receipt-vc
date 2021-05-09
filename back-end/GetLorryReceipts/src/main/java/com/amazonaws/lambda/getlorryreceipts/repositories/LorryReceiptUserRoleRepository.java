package com.amazonaws.lambda.getlorryreceipts.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.getlorryreceipts.configs.MySqlConfig;
import com.amazonaws.lambda.getlorryreceipts.models.daos.LorryReceiptUserRole;

public class LorryReceiptUserRoleRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LorryReceiptUserRoleRepository.class);

	public List<LorryReceiptUserRole> getLorryReceiptUserRolesByUserRoleId(int userRoleId) throws SQLException {

		LOGGER.info("LorryReceiptUserRoleRepository : getLorryReceiptUserRolesByUserRoleId, userRoleId :- {}", userRoleId);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from LORRYRECEIPTUSERROLE where userRoleId = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, userRoleId);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildLorryReceiptUserRoles(resultSet);
	}

	public List<LorryReceiptUserRole> getLorryReceiptUserRolesByLorryReceiptId(int lorryReceiptId) throws SQLException {

		LOGGER.info("LorryReceiptUserRoleRepository : getLorryReceiptUserRolesByLorryReceiptId, lorryReceiptId :- {}", lorryReceiptId);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from LORRYRECEIPTUSERROLE where lorryReceiptId = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, lorryReceiptId);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildLorryReceiptUserRoles(resultSet);
	}

	private List<LorryReceiptUserRole> buildLorryReceiptUserRoles(ResultSet resultSet) throws SQLException {

		List<LorryReceiptUserRole> lorryReceiptUserRoles = new ArrayList<LorryReceiptUserRole>();

		if (resultSet != null) {
			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				int lorryReceiptId = resultSet.getInt("lorryReceiptId");
				int userRoleId = resultSet.getInt("userRoleId");
				String vcId = resultSet.getString("vcId");
				boolean isVCStored = false;
				if (resultSet.getInt("isVCStored") != 0) {
					isVCStored = true;
				}
				String vcUrl = resultSet.getString("vcUrl");

				LorryReceiptUserRole lorryReceiptUserRole = new LorryReceiptUserRole();
				lorryReceiptUserRole.setId(id);
				lorryReceiptUserRole.setLorryReceiptId(lorryReceiptId);
				lorryReceiptUserRole.setUserRoleId(userRoleId);
				lorryReceiptUserRole.setVcId(vcId);
				lorryReceiptUserRole.setVCStored(isVCStored);
				lorryReceiptUserRole.setVcUrl(vcUrl);

				lorryReceiptUserRoles.add(lorryReceiptUserRole);
			}
		}

		return lorryReceiptUserRoles;
	}
}
