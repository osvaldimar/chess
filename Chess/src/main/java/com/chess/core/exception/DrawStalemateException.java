package com.chess.core.exception;

public class DrawStalemateException extends Exception {

	private static final long serialVersionUID = -3222999247415766882L;

	@Override
	public String getMessage() {
		return "King not have movements allowed and any piece for to move, then result is draw by Stalemate";
	}
	
}
