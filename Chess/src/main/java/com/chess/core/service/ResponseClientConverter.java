package com.chess.core.service;

import com.chess.core.ResponseChessboard;

public final class ResponseClientConverter {

	public static ResponseClient convert(ResponseChessboard res){
		ResponseClient responseClient = null;
		if(res.getStatusResponse() == ResponseChessboard.StatusResponse.CLICKED){
			responseClient = new ResponseClient.Builder()
				.status(res.getStatusResponse().toString())
				.currentPlayer(res.getCurrentPlayer().getTypePlayer().toString())
				.pieceClicked(res.getPieceClicked().getTypePiece().name())
				.positionsAvailable((String[]) res.getListPositionsAvailable().stream()
						.map(m -> m.toString()).toArray(String[]::new))
				.positionsToTake((String[]) res.getListPositionsToTake().stream()
						.map(m -> m.toString()).toArray(String[]::new))
				.positionSelected(res.getPositionSelected().toString())
				.turn(res.getTurn().getTypePlayer().toString())
				.build();
		}else if(res.getStatusResponse() == ResponseChessboard.StatusResponse.MOVED){
			responseClient = new ResponseClient.Builder()
				.status(res.getStatusResponse().toString())
				.currentPlayer(res.getCurrentPlayer().getTypePlayer().toString())
				.pieceClicked(res.getPieceClicked().getTypePiece().name())
				.positionsAvailable((String[]) res.getListPositionsAvailable().stream()
						.map(m -> m.toString()).toArray(String[]::new))
				.positionsToTake((String[]) res.getListPositionsToTake().stream()
						.map(m -> m.toString()).toArray(String[]::new))
				.positionSelected(res.getPositionSelected().toString())
				.pieceGotten(res.getPieceGotten() != null ? res.getPieceGotten().getTypePiece().toString() : null)
				.turn(res.getTurn().getTypePlayer().toString())
				.build();
		}else if(res.getStatusResponse() == ResponseChessboard.StatusResponse.PAWN_PROMOTION){
			responseClient = new ResponseClient.Builder()
				.status(res.getStatusResponse().toString())
				.currentPlayer(res.getCurrentPlayer().getTypePlayer().toString())
				.pieceClicked(res.getPieceClicked().getTypePiece().name())
				.turn(res.getTurn().getTypePlayer().toString())
				.build();
		}else{
			responseClient = new ResponseClient.Builder()
				.status(res.getStatusResponse().toString())
				.currentPlayer(res.getCurrentPlayer().getTypePlayer().toString())
				.turn(res.getTurn().getTypePlayer().toString())
				.build();
		}		
		return responseClient;		
	}
	
}
