package com.amazonaws.lambda.signup.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.signup.configs.MySqlConfig;
import com.amazonaws.lambda.signup.models.daos.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

	ObjectMapper mapper = new ObjectMapper();

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

	public User saveUser(User user) throws SQLException, JsonProcessingException {

		LOGGER.info("UserRepository : saveUser, user :- {}", mapper.writeValueAsString(user));
		Connection connection = MySqlConfig.getConnection();

		String query = "Insert Into USER (fullName, address, mobileNumber, did) VALUES(?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, user.getFullName());
		statement.setString(2, user.getAddress());
		statement.setString(3, user.getMobileNumber());
		statement.setString(4, user.getDid());
		LOGGER.info("PreparedStatement :- {}", statement);

		int affectedRows = statement.executeUpdate();
		if (affectedRows == 0) {
			throw new SQLException("Creating user failed, no rows affected.");
		}

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			user.setId(generatedKeys.getInt(1));
		} else {
			throw new SQLException("Creating user failed, no ID obtained.");
		}

		return user;
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
