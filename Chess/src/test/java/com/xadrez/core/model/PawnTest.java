package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.A2;
import static com.chess.core.enums.PositionChessboard.B2;
import static com.chess.core.enums.PositionChessboard.B3;
import static com.chess.core.enums.PositionChessboard.B4;
import static com.chess.core.enums.PositionChessboard.B7;
import static com.chess.core.enums.PositionChessboard.C2;
import static com.chess.core.enums.PositionChessboard.D3;
import static com.chess.core.enums.PositionChessboard.D7;
import static com.chess.core.enums.PositionChessboard.G3;
import static com.chess.core.enums.PositionChessboard.G7;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;

@RunWith(MockitoJUnitRunner.class)
public class PawnTest {

	private Player player1 = new Player("Joao", 100L, TypePlayer.P1);
	private Player player2 =  new Player("Maria", 100L, TypePlayer.P2);
	private Chessboard chessboard = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
			TypeColor.BLACK, TypeColor.WHITE, player1, player2);
	private Pawn pawn = new Pawn(TypeColor.WHITE, this.player1);

	
	/**
	 * Tests: 	position piece in the chessboard <br>
	 * 			start game <br>
	 * 			available movement front for pawn
	 */
	@Test
	public void testMovementOfPawnToFront(){
		System.out.println("\n------------------------------------------------------------------------------");
		Assert.assertNull(chessboard.getSquareChessboard(A2).getPiece());
		chessboard.positionPiece(A2, pawn);
		Assert.assertNotNull(chessboard.getSquareChessboard(A2).getPiece());
		chessboard.printChessboard(chessboard, "position");
		
		chessboard.startGame();
		chessboard.printChessboard(chessboard, "start game");
				
		List<PositionChessboard> listMovement = pawn.movementAvailable(B2, chessboard.getSquaresChessboard());
		System.out.println("\n" + listMovement);
		Assert.assertEquals(listMovement.get(0), B3);
		Assert.assertEquals(listMovement.get(1), B4);
		Assert.assertEquals(listMovement.size(), 2);
		chessboard.printChessboard(chessboard, "movement to front for pawn");		
				
	}
	
	/**
	 * Tests: 	available movement takes diagonal left and right for pawn
	 */
	@Test
	public void testMovementOfPawnToTakesPieces(){
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		chessboard.getSquaresChessboard()[D3.getLetter()][D3.getNumber()].addPiece(chessboard.getSquareChessboard(D7).getPiece());
		chessboard.getSquareChessboard(D7).removePiece();
		chessboard.getSquaresChessboard()[B3.getLetter()][B3.getNumber()].addPiece(chessboard.getSquareChessboard(B7).getPiece());
		chessboard.getSquareChessboard(B7).removePiece();
		
		List<PositionChessboard> listTakes = pawn.movementAvailableToTakePieces(C2, chessboard.getSquaresChessboard());
		System.out.println("\n" + listTakes);
		Assert.assertEquals(listTakes.size(), 2);
		chessboard.printChessboard(chessboard, "movement to takes for pawn");	
	}
	
	@Test
	public void testPositionPieceInTheChessboardAvailable(){		
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);		
		ResponseChessboard response = game.nextMove(PositionChessboard.E2);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 0);
	}
	
	@Test
	public void testPositionPieceInTheChessboardGottenPiece(){		
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		chessboard.getSquaresChessboard()[G3.getLetter()][G3.getNumber()].addPiece(chessboard.getSquareChessboard(G7).getPiece());
		chessboard.getSquareChessboard(G7).removePiece();
		GameApplication game = new GameApplication(chessboard);
		
		//click piece B2 pawn
		ResponseChessboard response = game.nextMove(PositionChessboard.B2);
		Assert.assertNotNull(response);
				
		//click piece B2 pawn same piece then clear
		response = game.nextMove(PositionChessboard.B2);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 0);
		
		//click H2 then return lists
		response = game.nextMove(PositionChessboard.H2);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 1);
		
		//click G3 then move H2 to G3 and take piece of enemy
		response = game.nextMove(PositionChessboard.G3);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		
		//click piece A2, but turn is player2
		response = game.nextMove(PositionChessboard.A2);
		Assert.assertNotNull(response);
		
		//click piece A7, then turn is player2
		response = game.nextMove(PositionChessboard.A7);
		Assert.assertNotNull(response);
	}
	
	
	
	
}
