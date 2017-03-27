package com.xadrez.core.movimento;

import static com.chess.core.enums.PositionChessboard.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Chessboard;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;
import com.xadrez.core.GameApplication;
import com.xadrez.core.ResponseChessboard;

@RunWith(MockitoJUnitRunner.class)
public class MovimentPawnTest {

	private Player player1;
	private Player player2;
	private Chessboard chessboard;
	private Pawn pawn;
	
	@Before
	public void setUp(){
		this.player1 = new Player("Joao", 100L, TypePlayer.P1);
		this.player2 = new Player("Maria", 100L, TypePlayer.P2);
		chessboard = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
				TypeColor.BLACK, TypeColor.WHITE, player1, player2);
		pawn = new Pawn(TypeColor.WHITE, this.player1);
	}
	
	/**
	 * Tests: 	position piece in the chessboard <br>
	 * 			start game <br>
	 * 			available movement front for pawn
	 */
	@Test
	public void testMovementOfPawnToFront(){
		System.out.println("\n------------------------------------------------------------------------------");
		assertNull(chessboard.squaresChessboard(A2).getPiece());
		chessboard.positionPiece(A2, pawn);
		assertNotNull(chessboard.squaresChessboard(A2).getPiece());
		chessboard.printChessboard(chessboard, "position");
		
		chessboard.startGame();
		chessboard.printChessboard(chessboard, "start game");
				
		List<PositionChessboard> listMovement = pawn.movementAvailable(B2, chessboard.squaresChessboard());
		System.out.println("\n" + listMovement);
		assertEquals(listMovement.get(0), B3);
		assertEquals(listMovement.get(1), B4);
		assertEquals(listMovement.size(), 2);
		chessboard.printChessboard(chessboard, "movement to front for pawn");		
				
	}
	
	/**
	 * Tests: 	available movement takes diagonal left and right for pawn
	 */
	@Test
	public void testMovementOfPawnToTakesPieces(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		chessboard.squaresChessboard()[D3.getLetter()][D3.getNumber()].addPiece(chessboard.squaresChessboard(D7).getPiece());
		chessboard.squaresChessboard(D7).removePiece();
		chessboard.squaresChessboard()[B3.getLetter()][B3.getNumber()].addPiece(chessboard.squaresChessboard(B7).getPiece());
		chessboard.squaresChessboard(B7).removePiece();
		
		List<PositionChessboard> listTakes = pawn.movementAvailableToTakePieces(C2, chessboard.squaresChessboard());
		System.out.println("\n" + listTakes);
		assertEquals(listTakes.size(), 2);
		chessboard.printChessboard(chessboard, "movement to takes for pawn");	
	}
	
	@Test
	public void testPositionPieceInTheChessboardAvailable(){		
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);		
		ResponseChessboard response = game.nextMove(PositionChessboard.E2);
		assertEquals(response.getListPositionsAvailable().size(), 2);
		assertEquals(response.getListPositionsToTake().size(), 0);
	}
	
	@Test
	public void testPositionPieceInTheChessboardGottenPiece(){		
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		chessboard.squaresChessboard()[G3.getLetter()][G3.getNumber()].addPiece(chessboard.squaresChessboard(G7).getPiece());
		chessboard.squaresChessboard(G7).removePiece();
		GameApplication game = new GameApplication(chessboard);
		
		//click piece B2 pawn
		ResponseChessboard response = game.nextMove(PositionChessboard.B2);
		assertNotNull(response);
				
		//click piece B2 pawn same piece then clear
		response = game.nextMove(PositionChessboard.B2);
		assertEquals(response.getListPositionsAvailable().size(), 0);
		
		//click H2 then return lists
		response = game.nextMove(PositionChessboard.H2);
		assertEquals(response.getListPositionsAvailable().size(), 2);
		assertEquals(response.getListPositionsToTake().size(), 1);
		
		//click G3 then move H2 to G3 and take piece of enemy
		response = game.nextMove(PositionChessboard.G3);
		assertNotNull(response);
		assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		
		//click piece A2, but turn is player2
		response = game.nextMove(PositionChessboard.A2);
		assertNotNull(response);
		
		//click piece A7, then turn is player2
		response = game.nextMove(PositionChessboard.A7);
		assertNotNull(response);
	}
	
	
	
	
}
