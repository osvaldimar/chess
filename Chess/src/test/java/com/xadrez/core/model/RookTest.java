package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.A1;
import static com.chess.core.enums.PositionChessboard.A2;
import static com.chess.core.enums.PositionChessboard.A7;
import static com.chess.core.enums.PositionChessboard.B2;
import static com.chess.core.enums.PositionChessboard.C3;
import static com.chess.core.enums.PositionChessboard.C7;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;
import com.chess.core.model.Rook;


public class RookTest {

	private Player player1 = new Player("Joao", 100L, TypePlayer.W);
	private Player player2 = new Player("Maria", 100L, TypePlayer.B);
	private Chessboard chessboard = new Chessboard(player1, player2);
	private Rook rook = new Rook(TypeColor.WHITE, this.player1.getTypePlayer());
	

	@Test
	public void testMovimentRookToFront(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.getSquareChessboard(A2).removePiece();
		GameApplication game = new GameApplication(chessboard);	
		
		ResponseChessboard response = game.nextMove(A1);
		assertEquals(response.getListPositionsToTake().size(), 1);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		response = game.nextMove(A1);
		assertNull(response.getListPositionsToTake());
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MARK_OFF);
		
		response = game.nextMove(A7);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_ACTION);
		
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
		chessboard.getSquareChessboard(A1).removePiece();		
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
