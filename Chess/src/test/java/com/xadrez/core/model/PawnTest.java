package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.A1;
import static com.chess.core.enums.PositionChessboard.A2;
import static com.chess.core.enums.PositionChessboard.A5;
import static com.chess.core.enums.PositionChessboard.A6;
import static com.chess.core.enums.PositionChessboard.A7;
import static com.chess.core.enums.PositionChessboard.B2;
import static com.chess.core.enums.PositionChessboard.B3;
import static com.chess.core.enums.PositionChessboard.B4;
import static com.chess.core.enums.PositionChessboard.B7;
import static com.chess.core.enums.PositionChessboard.C1;
import static com.chess.core.enums.PositionChessboard.C2;
import static com.chess.core.enums.PositionChessboard.D1;
import static com.chess.core.enums.PositionChessboard.D3;
import static com.chess.core.enums.PositionChessboard.D7;
import static com.chess.core.enums.PositionChessboard.E1;
import static com.chess.core.enums.PositionChessboard.E2;
import static com.chess.core.enums.PositionChessboard.G3;
import static com.chess.core.enums.PositionChessboard.G4;
import static com.chess.core.enums.PositionChessboard.G7;
import static com.chess.core.enums.PositionChessboard.H2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import com.chess.core.model.LastMovement;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;

@RunWith(MockitoJUnitRunner.class)
public class PawnTest {

	private Player player1 = new Player("Joao", 100L, TypePlayer.W);
	private Player player2 =  new Player("Maria", 100L, TypePlayer.B);
	private Chessboard chessboard = new Chessboard(player1, player2);
	private Pawn pawn = new Pawn(TypeColor.WHITE, this.player1.getTypePlayer());

	
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
		chessboard.printDebugChessboard(chessboard, "position");
		
		chessboard.startGame();
		chessboard.printDebugChessboard(chessboard, "start game");
				
		List<PositionChessboard> listMovement = pawn.movementAvailable(B2, chessboard.getSquaresChessboard());
		System.out.println("\n" + listMovement);
		Assert.assertEquals(listMovement.get(0), B3);
		Assert.assertEquals(listMovement.get(1), B4);
		Assert.assertEquals(listMovement.size(), 2);
		chessboard.printDebugChessboard(chessboard, "movement to front for pawn");		
		
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
		chessboard.printDebugChessboard(chessboard, "movement to takes for pawn");
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
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_ACTION);
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
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MARK_OFF);
		
		//click H2 then return lists
		response = game.nextMove(H2);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 1);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		
		//click G3 then move H2 to G3 and take piece of enemy
		response = game.nextMove(G3);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(response.getCurrentPlayer().getTypePlayer(), TypePlayer.W);
		Assert.assertEquals(response.getSquareClicked().getPosition(), H2);
		Assert.assertEquals(response.getPositionSelected(), G3);
		Assert.assertEquals(response.getPieceClicked().getTypePiece(), TypePiece.PAWN);
		Assert.assertEquals(response.getPieceClicked().getPlayer(), TypePlayer.W);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 1);
		Assert.assertEquals(response.getPieceGotten().getTypePiece(), TypePiece.PAWN);
		Assert.assertEquals(response.getPieceGotten().getPlayer(), TypePlayer.B);
		
		//last movement
		chessboard.printDebugChessboard(chessboard, "Test last movement with pawn taken");
		assertEquals(response.getLastMovement().getName(), LastMovement.NameMovement.TAKEN);
		assertEquals(response.getLastMovement().getMovedFrom(), H2);
		assertEquals(response.getLastMovement().getMovedTo(), G3);
		assertEquals(response.getLastMovement().getDestroyed(), G3);
		assertEquals(response.getLastMovement().getIdMovement(), 1);
		
		//click piece A7, turn player2, first movement then available 2 square to front
		response = game.nextMove(A7);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		Assert.assertEquals(response.getCurrentPlayer().getTypePlayer(), TypePlayer.B);
		
		//click move piece to A6, turn player2
		response = game.nextMove(A6);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(response.getCurrentPlayer().getTypePlayer(), TypePlayer.B);		
		Assert.assertEquals(response.getSquareClicked().getPosition(), A7);
		Assert.assertEquals(response.getPositionSelected(), A6);		
		Assert.assertEquals(response.getPieceClicked().getTypePiece(), TypePiece.PAWN);
		Assert.assertEquals(response.getPieceClicked().getPlayer(), TypePlayer.B);		
		Assert.assertEquals(response.getListPositionsAvailable().size(), 2);
		Assert.assertEquals(response.getListPositionsToTake().size(), 0);
		Assert.assertNull(response.getPieceGotten());
		
		//test second movement pawn of player1 only walk one square to front
		response = game.nextMove(G3);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 1);
		Assert.assertEquals(response.getListPositionsAvailable().get(0), G4);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		Assert.assertEquals(response.getCurrentPlayer().getTypePlayer(), TypePlayer.W);
		
		//turn player1, moved piece G3 to G4
		response = game.nextMove(G4);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		
		//turn player2, click piece A6 second movement, then available 1(A5) square to front
		response = game.nextMove(A6);
		Assert.assertEquals(response.getListPositionsAvailable().get(0), A5);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
	}
	
	
}
