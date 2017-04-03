package com.chess.core;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.exception.CheckMoveException;
import com.chess.core.exception.CheckStateException;
import com.chess.core.exception.CheckmateException;
import com.chess.core.model.King;
import com.chess.core.model.Pawn;
import com.chess.core.model.Piece;
import com.chess.core.model.Player;
import com.chess.core.model.Square;
import com.chess.core.util.PieceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GameApplication {

	private Chessboard chessboard;
	private Player currentPlayer;
	private Square squareClicked;
	private Piece pieceClicked;
	private Piece pieceGotten;
	private PositionChessboard positionSelected;
	private List<PositionChessboard> listPositionsAvailable = new ArrayList<>();
	private List<PositionChessboard> listPositionsToTake = new ArrayList<>();
	private List<Piece> listPiecesEnemyDoCheck;
	

	public GameApplication(Chessboard chessboard) {
		this.chessboard = chessboard;
		init();
	}

	private void init() {
		currentPlayer = chessboard.getPlayer1();
		chessboard.printChessboard(chessboard, "Game Chess start...");
	}
	
	public ResponseChessboard verifyCheckmateValidator() {
		ResponseChessboard response = null;
		this.clearAllFields();
		try {
			chessboard.processValidateCheckmate(currentPlayer);
			response = buildResponseChessboard(ResponseChessboard.StatusResponse.NONE_CHECK);
		} catch (CheckmateException e) {
			listPiecesEnemyDoCheck = chessboard.getPiecesEnemyDoCheck(currentPlayer);
			response = buildResponseChessboard(ResponseChessboard.StatusResponse.CHECKMATE);
		} catch (CheckStateException e) {
			listPiecesEnemyDoCheck = chessboard.getPiecesEnemyDoCheck(currentPlayer);
			response = buildResponseChessboard(ResponseChessboard.StatusResponse.CHECK);
		}
		this.clearAllFields();
		System.out.println("\nVerify checkmate validator:\n");
		this.printInfoResponse(response);
		return response;
	}

	public ResponseChessboard nextMove(PositionChessboard pos) {
		if(pos == null)
			return new ResponseChessboard(ResponseChessboard.StatusResponse.NONE, currentPlayer); //position null not exist
		
		positionSelected = pos;
		ResponseChessboard response = null;
		if(pieceClicked != null){
			try {
				response = executeMovePiece();
				if(response == null){
					response = executeClickPiece();
				}
			} catch (CheckMoveException e) {
				response = buildResponseChessboard(ResponseChessboard.StatusResponse.EXPOSED_CHECK);
			} catch (CheckStateException e) {
				response = buildResponseChessboard(ResponseChessboard.StatusResponse.CHECK);
			}			
		}else{
			response = executeClickPiece();
		}
		this.printInfoResponse(response);
		if(response.getStatusResponse() == ResponseChessboard.StatusResponse.MOVED)
			chessboard.printChessboard(chessboard, "Game Chess turn... player now: " + currentPlayer);
		return response;
	}
	
	private ResponseChessboard executeClickPiece(){
		pieceClicked = null;
		if(squareClicked != null && squareClicked.getPosition() == positionSelected){
			this.clearAllLists();
			ResponseChessboard response = buildResponseChessboard(ResponseChessboard.StatusResponse.CLEAR);
			squareClicked = null;
			return response;	//same piece? then ignore lists and clear all
		}
		//process lists available and to take for piece clicked
		squareClicked = this.chessboard.getSquareChessboard(positionSelected);
		if(!squareClicked.isAvailable() && !PieceUtils.isPieceOfEnemy(squareClicked, currentPlayer)){
			pieceClicked = squareClicked.getPiece();
			listPositionsAvailable = pieceClicked.movementAvailable(positionSelected, this.chessboard.getSquaresChessboard());
			listPositionsToTake = pieceClicked.movementAvailableToTakePieces(positionSelected, this.chessboard.getSquaresChessboard());
			
			//special movements castling and passant
			if(pieceClicked.getTypePiece() == TypePiece.KING){
				King king = (King)pieceClicked;
				listPositionsAvailable.addAll(king.specialMovementCastling(chessboard.getSquaresChessboard()));
			}
			if(pieceClicked.getTypePiece() == TypePiece.PAWN){
				Pawn pawn = (Pawn)pieceClicked;
				listPositionsToTake.addAll(pawn.specialMovementPassant(positionSelected, chessboard.getSquaresChessboard(), 
						chessboard.getLastSquarePiceMoved()));
			}
			return buildResponseChessboard(ResponseChessboard.StatusResponse.CLICKED);
		}
		return buildResponseChessboard(ResponseChessboard.StatusResponse.NONE);
	}

	private ResponseChessboard executeMovePiece() throws CheckMoveException, CheckStateException{
		if(listPositionsAvailable.contains(positionSelected)
				|| listPositionsToTake.contains(positionSelected)){			
			pieceGotten = this.chessboard.movePieceInTheChessboard(squareClicked.getPosition(), positionSelected, pieceClicked);
			ResponseChessboard response = buildResponseChessboard(ResponseChessboard.StatusResponse.MOVED);
			this.clearAllFields();
			this.changePlayer();
			return response;
		}
		return null;
	}
	
	private void clearAllFields() {
		positionSelected = null;
		pieceClicked = null;
		pieceGotten = null;
		squareClicked = null;
		this.clearAllLists();
	}	
	private void clearAllLists() {
		listPositionsAvailable = null;
		listPositionsToTake = null;
		listPiecesEnemyDoCheck = null;
	}

	private void changePlayer(){
		if(currentPlayer.getTypePlayer() == chessboard.getPlayer1().getTypePlayer()){
			currentPlayer = chessboard.getPlayer2();
		}else{
			currentPlayer = chessboard.getPlayer1();
		}
		this.verifyCheckmateValidator();
	}
	
	private void printInfoResponse(ResponseChessboard response) {
		//System.out.println("\n" + response);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(response));
	}

	public ResponseChessboard buildResponseChessboard(ResponseChessboard.StatusResponse status){
		return new ResponseChessboard.Builder()
				.status(status)
				.currentPlayer(currentPlayer)
				.squareClicked(squareClicked)
				.pieceClicked(pieceClicked)
				.positionSelected(positionSelected)
				.listPositionsAvailable(listPositionsAvailable)
				.listPositionsToTake(listPositionsToTake)
				.listPiecesEnemyDoCheck(listPiecesEnemyDoCheck)
				.pieceGotten(pieceGotten)
				.build();
	}
}
