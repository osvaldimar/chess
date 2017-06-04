package com.chess.core;

import java.util.List;

import com.chess.core.client.PlayerMode;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.memory.ChessboardMemory;
import com.chess.core.model.LastMovement;
import com.chess.core.model.Piece;
import com.chess.core.model.Square;

public class ChessboardModel {
	private Square[][] squares;
	private PlayerMode player1;
	private PlayerMode player2;
	private List<Piece> listGraveyard;
	private Square lastSquarePiceMoved;
	private PositionChessboard positionPromotionPawn;
	
	private ChessboardMemory chessMemory;
	private LastMovement lastMovement;
	private int totalMovements;
	public Square[][] getSquares() {
		return squares;
	}
	public void setSquares(Square[][] squares) {
		this.squares = squares;
	}
	public PlayerMode getPlayer1() {
		return player1;
	}
	public void setPlayer1(PlayerMode player1) {
		this.player1 = player1;
	}
	public PlayerMode getPlayer2() {
		return player2;
	}
	public void setPlayer2(PlayerMode player2) {
		this.player2 = player2;
	}
	public List<Piece> getListGraveyard() {
		return listGraveyard;
	}
	public void setListGraveyard(List<Piece> listGraveyard) {
		this.listGraveyard = listGraveyard;
	}
	public Square getLastSquarePiceMoved() {
		return lastSquarePiceMoved;
	}
	public void setLastSquarePiceMoved(Square lastSquarePiceMoved) {
		this.lastSquarePiceMoved = lastSquarePiceMoved;
	}
	public PositionChessboard getPositionPromotionPawn() {
		return positionPromotionPawn;
	}
	public void setPositionPromotionPawn(PositionChessboard positionPromotionPawn) {
		this.positionPromotionPawn = positionPromotionPawn;
	}
	public ChessboardMemory getChessMemory() {
		return chessMemory;
	}
	public void setChessMemory(ChessboardMemory chessMemory) {
		this.chessMemory = chessMemory;
	}
	public LastMovement getLastMovement() {
		return lastMovement;
	}
	public void setLastMovement(LastMovement lastMovement) {
		this.lastMovement = lastMovement;
	}
	public int getTotalMovements() {
		return totalMovements;
	}
	public void setTotalMovements(int totalMovements) {
		this.totalMovements = totalMovements;
	}		
}