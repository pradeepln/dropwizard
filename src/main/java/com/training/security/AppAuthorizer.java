package com.training.security;

import io.dropwizard.auth.Authorizer;

public class AppAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User user, String role) {
		return user.getRoles() != null && user.getRoles().contains(role);
	}
}