package com.chess.core;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.Piece;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class ResponseChessboard {

	private PositionChessboard positionClicked;
	private Player currentPlayer;
	private Square squareClicked;
	private List<PositionChessboard> listPositionsAvailable = new ArrayList<>();
	private List<PositionChessboard> listPositionsToTake = new ArrayList<>();
	private Piece pieceClicked;
	private Piece pieceGotten;
	private StatusResponse statusResponse;
	
	public enum StatusResponse{
		CLICKED, MOVED, NONE, CLEAR, EXPOSED, CHECK, MATE;
	}
	
	public ResponseChessboard(StatusResponse typeResponse, PositionChessboard positionClicked, Square squareClicked, Player currentPlayer){
		this.statusResponse = typeResponse;
		this.positionClicked = positionClicked;
		this.squareClicked = squareClicked;
		this.currentPlayer = currentPlayer;
	}
	
	public ResponseChessboard(StatusResponse typeResponse, PositionChessboard positionClicked, Piece pieceClicked, Piece pieceGotten, Player currentPlayer,
			Square squareClicked, List<PositionChessboard> listPositionsAvailable, List<PositionChessboard> listPositionsToTake){
				this.statusResponse = typeResponse;
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

	public StatusResponse getStatusResponse() {
		return statusResponse;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("STATUS ");
		builder.append(getStatusResponse() + ": ");
		builder.append(" - Square clicked: " + getSquareClicked());
		builder.append(" - Position clicked: " + getPositionClicked());
		builder.append(" - Piece gotten: " + getPieceGotten() + 
				(getPieceGotten() != null ? "-"+getPieceGotten().getPlayer().getTypePlayer() : ""));
		builder.append(" - player: " + currentPlayer);
		builder.append("\nList positions available: " + getListPositionsAvailable());
		builder.append("\nList positions to take: " + getListPositionsToTake());
		return builder.toString();
	}
}
