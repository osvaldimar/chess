package com.chess.core.client;

public class ResponseClient {

	private String status;
	private String currentPlayer;
	private String positionSelected;
	private String pieceClicked;
	private String[] positionsAvailable;
	private String[] positionsToTake;
	private String pieceGotten;
	private String winner;
	private String turn;
	
	private String keyClientID;
	private String keyClientType;
	
	private Integer idMovement;
	private String nameMovement;
	private String destroyed;
	private String movedFrom;
	private String movedTo;
	private String castlingFrom;
	private String castlingTo;
	
	private ResponseClient(){}
	
	public String getStatus() {
		return status;
	}
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	public String getPositionSelected() {
		return positionSelected;
	}
	public String getPieceClicked() {
		return pieceClicked;
	}
	public String[] getPositionsAvailable() {
		return positionsAvailable;
	}
	public String[] getPositionsToTake() {
		return positionsToTake;
	}
	public String getPieceGotten() {
		return pieceGotten;
	}
	public String getWinner() {
		return winner;
	}
	public String getTurn() {
		return turn;
	}
	

	public String getKeyClientID() {
		return keyClientID;
	}

	public String getKeyClientType() {
		return keyClientType;
	}

	public Integer getIdMovement() {
		return idMovement;
	}

	public String getNameMovement() {
		return nameMovement;
	}

	public String getDestroyed() {
		return destroyed;
	}

	public String getMovedFrom() {
		return movedFrom;
	}

	public String getMovedTo() {
		return movedTo;
	}

	public String getCastlingFrom() {
		return castlingFrom;
	}

	public String getCastlingTo() {
		return castlingTo;
	}


	public static class Builder{
		private ResponseClient response = new ResponseClient();
		
		public Builder status(String status){
			response.status = status;
			return this;
		}
		public Builder currentPlayer(String currentPlayer){
			response.currentPlayer = currentPlayer;
			return this;
		}
		public Builder positionSelected(String positionSelected){
			response.positionSelected = positionSelected;
			return this;
		}
		public Builder pieceClicked(String pieceClicked){
			response.pieceClicked = pieceClicked;
			return this;
		}
		public Builder positionsAvailable(String[] positionsAvailable){
			response.positionsAvailable = positionsAvailable;
			return this;
		}
		public Builder positionsToTake(String[] positionsToTake){
			response.positionsToTake = positionsToTake;
			return this;
		}
		public Builder pieceGotten(String pieceGotten){
			response.pieceGotten = pieceGotten;
			return this;
		}
		public Builder winner(String winner){
			response.winner = winner;
			return this;
		}
		public Builder turn(String turn){
			response.turn = turn;
			return this;
		}
		public Builder keyClientID(String keyClientID){
			response.keyClientID = keyClientID;
			return this;
		}
		public Builder keyClientType(String keyClientType){
			response.keyClientType = keyClientType;
			return this;
		}
		public Builder idMovement(Integer idMovement){
			response.idMovement = idMovement;
			return this;
		}		
		public Builder nameMovement(String nameMovement){
			response.nameMovement = nameMovement;
			return this;
		}
		public Builder destroyed(String destroyed){
			response.destroyed = destroyed;
			return this;
		}
		public Builder movedFrom(String movedFrom){
			response.movedFrom = movedFrom;
			return this;
		}
		public Builder movedTo(String movedTo){
			response.movedTo = movedTo;
			return this;
		}
		public Builder castlingFrom(String castlingFrom){
			response.castlingFrom = castlingFrom;
			return this;
		}
		public Builder castlingTo(String castlingTo){
			response.castlingTo = castlingTo;
			return this;
		}
		public ResponseClient build(){
			return response;
		}
	}

}
