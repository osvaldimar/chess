package com.xadrez.core;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.Chessboard;
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
	}

	public ResponseMove nextMove(PositionChessboard pos) {
		if(pieceClicked != null){
			ResponseMove response = executeMovePiece(pos);
			if(response == null){
				return executeClickPiece(pos);
			}
			return response;
		}else{
			return executeClickPiece(pos);
		}
	}
	
	private ResponseMove executeClickPiece(PositionChessboard pos){
		pieceClicked = null;
		squareClicked = this.chessboard.squaresChessboard(pos);
		if(!squareClicked.isAvailable() && !PieceUtils.isPieceOfEnemy(squareClicked, currentPlayer)){
			pieceClicked = squareClicked.getPiece();
			listPositionsAvailable = pieceClicked.movementAvailable(pos, this.chessboard.squaresChessboard());
			listPositionsToTake = pieceClicked.movementAvailableToTakePieces(pos, this.chessboard.squaresChessboard());
			return new ResponseMove(pos, pieceClicked, null, currentPlayer, squareClicked, 
					listPositionsAvailable, listPositionsToTake);
		}		
		return null;
	}
	
	private ResponseMove executeMovePiece(PositionChessboard pos){
		if(listPositionsAvailable.contains(pos)){
			Piece gotten = this.chessboard.positionPiece(pos, pieceClicked);
			if(gotten != null){
				throw new RuntimeException("Square marked as available, but piece gotten(" +gotten+ ") should is null");
			}
			return buildResponseMove(pos, gotten);
		}
		if(listPositionsToTake.contains(pos)){
			Piece gotten = this.chessboard.positionPiece(pos, pieceClicked);
			if(gotten == null){
				throw new RuntimeException("Square marked as piece to take, but square not have anything piece");
			}			
			return buildResponseMove(pos, gotten);
		}
		return null;
	}
	
	private ResponseMove buildResponseMove(PositionChessboard pos, Piece gotten) {
		ResponseMove response = new ResponseMove(pos, pieceClicked, gotten, currentPlayer, squareClicked, 
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

}
