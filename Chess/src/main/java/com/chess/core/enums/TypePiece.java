package com.chess.core.enums;

import java.util.Arrays;

public enum TypePiece {

	PAWN(1), ROOK(5), KNIGHT(3), BISHOP(3), QUEEN(9), KING(1000);
	
	private double value;
	
	TypePiece(double value){
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	public static TypePiece getEnum(String value){
		return Arrays.stream(TypePiece.values()).filter(
				f -> f.name().equalsIgnoreCase(value))
				.findFirst().orElse(null);
	}
}
