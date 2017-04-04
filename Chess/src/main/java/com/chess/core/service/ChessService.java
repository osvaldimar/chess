package com.chess.core.service;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;

public final class ChessService {

	private Chessboard chessboard;
	private GameApplication game;
	private boolean playing;
	
	public String startChess(){
		this.playing = Boolean.TRUE;
		this.chessboard = new Chessboard(new Player(TypePlayer.W), new Player(TypePlayer.B));
		this.chessboard.startGame();
		this.game = new GameApplication(chessboard);
		return ResponseGameJson.createResponseJson(
				new ResponseChessboard.Builder()
				.status(ResponseChessboard.StatusResponse.START)
				.turn(new Player(TypePlayer.W))
				.build());
	}

	public String selectAndMovePiece(String positionOriginOrDestiny, String currentPlayerRequesting){
		if(!isPlaying()) {
			return ResponseGameJson.createResponseJson(
					new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OFF)
					.build());
		}
		return ResponseGameJson.createResponseJson(
				this.game.selectAndMove(PositionChessboard.getEnum(positionOriginOrDestiny), 
						this.chessboard.getPlayerByType(currentPlayerRequesting)));
	}
	
	public String verifyCheckmateTurn(){
		if(!isPlaying()) {
			return ResponseGameJson.createResponseJson(
					new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OFF)
					.build());
		}
		return ResponseGameJson.createResponseJson(
				game.verifyCheckmateValidator());
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
}
