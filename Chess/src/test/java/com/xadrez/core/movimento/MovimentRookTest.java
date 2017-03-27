package com.xadrez.core.movimento;

import static com.chess.core.enums.PositionChessboard.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Chessboard;
import com.chess.core.model.Player;
import com.chess.core.model.Rook;
import com.xadrez.core.GameApplication;
import com.xadrez.core.ResponseChessboard;

@RunWith(MockitoJUnitRunner.class)
public class MovimentRookTest {

	private Player player1;
	private Player player2;
	private Chessboard chessboard;
	private Rook rook;
	
	@Before
	public void setUp(){
		this.player1 = new Player("Joao", 100L, TypePlayer.P1);
		this.player2 = new Player("Maria", 100L, TypePlayer.P2);
		this.chessboard = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
				TypeColor.BLACK, TypeColor.WHITE, player1, player2);
		rook = new Rook(TypeColor.WHITE, this.player1);
	}
	
	@Test
	public void testMovimentRookToFront(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(A2).removePiece();
		GameApplication game = new GameApplication(chessboard);	
		
		ResponseChessboard response = game.nextMove(A1);
		assertEquals(response.getListPositionsToTake().size(), 1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		response = game.nextMove(A1);
		assertEquals(response.getListPositionsToTake().size(), 0);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLEAR);
		
		response = game.nextMove(A7);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE);
		
		response = game.nextMove(A1);
		assertEquals(response.getListPositionsToTake().size(), 1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		response = game.nextMove(B2);
		assertEquals(response.getListPositionsToTake().size(), 0);
		assertEquals(response.getListPositionsAvailable().size(), 2);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);		
		
	}
	
	@Test
	public void testMovimentRookToTakeAnotherPiece(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(A1).removePiece();		
		chessboard.positionPiece(C3, rook);
		GameApplication game = new GameApplication(chessboard);	
		
		ResponseChessboard res = game.nextMove(C3);
		assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		assertEquals(res.getListPositionsAvailable().size(), 10);
		assertEquals(res.getListPositionsToTake().size(), 1);
		
		res = game.nextMove(C7);
		assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		assertNotNull(res.getPieceGotten());
		
	}
	
	
}
