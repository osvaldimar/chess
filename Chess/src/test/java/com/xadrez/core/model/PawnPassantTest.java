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

@RunWith(MockitoJUnitRunner.class)
public class PawnPassantTest {

	private Player player1 = new Player("Joao", 100L, TypePlayer.P1_W);
	private Player player2 =  new Player("Maria", 100L, TypePlayer.P2_B);
	private Chessboard chessboard = new Chessboard(player1, player2);

	
	@Test
	public void testTakePawnElPassantWhite(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		
		chessboard.walkPieceInTheChessboard(A2, A4);
		chessboard.getSquareChessboard(A4).getPiece().incrementMovements();
		
		//player1
		ResponseChessboard response = game.nextMove(A4);
		response = game.nextMove(A5);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		assertEquals(chessboard.getLastSquarePiceMoved().getPosition(), A5);
		assertEquals(chessboard.getLastSquarePiceMoved().getPiece().getTypePiece(), TypePiece.PAWN);
		
		//player2
		response = game.nextMove(B7);
		response = game.nextMove(B5);
		assertEquals(chessboard.getLastSquarePiceMoved().getPosition(), B5);
		assertEquals(chessboard.getLastSquarePiceMoved().getPiece().getTypePiece(), TypePiece.PAWN);
		
		//player1
		response = game.nextMove(A5);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		assertEquals(response.getListPositionsToTake().size(), 1);
		assertEquals(response.getListPositionsToTake().get(0), B6);
		response = game.nextMove(B6);
		assertTrue(chessboard.getSquareChessboard(B5).isAvailable());
		assertFalse(chessboard.getSquareChessboard(B6).isAvailable());
	}
	
	@Test
	public void testTakePawnElPassantBlack(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		
		chessboard.walkPieceInTheChessboard(E7, E4);
		
		//player1
		ResponseChessboard response = game.nextMove(F2);
		response = game.nextMove(F4);
		
		//player2
		response = game.nextMove(E4);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		assertEquals(response.getListPositionsToTake().size(), 1);
		assertEquals(response.getListPositionsToTake().get(0), F3);
		response = game.nextMove(F3);
		assertTrue(chessboard.getSquareChessboard(F4).isAvailable());
		assertFalse(chessboard.getSquareChessboard(F3).isAvailable());
	}
	
	@Test
	public void testTakePawnElPassantBlackExposedCheck(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		
		chessboard.walkPieceInTheChessboard(E7, E4);	//pawn black
		chessboard.walkPieceInTheChessboard(E8, H4);	//king black
		chessboard.walkPieceInTheChessboard(A1, A4);	//rook white
		
		//player1
		ResponseChessboard response = game.nextMove(F2);
		response = game.nextMove(F4);
		
		//player2
		response = game.nextMove(E4);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		assertEquals(response.getListPositionsToTake().size(), 1);
		assertEquals(response.getListPositionsToTake().get(0), F3);
		response = game.nextMove(F3);
		assertFalse(chessboard.getSquareChessboard(F4).isAvailable());
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.EXPOSED_CHECK);
		
		response = game.nextMove(E3);
		assertFalse(chessboard.getSquareChessboard(E3).isAvailable());	//has pawn black
		assertTrue(chessboard.getSquareChessboard(F3).isAvailable());	//available
		assertFalse(chessboard.getSquareChessboard(F4).isAvailable());	//has pawn white
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
	}
	
	@Test
	public void testPawnElPassantButNotTakeAndWalkToFront(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		
		chessboard.walkPieceInTheChessboard(E7, E4);	//pawn black
		
		//player1
		ResponseChessboard response = game.nextMove(F2);
		response = game.nextMove(F4);
		
		//player2
		response = game.nextMove(E4);
		assertEquals(response.getListPositionsAvailable().size(), 1);
		assertEquals(response.getListPositionsAvailable().get(0), E3);
		assertEquals(response.getListPositionsToTake().size(), 1);
		assertEquals(response.getListPositionsToTake().get(0), F3);
		response = game.nextMove(E3);
		assertFalse(chessboard.getSquareChessboard(E3).isAvailable());	//has pawn black
		assertTrue(chessboard.getSquareChessboard(F3).isAvailable());	//available
		assertFalse(chessboard.getSquareChessboard(F4).isAvailable());	//has pawn white
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
	}
	
	
}
