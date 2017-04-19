package com.chess.core.model;

public interface PlayerMode extends Runnable {

	String nextMove();
	String getPromotion();
	boolean isArtificialInteligence();
	
}
