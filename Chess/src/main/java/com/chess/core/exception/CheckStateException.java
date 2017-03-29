package com.chess.core.exception;

public class CheckStateException extends Exception{

	private static final long serialVersionUID = 1171245439671727948L;

	@Override
	public String getMessage() {
		return "King actually in check for one or more pieces";
	}
	
}
