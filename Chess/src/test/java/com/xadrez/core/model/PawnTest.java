package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.*;

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
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;

@RunWith(MockitoJUnitRunner.class)
public class PawnTest {

	private Player player1 = new Player("Joao", 100L, TypePlayer.P1_W);
	private Player player2 =  new Player("Maria", 100L, TypePlayer.P2_B);
	private Chessboard chessboard = new Chessboard(player1, player2);
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
		ResponseChessboard response = game.nextMove(E2);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 0);
		
		response = game.nextMove(null);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE);
		System.out.println(response.toString());
	}
	
	@Test
	public void testPositionPieceInTheChessboardGottenPiece(){		
		System.out.println("\n------------------------------------------------------------------------------");
		chessboard.startGame();
		chessboard.getSquaresChessboard()[G3.getLetter()][G3.getNumber()].addPiece(chessboard.getSquareChessboard(G7).getPiece());
		chessboard.getSquareChessboard(G7).removePiece();
		GameApplication game = new GameApplication(chessboard);
		
		//click piece B2 pawn
		ResponseChessboard response = game.nextMove(B2);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
				
		//click piece B2 pawn same piece then clear
		response = game.nextMove(B2);
		Assert.assertNull(response.getListPositionsAvailable());
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLEAR);
		
		//click H2 then return lists
		response = game.nextMove(H2);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 1);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		//click G3 then move H2 to G3 and take piece of enemy
		response = game.nextMove(G3);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(response.getCurrentPlayer().getTypePlayer(), TypePlayer.P1_W);
		Assert.assertEquals(response.getSquareClicked().getPosition(), H2);
		Assert.assertEquals(response.getPositionSelected(), G3);
		Assert.assertEquals(response.getPieceClicked().getTypePiece(), TypePiece.PAWN);
		Assert.assertEquals(response.getPieceClicked().getPlayer().getTypePlayer(), TypePlayer.P1_W);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 1);
		Assert.assertEquals(response.getPieceGotten().getTypePiece(), TypePiece.PAWN);
		Assert.assertEquals(response.getPieceGotten().getPlayer().getTypePlayer(), TypePlayer.P2_B);
		
		//click piece A7, turn player2, first movement then available 2 square to front
		response = game.nextMove(A7);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		Assert.assertEquals(response.getCurrentPlayer().getTypePlayer(), TypePlayer.P2_B);
		
		//click move piece to A6, turn player2
		response = game.nextMove(A6);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(response.getCurrentPlayer().getTypePlayer(), TypePlayer.P2_B);		
		Assert.assertEquals(response.getSquareClicked().getPosition(), A7);
		Assert.assertEquals(response.getPositionSelected(), A6);		
		Assert.assertEquals(response.getPieceClicked().getTypePiece(), TypePiece.PAWN);
		Assert.assertEquals(response.getPieceClicked().getPlayer().getTypePlayer(), TypePlayer.P2_B);		
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 0);
		Assert.assertNull(response.getPieceGotten());
		
		//test second movement pawn of player1 only walk one square to front
		response = game.nextMove(G3);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 1);
		Assert.assertEquals(response.getListPositionsAvailable().get(0), G4);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		Assert.assertEquals(response.getCurrentPlayer().getTypePlayer(), TypePlayer.P1_W);
		
		//turn player1, moved piece G3 to G4
		response = game.nextMove(G4);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		
		//turn player2, click piece A6 second movement, then available 1(A5) square to front
		response = game.nextMove(A6);
		Assert.assertEquals(response.getListPositionsAvailable().get(0), A5);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
	}
	
	
}
