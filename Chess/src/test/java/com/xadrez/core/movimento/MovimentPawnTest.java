package com.xadrez.core.movimento;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Chessboard;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

import static com.chess.core.enums.PositionChessboard.*;
import static org.junit.Assert.*;

import java.util.List;

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
		pawn = new Pawn(TypePiece.PEAO, TypeColor.WHITE, this.player1);
	}
	
	/**
	 * Tests: 	position piece in the chessboard <br>
	 * 			start game <br>
	 * 			available movement front for pawn
	 */
	@Test
	public void testMovementOfPawnToFront(){
		
		assertNull(chessboard.squaresChessboard(A2).getPiece());
		chessboard.positionPiece(A2, pawn);
		assertNotNull(chessboard.squaresChessboard(A2).getPiece());
		chessboard.printChessboard(chessboard, "position");
		
		chessboard.startGame();
		chessboard.printChessboard(chessboard, "start game");
				
		List<PositionChessboard> listMovement = pawn.movementAvailable(B2, chessboard.squaresChessboard());
		listMovement.forEach(p->System.out.println(p));
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
		
		chessboard.startGame();
		chessboard.squaresChessboard()[D3.getLetter()][D3.getNumber()].addPiece(chessboard.squaresChessboard(D7).getPiece());
		chessboard.squaresChessboard(D7).removePiece();
		chessboard.squaresChessboard()[B3.getLetter()][B3.getNumber()].addPiece(chessboard.squaresChessboard(B7).getPiece());
		chessboard.squaresChessboard(B7).removePiece();
		
		List<PositionChessboard> listTakes = pawn.movementAvailableToTakePieces(C2, chessboard.squaresChessboard());
		listTakes.forEach(p->System.out.println(p));
		assertEquals(listTakes.size(), 2);
		chessboard.printChessboard(chessboard, "movement to takes for pawn");	
	}
	
	@Test
	public void testPositionPieceInTheChessboard(){
		
		chessboard.startGame();
	}
	
}
