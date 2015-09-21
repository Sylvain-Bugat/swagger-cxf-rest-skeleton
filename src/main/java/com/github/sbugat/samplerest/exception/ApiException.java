package com.github.sbugat.samplerest.exception;

public class ApiException extends Exception {

	private static final long serialVersionUID = -7527143492481161484L;

	private final int code;

	public ApiException(final int code, final String msg) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
