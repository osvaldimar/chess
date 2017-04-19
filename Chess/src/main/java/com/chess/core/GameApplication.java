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
import com.chess.core.util.ChessboardPieceFactory;
import com.chess.core.util.PieceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GameApplication {

	private Chessboard chessboard;
	private Player turnPlayer;
	private Player currentPlayerRequesting;
	private Square squareClicked;
	private Piece pieceClicked;
	private Piece pieceGotten;
	private PositionChessboard positionSelected;
	private List<PositionChessboard> listPositionsAvailable = new ArrayList<>();
	private List<PositionChessboard> listPositionsToTake = new ArrayList<>();
	private List<Piece> listPiecesEnemyDoCheck;
	
	private boolean playing;

	public GameApplication(Chessboard chessboard) {
		this.chessboard = chessboard;
		init();
	}

	private void init() {
		this.playing = Boolean.TRUE;
		turnPlayer = chessboard.getPlayer1();
		currentPlayerRequesting = chessboard.getPlayer1();
		chessboard.printDebugChessboard(chessboard, "Game Chess start...");
	}
	
	public ResponseChessboard executePromotion(TypePiece type, Player currentPlayerRequesting){
		if(!this.playing) {
			return new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OFF)
					.build();
		}
		//valid currentPlayer and turn
		Piece piece = ChessboardPieceFactory.buildPieceByType(type, currentPlayerRequesting);
		if(currentPlayerRequesting != null && 
				currentPlayerRequesting.getTypePlayer() != turnPlayer.getTypePlayer()) 
			return new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OPPONENT_TURN) 
					.currentPlayer(currentPlayerRequesting)
					.turn(turnPlayer)
					.build();
		if(chessboard.getPositionPromotionPawn() == null){
			return buildResponseChessboard(ResponseChessboard.StatusResponse.NONE_ACTION);
		}
		if(piece != null){
			chessboard.processPromotionOfPawn(positionSelected, piece);
			this.changeTurnPlayer();
			ResponseChessboard response = buildResponseChessboard(ResponseChessboard.StatusResponse.MOVED);
			this.clearAllFields();
			this.chessboard.play(turnPlayer.getTypePlayer());
			return response;
		}
		return buildResponseChessboard(ResponseChessboard.StatusResponse.PAWN_PROMOTION);
	}
	
	public ResponseChessboard verifyCheckmateValidator() {
		if(!this.playing) {
			return new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OFF)
					.build();
		}
		ResponseChessboard response = null;
		this.clearAllFields();
		try {
			chessboard.processValidateCheckmate(turnPlayer);
			response = buildResponseChessboard(ResponseChessboard.StatusResponse.NONE_CHECK);
		} catch (CheckmateException e) {
			listPiecesEnemyDoCheck = chessboard.getPiecesEnemyDoCheck(turnPlayer);
			response = buildResponseChessboard(ResponseChessboard.StatusResponse.CHECKMATE);
		} catch (CheckStateException e) {
			listPiecesEnemyDoCheck = chessboard.getPiecesEnemyDoCheck(turnPlayer);
			response = buildResponseChessboard(ResponseChessboard.StatusResponse.IN_CHECK);
		}
		this.clearAllFields();
		return response;
	}

	@Deprecated
	public ResponseChessboard nextMove(PositionChessboard pos) {
		ResponseChessboard response = selectAndMove(pos, turnPlayer);
		this.printInfoResponse(response);
		if(response.getStatusResponse() == ResponseChessboard.StatusResponse.MOVED)
			chessboard.printDebugChessboard(chessboard, "Game Chess turn... player now: " + turnPlayer);
		return response;
	}
	
	public ResponseChessboard selectAndMove(PositionChessboard pos, Player currentPlayerRequesting) {
		if(!this.playing) {
			return new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OFF)
					.build();
		}
		//valid position and player
		if(currentPlayerRequesting == null || pos == null)
			return new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.NONE_ACTION)
					.currentPlayer(currentPlayerRequesting)
					.turn(turnPlayer)
					.build();
		//valid currentPlayer and turn
		if(currentPlayerRequesting != null && 
				currentPlayerRequesting.getTypePlayer() != turnPlayer.getTypePlayer()) 
			return new ResponseChessboard.Builder()
					.status(ResponseChessboard.StatusResponse.OPPONENT_TURN) 
					.currentPlayer(currentPlayerRequesting)
					.turn(turnPlayer)
					.build();
		//validate promotion pawn
		if(this.chessboard.getPositionPromotionPawn() != null)
			return buildResponseChessboard(ResponseChessboard.StatusResponse.PAWN_PROMOTION);
		
		this.currentPlayerRequesting = currentPlayerRequesting;		
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
				//attempt away MOVED resulted state CHECK, then validate if IN_CHECK or CHECKMATE in the turn actual requesting
				response = this.verifyCheckmateValidator();
				//response = buildResponseChessboard(ResponseChessboard.StatusResponse.IN_CHECK);
			}			
		}else{
			response = executeClickPiece();
		}
		return response;
	}
	
	private ResponseChessboard executeClickPiece(){
		pieceClicked = null;
		if(squareClicked != null && squareClicked.getPosition() == positionSelected){
			//same piece clicked again? then ignore lists and clear all, mark off piece
			this.clearAllLists();
			ResponseChessboard response = buildResponseChessboard(ResponseChessboard.StatusResponse.MARK_OFF);
			squareClicked = null;
			return response;
		}
		//process lists available and to take for piece clicked
		squareClicked = this.chessboard.getSquareChessboard(positionSelected);
		if(!squareClicked.isAvailable() && !PieceUtils.isPieceOfEnemy(squareClicked, currentPlayerRequesting)){
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
		return buildResponseChessboard(ResponseChessboard.StatusResponse.NONE_ACTION);
	}

	private ResponseChessboard executeMovePiece() throws CheckMoveException, CheckStateException{
		if(listPositionsAvailable.contains(positionSelected)
				|| listPositionsToTake.contains(positionSelected)){			
			pieceGotten = this.chessboard.movePieceInTheChessboard(squareClicked.getPosition(), positionSelected, pieceClicked);			
			//validate promotion
			if(chessboard.getPositionPromotionPawn() != null){
				return buildResponseChessboard(ResponseChessboard.StatusResponse.PAWN_PROMOTION);
			}
			this.changeTurnPlayer();
			ResponseChessboard response = buildResponseChessboard(ResponseChessboard.StatusResponse.MOVED);
			this.clearAllFields();
			this.chessboard.play(turnPlayer.getTypePlayer());
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
	
	private void changeTurnPlayer(){
		if(turnPlayer.getTypePlayer() == chessboard.getPlayer1().getTypePlayer()){
			turnPlayer = chessboard.getPlayer2();
		}else{
			turnPlayer = chessboard.getPlayer1();
		}
	}
	
	public void printInfoResponse(ResponseChessboard response) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(response));
	}
	
	@Deprecated
	public void printInfoChessboard(Chessboard chessboard) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(chessboard.getSquaresChessboard()));
	}

	public String getInfoChessboardJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(chessboard.getSquaresChessboard());
	}
	
	public void printInfoChessboardJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(chessboard.getSquaresChessboard()));
	}
	
	public ResponseChessboard buildResponseChessboard(ResponseChessboard.StatusResponse status){
		return new ResponseChessboard.Builder()
				.status(status)
				.currentPlayer(currentPlayerRequesting)
				.squareClicked(squareClicked)
				.pieceClicked(pieceClicked)
				.positionSelected(positionSelected)
				.listPositionsAvailable(listPositionsAvailable)
				.listPositionsToTake(listPositionsToTake)
				.listPiecesEnemyDoCheck(listPiecesEnemyDoCheck)
				.pieceGotten(pieceGotten)
				.turn(turnPlayer)
				.build();
	}
}
