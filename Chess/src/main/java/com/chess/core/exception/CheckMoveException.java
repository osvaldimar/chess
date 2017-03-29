package com.chess.core.exception;

public class CheckMoveException extends Exception{

	private static final long serialVersionUID = 6408004849245248685L;
	
	@Override
	public String getMessage() {
		return "King exposed to the check when attempted to move a piece";
	}
	
}
