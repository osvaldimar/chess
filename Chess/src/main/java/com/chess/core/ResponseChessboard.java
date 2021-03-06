package com.chess.core;

import java.util.List;

import com.chess.core.client.PlayerMode;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.LastMovement;
import com.chess.core.model.Piece;
import com.chess.core.model.Square;

public class ResponseChessboard {

	private StatusResponse statusResponse;
	private PlayerMode currentPlayer;
	private Square squareClicked;
	private PositionChessboard positionSelected;
	private Piece pieceClicked;
	private List<PositionChessboard> listPositionsAvailable;
	private List<PositionChessboard> listPositionsToTake;
	private List<Piece> listPiecesEnemyDoCheck;
	private Piece pieceGotten;
	private PlayerMode winner;
	private PlayerMode turn;
	private LastMovement lastMovement;
	
	public enum StatusResponse{
		START, OFF, NONE_ACTION, OPPONENT_TURN, CLICKED, MOVED, PAWN_PROMOTION, MARK_OFF, 
		NONE_CHECK, EXPOSED_CHECK, IN_CHECK, CHECKMATE, DRAW_STALEMATE, DRAW_50_MOVEMENTS, DRAW_3_POSITIONS, INVALID;
	}
	
	private ResponseChessboard(){}

	public PlayerMode getCurrentPlayer() {
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
	public PositionChessboard getPositionSelected() {
		return positionSelected;
	}
	public Piece getPieceGotten() {
		return pieceGotten;
	}
	public StatusResponse getStatusResponse() {
		return statusResponse;
	}
	public PlayerMode getWinner() {
		return winner;
	}
	public PlayerMode getTurn() {
		return turn;
	}
	public LastMovement getLastMovement() {
		return lastMovement;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("STATUS ");
		builder.append(getStatusResponse() + ": ");
		builder.append(" - Square clicked: " + getSquareClicked());
		builder.append(" - Position selected: " + getPositionSelected());
		builder.append(" - Piece gotten: " + getPieceGotten() + 
				(getPieceGotten() != null ? "-"+getPieceGotten().getPlayer() : ""));
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
		public Builder currentPlayer(PlayerMode currentPlayer){
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
		public Builder turn(PlayerMode turnPlayer) {
			response.turn = turnPlayer;
			return this;
		}
		public Builder lastMovement(LastMovement lastMovement) {
			response.lastMovement = lastMovement;
			return this;
		}
		public ResponseChessboard build(){
			if(response.statusResponse == StatusResponse.CHECKMATE) 
				response.winner = response.currentPlayer;
			return response;
		}
	}
}
