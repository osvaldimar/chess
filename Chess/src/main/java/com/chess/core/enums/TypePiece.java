package com.chess.core.enums;

public enum TypePiece {

	PAWN(1), ROOK(5), KNIGHT(3), BISHOP(3), QUEEN(9), KING(1000);
	
	private double value;
	
	TypePiece(int value){
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
}
