package com.chess.core.service;

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
		public ResponseClient build(){
			return response;
		}
	}

}
