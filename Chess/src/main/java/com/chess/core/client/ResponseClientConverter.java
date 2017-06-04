package com.chess.core.client;

import java.util.Objects;
import java.util.Optional;

import com.chess.core.ResponseChessboard;
import com.chess.core.client.ResponseClient;
import com.chess.core.client.ResponseClient.Builder;

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
				.pieceClicked(Objects.nonNull(res.getPieceClicked()) ? res.getPieceClicked().getTypePiece().name() : null)
				.turn(res.getTurn().getTypePlayer().toString())
				.build();
		}else{
			Builder build = new ResponseClient.Builder()
				.status(res.getStatusResponse().toString())
				.currentPlayer(res.getCurrentPlayer().getTypePlayer().toString())
				.turn(res.getTurn().getTypePlayer().toString());
				if(res.getLastMovement() != null){
					build.idMovement(res.getLastMovement().getIdMovement())
					.nameMovement(res.getLastMovement().getName().toString())
					.movedFrom(res.getLastMovement().getMovedFrom().toString())
					.movedTo(res.getLastMovement().getMovedTo().toString());
					if(res.getLastMovement().getDestroyed() != null)
						build.destroyed(res.getLastMovement().getDestroyed().toString());
					if(res.getLastMovement().getCastlingFrom() != null)
						build.castlingFrom(res.getLastMovement().getCastlingFrom().toString());
					if(res.getLastMovement().getCastlingTo() != null)
						build.castlingTo(res.getLastMovement().getCastlingTo().toString());
				}
				responseClient = build.build();
		}		
		return responseClient;
	}
	
}
