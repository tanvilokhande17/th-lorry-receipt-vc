package com.amazonaws.lambda.signup.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.signup.configs.MySqlConfig;
import com.amazonaws.lambda.signup.models.daos.UserRole;

public class UserRoleRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleRepository.class);

	public UserRole getUserRoleByUserIdAndRole(int userId, String role) throws SQLException {

		LOGGER.info("UserRoleRepository : getUserRoleByUserIdAndRole, userId :- {}, role :- {}", userId, role);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from USERROLE where userId = ? AND role = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, userId);
		statement.setString(2, role);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildUserRole(resultSet);
	}

	public void saveUserRole(UserRole userRole) throws SQLException {

		LOGGER.info("UserRoleRepository : saveUserRole, userRole :- {}", userRole);
		Connection connection = MySqlConfig.getConnection();

		String query = "Insert Into USERROLE (userId, role) VALUES(?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, userRole.getUserId());
		statement.setString(2, userRole.getRole());
		LOGGER.info("PreparedStatement :- {}", statement);

		statement.executeUpdate();
	}

	private UserRole buildUserRole(ResultSet resultSet) throws SQLException {

		UserRole userRole = null;

		if (resultSet != null && resultSet.next()) {

			int id = resultSet.getInt("id");
			int userId = resultSet.getInt("userId");
			String role = resultSet.getString("role");

			userRole = new UserRole();
			userRole.setId(id);
			userRole.setUserId(userId);
			userRole.setRole(role);
			;
		}

		return userRole;
	}
}
