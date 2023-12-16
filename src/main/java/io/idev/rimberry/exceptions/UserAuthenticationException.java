package io.idev.rimberry.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserAuthenticationException extends RuntimeException {
	private static Logger logger = LogManager.getLogger(UserAuthenticationException.class.toString());
	/**
	 * 
	 */
	private static final long serialVersionUID = -7107879666540063217L;

	private String message;

	public UserAuthenticationException() {
		super();
	}

	public UserAuthenticationException(String message) {
		super();
		this.message = message;
		logger.error(this.message);
	}

	public String getMessage() {
		return this.message;
	}
}
