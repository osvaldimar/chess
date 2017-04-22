package com.chess.core.client;

import com.chess.core.GameApplication;

public interface ChessPlayer<T> {

	GameApplication startChess(T player1, T player2);
	
}
