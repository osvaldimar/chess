package com.chess.core.service;

import java.util.UUID;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.client.ChessPlayerOnline;
import com.chess.core.client.KeyUUIDChess;
import com.chess.core.model.Player;

public class ChessPlayerOnlineCommon implements ChessPlayerOnline<Player> {

	private KeyUUIDChess keyChess;
	private GameApplication gameChess;
	
	@Override
	public GameApplication startChess(Player player1, Player player2) {
		UUID uuidP1W = UUID.randomUUID();
		UUID uuidP2B = UUID.randomUUID();
		keyChess = new KeyUUIDChess(uuidP1W, uuidP2B);
		Chessboard chessboard = new Chessboard(player1, player2);
		chessboard.startGame();
		return new GameApplication(chessboard);
	}

	@Override
	public GameApplication findGameOnline(KeyUUIDChess key) {
		if(keyChess == key)
			return gameChess;
		return null;
	}	
	

}
