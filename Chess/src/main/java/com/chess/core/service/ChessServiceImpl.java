package com.chess.core.service;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.client.ChessServiceRemote;
import com.chess.core.client.ResponseClientConverter;
import com.chess.core.client.TransformJson;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;

public class ChessServiceImpl implements ChessServiceRemote{

	private Chessboard chessboard;
	private GameApplication game;
	
	@Override
	public String startChess(){
		this.chessboard = new Chessboard(new Player(TypePlayer.W), new Player(TypePlayer.B));
		this.chessboard.startGame();
		this.game = new GameApplication(chessboard);
		return TransformJson.createResponseJson(
				ResponseClientConverter.convert(
						new ResponseChessboard.Builder()
						.status(ResponseChessboard.StatusResponse.START)
						.currentPlayer(new Player(TypePlayer.W))
						.turn(new Player(TypePlayer.W))
						.build()));
	}

	@Override
	public String selectAndMovePiece(String positionOriginOrDestiny, String currentPlayerRequesting){
		return TransformJson.createResponseJson(
				ResponseClientConverter.convert(
				this.game.selectAndMove(PositionChessboard.getEnum(positionOriginOrDestiny), 
						this.chessboard.getPlayerByType(currentPlayerRequesting))));
	}
	
	@Override
	public String verifyCheckmateTurn(){
		return TransformJson.createResponseJson(
				ResponseClientConverter.convert(
				this.game.verifyCheckmateValidator()));
	}
	
	@Override
	public String choosePromotion(String promotedPiece, String currentPlayerRequesting){
		return TransformJson.createResponseJson(
				ResponseClientConverter.convert(
				this.game.executePromotion(TypePiece.getEnum(promotedPiece), 
						this.chessboard.getPlayerByType(currentPlayerRequesting))));
	}
	
	@Override
	public void printInfoResponseJson(String response){
		System.out.println(response);
	}
	
	@Override
	public String getSquaresChessboardJson(){
		return this.game.getInfoChessboardJson();
	}

	@Override
	public void printSquaresChessboardJson(){
		this.game.printInfoChessboardJson();
	}
	
	@Override
	public String getLayoutChessboard(){
		return this.chessboard.getLayoutChessboard();
	}
	
	@Override
	public void printLayoutChessboard(){
		this.chessboard.printLayoutChessboard();
	}
	
}
