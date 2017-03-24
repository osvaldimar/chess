package com.xadrez.core;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.Piece;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class ResponseMove {

	private PositionChessboard positionClicked;
	private Player currentPlayer;
	private Square squareClicked;
	private List<PositionChessboard> listPositionsAvailable = new ArrayList<>();
	private List<PositionChessboard> listPositionsToTake = new ArrayList<>();
	private Piece pieceClicked;
	private Piece pieceGotten;
	
	public ResponseMove(PositionChessboard positionClicked, Piece pieceClicked, Piece pieceGotten, Player currentPlayer,
			Square squareClicked, List<PositionChessboard> listPositionsAvailable, List<PositionChessboard> listPositionsToTake){
				this.positionClicked = positionClicked;
				this.pieceClicked = pieceClicked;
				this.pieceGotten = pieceGotten;
				this.currentPlayer = currentPlayer;
				this.squareClicked = squareClicked;
				this.listPositionsAvailable = listPositionsAvailable;
				this.listPositionsToTake = listPositionsToTake;		
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public Square getSquareClicked() {
		return squareClicked;
	}
	public List<PositionChessboard> getListPositionsAvailable() {
		return listPositionsAvailable;
	}
	public List<PositionChessboard> getListPositionsToTake() {
		return listPositionsToTake;
	}
	public Piece getPieceClicked() {
		return pieceClicked;
	}
	public PositionChessboard getPositionClicked() {
		return positionClicked;
	}
	public Piece getPieceGotten() {
		return pieceGotten;
	}	
}
