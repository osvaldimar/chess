package com.chess.core.client;

import com.chess.core.GameApplication;
import com.chess.core.memory.ChessboardMemory;

public interface ChessServiceRemote {

	String startChessOfflineCommon();
	
	ResponseClient selectAndMovePiece(String positionOriginOrDestiny, String currentPlayerRequesting);
	
	ResponseClient verifyCheckmateTurn();
	
	ResponseClient verifyCheckmateTurn(String typePlayer);
	
	ResponseClient choosePromotion(String promotedPiece, String currentPlayerRequesting);
	
	ResponseClient clearPieceClickedMarkOff();
	
	void printInfoResponseJson(String response);
	
	String getSquaresChessboardJson();

	void printSquaresChessboardJson();
	
	String getLayoutChessboard();
	
	void printLayoutChessboard();
	
	int getTotaMovementsGameChess();

	void play(GameApplication game);
	
	ChessboardMemory getChessboardMemory();
}
