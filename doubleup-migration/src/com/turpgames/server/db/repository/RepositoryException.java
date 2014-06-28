package com.turpgames.server.db.repository;

public class RepositoryException extends Exception {
	private static final long serialVersionUID = -2780019309271068076L;

	public RepositoryException(String message, Object... args) {
		super(String.format(message, args));
	}

	public RepositoryException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
