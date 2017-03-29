package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Chessboard;
import com.chess.core.model.Knight;
import com.chess.core.model.Player;
import com.xadrez.core.GameApplication;
import com.xadrez.core.ResponseChessboard;


@RunWith(MockitoJUnitRunner.class)
public class KnightTest {

	private Player player1;
	private Player player2;
	private Chessboard chessboard;
	private Knight knight;
	
	@Before
	public void setUp(){
		player1 = new Player("Joao", 100L, TypePlayer.P1);
		player2 = new Player("Maria", 100L, TypePlayer.P2);
		chessboard = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
				TypeColor.BLACK, TypeColor.WHITE, player1, player2);
		knight = new Knight(TypeColor.WHITE, player1);
	}

	@Test
	public void testMovimentQueenPlaces(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);	

		ResponseChessboard response = game.nextMove(B1);
		assertEquals(response.getListPositionsAvailable().size(), 2);
		assertEquals(response.getListPositionsToTake().size(), 0);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
	}
	
	@Test
	public void testMovimentQueenToTakeAnotherPiece(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(B1).removePiece();		
		chessboard.positionPiece(C6, knight);
		GameApplication game = new GameApplication(chessboard);	

		ResponseChessboard response = game.nextMove(C6);
		assertEquals(response.getListPositionsAvailable().size(), 4);
		assertEquals(response.getListPositionsToTake().size(), 4);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		response = game.nextMove(D8);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		assertNotNull(response.getPieceGotten());
		
	}
	
	
}
