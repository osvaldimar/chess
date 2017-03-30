package com.chess.core.exception;

public class CheckmateException extends Exception {

	private static final long serialVersionUID = -6855291783784417680L;

	@Override
	public String getMessage() {
		return "Checkmate";
	}
}
