package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;
import com.chess.core.model.Queen;
import com.chess.core.model.Rook;

@RunWith(MockitoJUnitRunner.class)
public class PawnPromotionTest {

	private Player player1 = new Player("Joao", 100L, TypePlayer.W);
	private Player player2 =  new Player("Maria", 100L, TypePlayer.B);
	private Chessboard chessboard = new Chessboard(player1, player2);

	
	@Test
	public void testPromotionPawnBlack(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		chessboard.getSquareChessboard(A2).removePiece();
		chessboard.getSquareChessboard(B2).removePiece();
		chessboard.getSquareChessboard(C2).removePiece();
		chessboard.getSquareChessboard(A1).removePiece();
		chessboard.getSquareChessboard(B1).removePiece();
		chessboard.getSquareChessboard(C1).removePiece();
		chessboard.getSquareChessboard(D1).removePiece();
		chessboard.walkPieceInTheChessboard(B7, B2);
		chessboard.getSquareChessboard(B2).getPiece().incrementMovements();
		chessboard.printDebugChessboard(chessboard, "promotion test init");
		
		//player1
		ResponseChessboard response = game.selectAndMove(D2, player1);
		response = game.selectAndMove(D4, player1);		
		//player2
		response = game.selectAndMove(B2, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		
		response = game.selectAndMove(B1, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.PAWN_PROMOTION);
		assertEquals(response.getTurn().getTypePlayer(), TypePlayer.B);
		chessboard.printDebugChessboard(chessboard, "promotion test - MOVED_PROMOTION");
		
		response = game.selectAndMove(E2, player1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.OPPONENT_TURN);
		assertEquals(response.getTurn().getTypePlayer(), TypePlayer.B);
		
		response = game.selectAndMove(E7, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.PAWN_PROMOTION);
		
		//promotion
		response = game.executePromotion(TypePiece.QUEEN, player1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.OPPONENT_TURN);
		
		response = game.executePromotion(null, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.PAWN_PROMOTION);
		
		response = game.executePromotion(TypePiece.QUEEN, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		chessboard.printDebugChessboard(chessboard, "promotion test - MOVED_PROMOTION");
		
		response = game.verifyCheckmateValidator();
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.IN_CHECK);
		assertEquals(response.getTurn().getTypePlayer(), TypePlayer.W);
	}
	
	@Test
	public void testPromotionPawnBlackWithCheckmateRook(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		chessboard.getSquareChessboard(A2).removePiece();
		chessboard.getSquareChessboard(B2).removePiece();
		chessboard.getSquareChessboard(C2).removePiece();
		chessboard.getSquareChessboard(A1).removePiece();
		chessboard.getSquareChessboard(B1).removePiece();
		chessboard.getSquareChessboard(C1).removePiece();
		chessboard.getSquareChessboard(D1).removePiece();
		chessboard.walkPieceInTheChessboard(B7, B2);
		chessboard.getSquareChessboard(B2).getPiece().incrementMovements();
		chessboard.printDebugChessboard(chessboard, "promotion test init");
		
		//player1
		ResponseChessboard response = game.selectAndMove(H2, player1);
		response = game.selectAndMove(H4, player1);		
		//player2
		response = game.selectAndMove(B2, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		
		response = game.selectAndMove(B1, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.PAWN_PROMOTION);
		assertEquals(response.getTurn().getTypePlayer(), TypePlayer.B);
		chessboard.printDebugChessboard(chessboard, "promotion test - MOVED_PROMOTION");		
		
		response = game.executePromotion(TypePiece.ROOK, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		chessboard.printDebugChessboard(chessboard, "promotion test - MOVED_PROMOTION ROOK");

		response = game.verifyCheckmateValidator();
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CHECKMATE);
		assertEquals(response.getTurn().getTypePlayer(), TypePlayer.W);
		chessboard.printDebugChessboard(chessboard, "promotion test with checkmate");
	}
	
	@Test
	public void testPromotionPawnWhite(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		chessboard.getSquareChessboard(B8).removePiece();
		chessboard.getSquareChessboard(A7).removePiece();
		chessboard.getSquareChessboard(B7).removePiece();
		chessboard.getSquareChessboard(C7).removePiece();
		chessboard.walkPieceInTheChessboard(B2, B7);
		chessboard.getSquareChessboard(B7).getPiece().incrementMovements();
		chessboard.printDebugChessboard(chessboard, "promotion test init");
		
		ResponseChessboard response = game.selectAndMove(B7, player1);
		response = game.selectAndMove(A8, player1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.PAWN_PROMOTION);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		assertEquals(response.getListPositionsToTake().size(), 2);
		chessboard.printDebugChessboard(chessboard, "promotion test - MOVED_PROMOTION");
		
		response = game.selectAndMove(E7, player2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.OPPONENT_TURN);
		
		response = game.selectAndMove(A8, player1);
		assertEquals(response.getTurn().getTypePlayer(), TypePlayer.W);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.PAWN_PROMOTION);
		
		response = game.executePromotion(TypePiece.ROOK, player1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		chessboard.printDebugChessboard(chessboard, "promotion test - moved");
	}
}
