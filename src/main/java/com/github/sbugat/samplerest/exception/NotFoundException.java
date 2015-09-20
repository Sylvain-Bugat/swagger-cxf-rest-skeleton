package com.github.sbugat.samplerest.exception;

public class NotFoundException extends ApiException {

	private static final long serialVersionUID = 6619441163039756639L;

	private final int code;

	public NotFoundException(final int code, final String msg) {
		super(code, msg);
		this.code = code;
	}
}
