package com.github.sbugat.samplerest.exception;

public class BadRequestException extends ApiException {

	private static final long serialVersionUID = 5346357475983731831L;

	public BadRequestException(final int code, final String msg) {
		super(code, msg);
	}
}
