package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.B1;
import static com.chess.core.enums.PositionChessboard.C6;
import static com.chess.core.enums.PositionChessboard.D8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Knight;
import com.chess.core.model.Player;


@RunWith(MockitoJUnitRunner.class)
public class KnightTest {

	private Player player1;
	private Player player2;
	private Chessboard chessboard;
	private Knight knight;
	
	@Before
	public void setUp(){
		player1 = new Player("Joao", 100L, TypePlayer.W);
		player2 = new Player("Maria", 100L, TypePlayer.B);
		chessboard = new Chessboard(player1, player2);
		knight = new Knight(TypeColor.WHITE, player1.getTypePlayer());
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
		chessboard.getSquareChessboard(B1).removePiece();		
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
