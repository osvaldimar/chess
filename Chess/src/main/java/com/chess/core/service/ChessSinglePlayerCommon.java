package com.chess.core.service;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.client.ChessSinglePlayer;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;

public class ChessSinglePlayerCommon implements ChessSinglePlayer {

	@Override
	public GameApplication startChess() {
		Chessboard chessboard = new Chessboard(new Player(TypePlayer.W), new Player(TypePlayer.B));
		chessboard.startGame();
		return new GameApplication(chessboard);
	}

	
}
