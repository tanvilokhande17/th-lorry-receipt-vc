package com.amazonaws.lambda.getlorryreceipts.models.entities;

import com.amazonaws.lambda.getlorryreceipts.models.daos.User;
import com.amazonaws.lambda.getlorryreceipts.models.daos.UserRole;

public class LoggedInUserCheckDetails {

	String message;

	User loggedInUser;

	UserRole loggedInUserRole;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public UserRole getLoggedInUserRole() {
		return loggedInUserRole;
	}

	public void setLoggedInUserRole(UserRole loggedInUserRole) {
		this.loggedInUserRole = loggedInUserRole;
	}
}
