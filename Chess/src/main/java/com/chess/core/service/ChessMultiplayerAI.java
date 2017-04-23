package com.chess.core.service;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.client.ChessMultiplayer;
import com.chess.core.client.PlayerMode;

public class ChessMultiplayerAI implements ChessMultiplayer<PlayerMode> {

	@Override
	public GameApplication startChess(PlayerMode player1, PlayerMode player2) {
		Chessboard chessboard = new Chessboard(player1, player2);
		chessboard.startGame();
		return new GameApplication(chessboard);
	}


}
