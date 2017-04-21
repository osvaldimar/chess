package com.chess.core.exception;

public class Draw3PositionsException extends Exception {

	private static final long serialVersionUID = 1961725706688384798L;

	@Override
	public String getMessage() {
		return "Rule 3 equal positions, positions are same in 3 turns, then result is draw by 3 positions";
	}
}
