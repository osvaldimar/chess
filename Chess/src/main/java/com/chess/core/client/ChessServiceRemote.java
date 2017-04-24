package com.chess.core.client;

import com.chess.core.GameApplication;
import com.chess.core.memory.ChessboardMemory;

public interface ChessServiceRemote {

	String startChess();

	ResponseClient selectAndMovePiece(String positionOriginOrDestiny, String currentPlayerRequesting);
	
	ResponseClient verifyCheckmateTurn();
	
	ResponseClient choosePromotion(String promotedPiece, String currentPlayerRequesting);
	
	void printInfoResponseJson(String response);
	
	String getSquaresChessboardJson();

	void printSquaresChessboardJson();
	
	String getLayoutChessboard();
	
	void printLayoutChessboard();
	
	int getTotaMovementsGameChess();

	void play(GameApplication game);
	
	ChessboardMemory getChessboardMemory();
}
