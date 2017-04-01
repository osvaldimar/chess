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
	
	private ResponseChessboard(){}
	
	public ResponseChessboard(StatusResponse statusResponse, Player currentPlayer) {
		this.statusResponse = statusResponse;
		this.currentPlayer = currentPlayer;
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
		
	public static class Builder {
		private ResponseChessboard response = new ResponseChessboard();
		
		public Builder status(StatusResponse status){
			response.statusResponse = status;
			return this;
		}
		public Builder currentPlayer(Player currentPlayer){
			response.currentPlayer = currentPlayer;
			return this;
		}
		public Builder squareClicked(Square squareClicked){
			response.squareClicked = squareClicked;
			return this;
		}
		public Builder positionSelected(PositionChessboard positionSelected){
			response.positionSelected = positionSelected;
			return this;
		}
		public Builder pieceClicked(Piece pieceClicked){
			response.pieceClicked = pieceClicked;
			return this;
		}
		public Builder listPositionsAvailable(List<PositionChessboard> listPositionsAvailable){
			response.listPositionsAvailable = listPositionsAvailable;
			return this;
		}
		public Builder listPositionsToTake(List<PositionChessboard> listPositionsToTake){
			response.listPositionsToTake = listPositionsToTake;
			return this;
		}
		public Builder listPiecesEnemyDoCheck(List<Piece> listPiecesEnemyDoCheck){
			response.listPiecesEnemyDoCheck = listPiecesEnemyDoCheck;
			return this;
		}
		public Builder pieceGotten(Piece pieceGotten){
			response.pieceGotten = pieceGotten;
			return this;
		}		
		public ResponseChessboard build(){
			return response;
		}
	}
}
