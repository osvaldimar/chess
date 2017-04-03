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
	private boolean play;
	
	public String startChess(){
		this.play = Boolean.TRUE;
		this.chessboard = new Chessboard(
				new Player(TypePlayer.P1_W), new Player(TypePlayer.P2_B));
		this.chessboard.startGame();
		this.game = new GameApplication(chessboard);
		return ResponseGameJson.createResponseJson(
				new ResponseChessboard.Builder()
				.status(ResponseChessboard.StatusResponse.START)
				.turn(new Player(TypePlayer.P1_W))
				.build());
	}

	public String selectPiece(String positionOrigin){
		if(!isPlay()) {
			return ResponseGameJson.createResponseJson(
					new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OFF)
					.build());
		}
		return ResponseGameJson.createResponseJson(
				game.nextMove(PositionChessboard.getEnum(positionOrigin)));
	}
	
	public String movePiece(String positionDestiny){
		if(!isPlay()) {
			return ResponseGameJson.createResponseJson(
					new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OFF)
					.build());
		}
		return ResponseGameJson.createResponseJson(
				game.nextMove(PositionChessboard.getEnum(positionDestiny)));
	}
	
	public String verifyCheckmate(){
		if(!isPlay()) {
			return ResponseGameJson.createResponseJson(
					new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OFF)
					.build());
		}
		return ResponseGameJson.createResponseJson(
				game.verifyCheckmateValidator());
	}
	
	public boolean isPlay() {
		return play;
	}
	
}
