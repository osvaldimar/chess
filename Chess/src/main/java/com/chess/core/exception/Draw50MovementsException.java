package com.chess.core.exception;

public class Draw50MovementsException extends Exception {

	private static final long serialVersionUID = -6640911537538903195L;

	@Override
	public String getMessage() {
		return "Rule 50 movements without take any piece and not moved any pawn, then result is draw by 50 Movements";
	}
}
