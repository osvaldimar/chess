package com.chess.core.service;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.client.ChessPlayerOffline;
import com.chess.core.model.Player;

public class ChessPlayerOfflineCommon implements ChessPlayerOffline<Player> {

	@Override
	public GameApplication startChess(Player player1, Player player2) {
		Chessboard chessboard = new Chessboard(player1, player2);
		chessboard.startGame();
		return new GameApplication(chessboard);
	}

	
}
