package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.King;
import com.chess.core.model.Knight;
import com.chess.core.model.Player;



@RunWith(MockitoJUnitRunner.class)
public class KingCastlingTest {

	private Player player1;
	private Player player2;
	private Chessboard chessboard;
	private King king;
	
	@Before
	public void setUp(){
		player1 = new Player("Joao", 100L, TypePlayer.P1);
		player2 = new Player("Maria", 100L, TypePlayer.P2);
		chessboard = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
				TypeColor.BLACK, TypeColor.WHITE, player1, player2);
		king = new King(TypeColor.WHITE, player1);
	}
	
	@Test
	public void testMovimentAvailableCastlingKingAndRook(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);

		ResponseChessboard response = game.nextMove(E1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 0);
		response = game.nextMove(E1);
		
		chessboard.getSquareChessboard(D1).removePiece();
		response = game.nextMove(E1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		response = game.nextMove(E1);
		
		chessboard.getSquareChessboard(C1).removePiece();
		response = game.nextMove(E1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		response = game.nextMove(E1);
		
		chessboard.getSquareChessboard(B1).removePiece();
		response = game.nextMove(E1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 2);
		response = game.nextMove(E1);
		
		chessboard.getSquareChessboard(F1).removePiece();
		chessboard.getSquareChessboard(G1).removePiece();
		response = game.nextMove(E1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 4);
		response = game.nextMove(E1);
		
		chessboard.getSquareChessboard(H1).getPiece().incrementMovements();
		chessboard.walkPieceInTheChessboard(A1, B1);
		chessboard.printChessboard(chessboard, "Test castling WHITE rook H1 count movements, rook A1 moved B1");
		response = game.nextMove(E1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 2);
	}
	
	@Test
	public void testMovimentAvailableCastlingKingAndRookForBlack(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		
		//player1
		ResponseChessboard response = game.nextMove(A2);
		response = game.nextMove(A3);
		
		//player2
		response = game.nextMove(E8);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 0);
		response = game.nextMove(E8);
		
		chessboard.getSquareChessboard(D8).removePiece();
		response = game.nextMove(E8);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		response = game.nextMove(E8);
		
		chessboard.getSquareChessboard(C8).removePiece();
		response = game.nextMove(E8);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		response = game.nextMove(E8);
		
		chessboard.getSquareChessboard(B8).removePiece();
		response = game.nextMove(E8);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 2);
		response = game.nextMove(E8);
		
		chessboard.getSquareChessboard(F8).removePiece();
		chessboard.getSquareChessboard(G8).removePiece();
		response = game.nextMove(E8);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 4);
		response = game.nextMove(E8);
		
		chessboard.getSquareChessboard(H8).getPiece().incrementMovements();
		chessboard.walkPieceInTheChessboard(A8, B8);
		chessboard.printChessboard(chessboard, "Test castling BLACK rook H8 count movements, rook A8 moved B8");
		response = game.nextMove(E8);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(response.getListPositionsAvailable().size(), 2);
	}
	
	@Test
	public void testCantCastlingKingAndRook(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.getSquareChessboard(F1).removePiece();
		chessboard.getSquareChessboard(G1).removePiece();
		chessboard.getSquareChessboard(B1).removePiece();
		chessboard.getSquareChessboard(C1).removePiece();
		chessboard.getSquareChessboard(D1).removePiece();
		GameApplication game = new GameApplication(chessboard);		
		
		ResponseChessboard res = game.nextMove(E1);
		assertEquals(res.getListPositionsAvailable().size(), 4);
		res = game.nextMove(F1);		
		//player 2
		game.nextMove(A7);
		game.nextMove(A5);
		
		res = game.nextMove(F1);
		assertEquals(res.getListPositionsAvailable().size(), 2);
		res = game.nextMove(G1);
		assertEquals(chessboard.getSquareChessboard(H1).getPiece().getTypePiece(), TypePiece.ROOK);
	}
	
	@Test
	public void testDoingCastlingKingAndRook(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.getSquareChessboard(F1).removePiece();
		chessboard.getSquareChessboard(G1).removePiece();
		GameApplication game = new GameApplication(chessboard);		
		
		ResponseChessboard res = game.nextMove(E1);
		res = game.nextMove(G1);
		assertEquals(chessboard.getSquareChessboard(G1).getPiece().getTypePiece(), TypePiece.KING);
		assertEquals(chessboard.getSquareChessboard(F1).getPiece().getTypePiece(), TypePiece.ROOK);
		assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		
		chessboard.startGame();
		chessboard.getSquareChessboard(B1).removePiece();
		chessboard.getSquareChessboard(C1).removePiece();
		chessboard.getSquareChessboard(D1).removePiece();
		game = new GameApplication(chessboard);		
		
		res = game.nextMove(E1);
		res = game.nextMove(C1);
		assertEquals(chessboard.getSquareChessboard(C1).getPiece().getTypePiece(), TypePiece.KING);
		assertEquals(chessboard.getSquareChessboard(D1).getPiece().getTypePiece(), TypePiece.ROOK);
		assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);		
	}
	
	@Test
	public void testCantDoCastlingBecauseExposedCheckKingAndRook(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.getSquareChessboard(F1).removePiece();
		chessboard.getSquareChessboard(G1).removePiece();
		chessboard.getSquareChessboard(B8).removePiece();
		chessboard.positionPiece(H3, new Knight(TypeColor.BLACK, player2));
		GameApplication game = new GameApplication(chessboard);		
		
		ResponseChessboard res = game.nextMove(E1);
		res = game.nextMove(G1);
		assertEquals(chessboard.getSquareChessboard(E1).getPiece().getTypePiece(), TypePiece.KING);
		assertEquals(chessboard.getSquareChessboard(H1).getPiece().getTypePiece(), TypePiece.ROOK);
		assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.EXPOSED_CHECK);		
	}
	
	@Test
	public void testCantDoCastlingBecauseKingIsCheck(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.getSquareChessboard(F1).removePiece();
		chessboard.getSquareChessboard(G1).removePiece();
		chessboard.getSquareChessboard(B8).removePiece();
		chessboard.positionPiece(F3, new Knight(TypeColor.BLACK, player2));
		GameApplication game = new GameApplication(chessboard);		
		
		ResponseChessboard res = game.nextMove(E1);
		assertEquals(res.getListPositionsAvailable().size(), 1);
		res = game.nextMove(G1);
		assertEquals(chessboard.getSquareChessboard(E1).getPiece().getTypePiece(), TypePiece.KING);
		assertEquals(chessboard.getSquareChessboard(H1).getPiece().getTypePiece(), TypePiece.ROOK);
		assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.NONE);
	}
	
}
