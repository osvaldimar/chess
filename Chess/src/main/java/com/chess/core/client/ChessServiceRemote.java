package com.chess.core.client;

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
}
