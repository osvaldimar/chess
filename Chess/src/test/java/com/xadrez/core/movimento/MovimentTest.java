package com.xadrez.core.movimento;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.model.Chessboard;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

import static com.chess.core.enums.PositionChessboard.*;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MovimentTest {

	private Player jogador1;
	private Player jogador2;
	
	@Before
	public void setUp(){
		this.jogador1 = new Player("Joao", 100L);
		this.jogador1 = new Player("Maria", 100L);
	}
	
	@Test
	public void testMovimentoDoPeaoParaFrente(){
		
		Pawn pawn = new Pawn(TypePiece.PEAO, TypeColor.WHITE, this.jogador1);
		
		Chessboard board = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
				TypeColor.BLACK, TypeColor.WHITE, jogador1, jogador2);
		
		Assert.assertNull(board.squaresChessboard(A2).getPiece());
		board.positionPiece(A2, pawn);
		Assert.assertNotNull(board.squaresChessboard(A2).getPiece());
		board.printChessboard(board);		
		
		/*board.squaresChessboard()[A2.getLetter()][A2.getNumber()] = null;	
		Assert.assertNotNull(board.squaresChessboard(A2));		
		board.squaresChessboard(A2).addPiece(null);
		Assert.assertNotNull(board.squaresChessboard(A2).getPiece());
		board.printChessboard(board);*/
		
		board.startGame();
		board.printChessboard(board);
				
		List<PositionChessboard> listMovement = pawn.movementAvailable(B2, board.squaresChessboard());
		listMovement.forEach(p->System.out.println(p));
		Assert.assertEquals(listMovement.get(0), B3);
		Assert.assertEquals(listMovement.get(1), B4);
		Assert.assertEquals(listMovement.size(), 2);
		
		
/*		
		board.clickedByHand(){
			
		}
		if(pawn.movimentAvailable()){
			board.printSquaresAvailableToPiece(pawn);			
		}*/
		
		
	}
	
}
