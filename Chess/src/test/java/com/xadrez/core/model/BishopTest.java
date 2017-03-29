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
import com.chess.core.model.Bishop;
import com.chess.core.model.Chessboard;
import com.chess.core.model.Player;
import com.xadrez.core.GameApplication;
import com.xadrez.core.ResponseChessboard;


@RunWith(MockitoJUnitRunner.class)
public class BishopTest {

	private Player player1;
	private Player player2;
	private Chessboard chessboard;
	private Bishop bishop;
	
	@Before
	public void setUp(){
		player1 = new Player("Joao", 100L, TypePlayer.P1);
		player2 = new Player("Maria", 100L, TypePlayer.P2);
		chessboard = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
				TypeColor.BLACK, TypeColor.WHITE, player1, player2);
		bishop = new Bishop(TypeColor.WHITE, player1);
	}

	@Test
	public void testMovimentBishopToFront(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);	
		
		ResponseChessboard response = game.nextMove(C1);
		assertEquals(response.getListPositionsToTake().size(), 0);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		response = game.nextMove(C1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLEAR);
		
		chessboard.squaresChessboard(D2).removePiece();
		response = game.nextMove(C1);
		assertEquals(response.getListPositionsAvailable().size(), 5);
		assertEquals(response.getListPositionsToTake().size(), 0);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
	}
	
	@Test
	public void testMovimentBishopToTakeAnotherPiece(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(C1).removePiece();		
		chessboard.positionPiece(C3, bishop);
		GameApplication game = new GameApplication(chessboard);	
		
		ResponseChessboard res = game.nextMove(C3);
		assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(res.getListPositionsAvailable().size(), 5);
		assertEquals(res.getListPositionsToTake().size(), 1);
		
		res = game.nextMove(G7);
		assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		assertNotNull(res.getPieceGotten());
		
	}
	
	
}
