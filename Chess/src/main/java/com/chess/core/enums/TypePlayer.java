package com.chess.core.enums;

import java.util.Arrays;

public enum TypePlayer {

	W, B;
	
	public static TypePlayer getEnum(String value){
		return Arrays.stream(TypePlayer.values()).filter(e -> e.name()
				.equalsIgnoreCase(value))
				.findFirst().orElse(null);
	}
}
