package com.chess.core;

import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.Piece;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class ResponseChessboard {

	private StatusResponse statusResponse;
	private Player currentPlayer;
	private Square squareClicked;
	private PositionChessboard positionSelected;
	private Piece pieceClicked;
	private List<PositionChessboard> listPositionsAvailable;
	private List<PositionChessboard> listPositionsToTake;
	private List<Piece> listPiecesEnemyDoCheck;
	private Piece pieceGotten;
	
	public enum StatusResponse{
		CLICKED, MOVED, NONE, CLEAR, EXPOSED_CHECK, CHECK, CHECKMATE, NONE_CHECK;
	}
	
	public ResponseChessboard(StatusResponse statusResponse, Player currentPlayer, List<Piece> listPiecesEnemyDoCheck) {
		this.statusResponse = statusResponse;
		this.currentPlayer = currentPlayer;
		this.listPiecesEnemyDoCheck = listPiecesEnemyDoCheck;
	}
	
	public ResponseChessboard(StatusResponse typeResponse, PositionChessboard positionClicked, Square squareClicked, Player currentPlayer){
		this.statusResponse = typeResponse;
		this.positionSelected = positionClicked;
		this.squareClicked = squareClicked;
		this.currentPlayer = currentPlayer;
	}
	
	public ResponseChessboard(StatusResponse typeResponse, PositionChessboard positionClicked, Piece pieceClicked, Piece pieceGotten, Player currentPlayer,
			Square squareClicked, List<PositionChessboard> listPositionsAvailable, List<PositionChessboard> listPositionsToTake){
				this.statusResponse = typeResponse;
				this.positionSelected = positionClicked;
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
	public List<Piece> getListPiecesEnemyDoCheck() {
		return listPiecesEnemyDoCheck;
	}
	public Piece getPieceClicked() {
		return pieceClicked;
	}
	public PositionChessboard getPositionClicked() {
		return positionSelected;
	}
	public Piece getPieceGotten() {
		return pieceGotten;
	}

	public StatusResponse getStatusResponse() {
		return statusResponse;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("STATUS ");
		builder.append(getStatusResponse() + ": ");
		builder.append(" - Square clicked: " + getSquareClicked());
		builder.append(" - Position selected: " + getPositionClicked());
		builder.append(" - Piece gotten: " + getPieceGotten() + 
				(getPieceGotten() != null ? "-"+getPieceGotten().getPlayer().getTypePlayer() : ""));
		builder.append(" - Current player: " + currentPlayer);
		if(getListPositionsAvailable() != null || getListPositionsToTake()!= null){
			builder.append("\nList positions available: " + getListPositionsAvailable());
			builder.append("\nList positions to take: " + getListPositionsToTake());
		}
		return builder.toString();
	}
}
