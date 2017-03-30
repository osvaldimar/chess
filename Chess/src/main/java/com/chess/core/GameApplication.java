package com.chess.core;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.Chessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.exception.CheckmateException;
import com.chess.core.exception.CheckMoveException;
import com.chess.core.exception.CheckStateException;
import com.chess.core.model.Piece;
import com.chess.core.model.Player;
import com.chess.core.model.Square;
import com.chess.core.util.PieceUtils;

public final class GameApplication {

	private Chessboard chessboard;
	private Player currentPlayer;
	private Square squareClicked;
	private List<PositionChessboard> listPositionsAvailable = new ArrayList<>();
	private List<PositionChessboard> listPositionsToTake = new ArrayList<>();
	private Piece pieceClicked;

	public GameApplication(Chessboard chessboard) {
		this.chessboard = chessboard;
		init();
	}

	private void init() {
		currentPlayer = chessboard.getPlayer1();
		chessboard.printChessboard(chessboard, "Play game application starting...");
	}
	
	public ResponseChessboard verifyCheckmateValidator() {
		ResponseChessboard response = null;
		try {
			chessboard.processValidateCheckmate(currentPlayer);
			response = new ResponseChessboard(ResponseChessboard.StatusResponse.NONE, 
					null, null, currentPlayer);
			this.printInfoResponse(response);
		} catch (CheckmateException e) {
			response = new ResponseChessboard(ResponseChessboard.StatusResponse.MATE, 
					null, null, currentPlayer);
			this.printInfoResponse(response);
		}
		return response;
	}

	public ResponseChessboard nextMove(PositionChessboard pos) {
		ResponseChessboard response = null;
		if(pieceClicked != null){
			try {
				response = executeMovePiece(pos);
				if(response == null){
					response = executeClickPiece(pos);
				}
			} catch (CheckMoveException e) {
				response = new ResponseChessboard(ResponseChessboard.StatusResponse.EXPOSED, 
						pos, squareClicked, currentPlayer);
			} catch (CheckStateException e) {
				response = new ResponseChessboard(ResponseChessboard.StatusResponse.CHECK, 
						pos, squareClicked, currentPlayer);
			}			
		}else{
			response = executeClickPiece(pos);
		}
		this.printInfoResponse(response);
		if(response.getStatusResponse() == ResponseChessboard.StatusResponse.MOVED)
			chessboard.printChessboard(chessboard, "Play game application turn... player: " + currentPlayer);
		return response;
	}
	
	private ResponseChessboard executeClickPiece(PositionChessboard pos){
		pieceClicked = null;
		if(squareClicked != null && squareClicked.getPosition() == pos){
			ResponseChessboard response = new ResponseChessboard(ResponseChessboard.StatusResponse.CLEAR, pos, squareClicked, currentPlayer);
			squareClicked = null;
			return response;	//same piece? then ignore lists and clear all
		}
		//process lists available and to take for piece clicked
		squareClicked = this.chessboard.squaresChessboard(pos);
		if(!squareClicked.isAvailable() && !PieceUtils.isPieceOfEnemy(squareClicked, currentPlayer)){
			pieceClicked = squareClicked.getPiece();
			listPositionsAvailable = pieceClicked.movementAvailable(pos, this.chessboard.squaresChessboard());
			listPositionsToTake = pieceClicked.movementAvailableToTakePieces(pos, this.chessboard.squaresChessboard());
			return buildResponseClicked(pos);
		}
		ResponseChessboard response = new ResponseChessboard(ResponseChessboard.StatusResponse.NONE, pos, squareClicked, currentPlayer);
		return response;
	}
	
	private ResponseChessboard executeMovePiece(PositionChessboard pos) throws CheckMoveException, CheckStateException{
		if(listPositionsAvailable.contains(pos)){
			Piece gotten = this.chessboard.movePieceIntTheChessboard(squareClicked.getPosition(), pos, pieceClicked);
			if(gotten != null){
				throw new RuntimeException("Square marked as available, but piece gotten(" +gotten+ ") should is null");
			}
			return buildResponseMove(pos, gotten);
		}
		if(listPositionsToTake.contains(pos)){
			Piece gotten = this.chessboard.movePieceIntTheChessboard(squareClicked.getPosition(), pos, pieceClicked);
			if(gotten == null){
				throw new RuntimeException("Square marked as piece to take, but square not have anything piece");
			}			
			return buildResponseMove(pos, gotten);
		}
		return null;
	}
	
	private ResponseChessboard buildResponseClicked(PositionChessboard pos) {
		ResponseChessboard response = new ResponseChessboard(ResponseChessboard.StatusResponse.CLICKED,  pos, pieceClicked, null, currentPlayer, squareClicked, 
				listPositionsAvailable, listPositionsToTake);
		return response;
	}
	
	private ResponseChessboard buildResponseMove(PositionChessboard pos, Piece gotten) {
		ResponseChessboard response = new ResponseChessboard(ResponseChessboard.StatusResponse.MOVED, pos, pieceClicked, gotten, currentPlayer, squareClicked, 
				listPositionsAvailable, listPositionsToTake);
		pieceClicked = null;
		squareClicked = null;
		listPositionsAvailable = null;
		listPositionsToTake = null;
		this.changePlayer();		
		return response;
	}

	private void changePlayer(){
		if(currentPlayer.getTypePlayer() == chessboard.getPlayer1().getTypePlayer()){
			currentPlayer = chessboard.getPlayer2();
		}else{
			currentPlayer = chessboard.getPlayer1();
		}
	}
	
	private void printInfoResponse(ResponseChessboard response) {
		System.out.println("\n" + response);
	}

}
