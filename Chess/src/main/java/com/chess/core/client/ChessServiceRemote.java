package com.chess.core.client;

import java.util.UUID;

public interface ChessServiceRemote {

	String startChess();

	String selectAndMovePiece(String positionOriginOrDestiny, String currentPlayerRequesting);
	
	String verifyCheckmateTurn();
	
	String choosePromotion(String promotedPiece, String currentPlayerRequesting);
	
	void printInfoResponseJson(String response);
	
	String getSquaresChessboardJson();

	void printSquaresChessboardJson();
	
	String getLayoutChessboard();
	
	void printLayoutChessboard();
	
	UUID getUuidChess();
}
