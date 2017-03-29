package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.A7;
import static com.chess.core.enums.PositionChessboard.A8;
import static com.chess.core.enums.PositionChessboard.D1;
import static com.chess.core.enums.PositionChessboard.D4;
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
import com.chess.core.model.Player;
import com.chess.core.model.Queen;


@RunWith(MockitoJUnitRunner.class)
public class QueenTest {

	private Player player1;
	private Player player2;
	private Chessboard chessboard;
	private Queen queen;
	
	@Before
	public void setUp(){
		player1 = new Player("Joao", 100L, TypePlayer.P1);
		player2 = new Player("Maria", 100L, TypePlayer.P2);
		chessboard = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
				TypeColor.BLACK, TypeColor.WHITE, player1, player2);
		queen = new Queen(TypeColor.WHITE, player1);
	}

	@Test
	public void testMovimentQueenPlaces(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(D1).removePiece();		
		chessboard.positionPiece(D4, queen);
		GameApplication game = new GameApplication(chessboard);	

		ResponseChessboard response = game.nextMove(D4);
		assertEquals(response.getListPositionsAvailable().size(), 16);
		assertEquals(response.getListPositionsToTake().size(), 3);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
	}
	
	@Test
	public void testMovimentQueenToTakeAnotherPiece(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(D1).removePiece();		
		chessboard.positionPiece(D4, queen);
		GameApplication game = new GameApplication(chessboard);	

		ResponseChessboard response = game.nextMove(D4);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		response = game.nextMove(A7);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		assertNotNull(response.getPieceGotten());
		
		response = game.nextMove(A8);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		response = game.nextMove(A7);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		assertNotNull(response.getPieceGotten());
		
	}
	
	
}
