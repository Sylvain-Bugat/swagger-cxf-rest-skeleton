package com.github.sbugat.samplerest.exception;

public class NotFoundException extends ApiException {

	private static final long serialVersionUID = 6619441163039756639L;

	public NotFoundException(final int code, final String msg) {
		super(code, msg);
	}
}
