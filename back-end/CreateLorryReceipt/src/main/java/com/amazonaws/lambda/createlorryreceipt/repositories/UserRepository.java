package com.amazonaws.lambda.createlorryreceipt.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.createlorryreceipt.configs.MySqlConfig;
import com.amazonaws.lambda.createlorryreceipt.models.daos.User;

public class UserRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

	public User getUserByMobileNumber(String mobileNumber) throws SQLException {

		LOGGER.info("UserRepository : getUserByMobileNumber, mobileNumber :- {}", mobileNumber);
		Connection connection = MySqlConfig.getConnection();

		String query = "Select * from USER where mobileNumber = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, mobileNumber);
		LOGGER.info("PreparedStatement :- {}", statement);

		ResultSet resultSet = statement.executeQuery();
		return buildUser(resultSet);
	}

	private User buildUser(ResultSet resultSet) throws SQLException {

		User user = null;

		if (resultSet != null && resultSet.next()) {

			int id = resultSet.getInt("id");
			String fullName = resultSet.getString("fullName");
			String address = resultSet.getString("address");
			String mobileNumber = resultSet.getString("mobileNumber");
			String did = resultSet.getString("did");

			user = new User();
			user.setId(id);
			user.setFullName(fullName);
			user.setAddress(address);
			user.setMobileNumber(mobileNumber);
			user.setDid(did);
		}

		return user;
	}
}
