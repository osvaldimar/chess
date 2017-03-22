package com.xadrez.core.movimento;

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

import static com.chess.core.enums.PositionChessboard.*;

@RunWith(MockitoJUnitRunner.class)
public class MovimentTest {

	private Player jogador1;
	
	@Before
	public void setUp(){
		this.jogador1 = new Player("Joao", 100L);
	}
	
	@Test
	public void testMovimentoDoPeaoParaFrente(){
		
		Pawn pawn = new Pawn(TypePiece.PEAO, TypeColor.WHITE, this.jogador1);
		
		Chessboard board = new Chessboard(TypeColor.BLACK, TypeColor.WHITE);;
		board.positionPiece(A2, pawn);
		
		board.clickedByHand(){
			
		}
		if(pawn.movimentAvailable()){
			board.printSquaresAvailableToPiece(pawn);			
		}
		
		
	}
	
}
