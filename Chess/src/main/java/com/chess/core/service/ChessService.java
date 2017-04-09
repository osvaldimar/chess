package com.chess.core.service;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;

public final class ChessService {

	private Chessboard chessboard;
	private GameApplication game;
	
	public String startChess(){
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
		return ResponseGameJson.createResponseJson(
				this.game.selectAndMove(PositionChessboard.getEnum(positionOriginOrDestiny), 
						this.chessboard.getPlayerByType(currentPlayerRequesting)));
	}
	
	public String verifyCheckmateTurn(){
		return ResponseGameJson.createResponseJson(
				this.game.verifyCheckmateValidator());
	}
	
	public String choosePromotion(String promotedPiece, String currentPlayerRequesting){
		return ResponseGameJson.createResponseJson(
				this.game.executePromotion(TypePiece.getEnum(promotedPiece), 
						this.chessboard.getPlayerByType(currentPlayerRequesting)));
	}
	
	public void printInfoResponseJson(String response){
		System.out.println(response);
	}
	
	public String getSquaresChessboardJson(){
		return this.game.getInfoChessboardJson();
	}

	public void printSquaresChessboardJson(){
		this.game.printInfoChessboardJson();
	}
	
	public String getLayoutChessboard(){
		return this.chessboard.getLayoutChessboard();
	}
	
	public void printLayoutChessboard(){
		this.chessboard.printLayoutChessboard();
	}
	
}
