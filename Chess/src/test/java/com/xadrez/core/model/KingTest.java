package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.E1;
import static com.chess.core.enums.PositionChessboard.E4;
import static com.chess.core.enums.PositionChessboard.E5;
import static com.chess.core.enums.PositionChessboard.E7;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.King;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;
import com.chess.core.util.PieceUtils;



@RunWith(MockitoJUnitRunner.class)
public class KingTest {

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
	public void testMovimentKingPlaces(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(E1).removePiece();		
		chessboard.positionPiece(E4, king);
		GameApplication game = new GameApplication(chessboard);	

		ResponseChessboard response = game.nextMove(E4);
		assertEquals(response.getListPositionsAvailable().size(), 8);
		assertEquals(response.getListPositionsToTake().size(), 0);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
	}
	
	@Test
	public void testMovimentKingToTakeAnotherPiece(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(E1).removePiece();
		chessboard.squaresChessboard(E7).removePiece();
		chessboard.positionPiece(E4, king);
		chessboard.positionPiece(E5, new Pawn(TypeColor.BLACK, player2));
		GameApplication game = new GameApplication(chessboard);	

		ResponseChessboard response = game.nextMove(E4);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		response = game.nextMove(E5);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		assertNotNull(response.getPieceGotten());
		
	}
	
	@Test
	public void testGetPositionOfKingChessboard(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();		
		PositionChessboard p1 = PieceUtils.getPositionKingInChessboard(chessboard.getCloneSquaresChessboard(), player1);
		Assert.assertEquals(p1, PositionChessboard.E1);
		PositionChessboard p2 = PieceUtils.getPositionKingInChessboard(chessboard.getCloneSquaresChessboard(), player2);
		Assert.assertEquals(p2, PositionChessboard.E8);
		chessboard.printChessboard(chessboard, "Test getPositionKingInChessboard()");		
	}
}
