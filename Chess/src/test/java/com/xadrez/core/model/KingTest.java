package com.xadrez.core.model;

import static com.chess.core.enums.PositionChessboard.*;
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
import com.chess.core.model.Bishop;
import com.chess.core.model.King;
import com.chess.core.model.Knight;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;
import com.chess.core.model.Queen;
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
	
	@Test
	public void testKingBeforeSimulationCheck(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(F8).removePiece();
		chessboard.positionPiece(F2, new Bishop(TypeColor.BLACK, player2));
		GameApplication game = new GameApplication(chessboard);	
		
		ResponseChessboard res = game.nextMove(A2);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		res = game.nextMove(A3);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CHECK);
		res = game.nextMove(A7);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.NONE);
		res = game.nextMove(A6);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.NONE);	
	}
	
	@Test
	public void testKingAfterSimulationCheck(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(F8).removePiece();
		chessboard.positionPiece(H4, new Bishop(TypeColor.BLACK, player2));
		GameApplication game = new GameApplication(chessboard);	
		
		ResponseChessboard res = game.nextMove(F2);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		res = game.nextMove(F3);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.EXPOSED);
	}
	
	@Test
	public void testKingAdvancedBeforeAndAfterSimulationCheck(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(F8).removePiece();
		chessboard.squaresChessboard(D8).removePiece();
		chessboard.squaresChessboard(B1).removePiece();
		chessboard.squaresChessboard(D2).removePiece();
		chessboard.positionPiece(H4, new Bishop(TypeColor.BLACK, player2));
		chessboard.positionPiece(F2, new Queen(TypeColor.BLACK, player2));
		chessboard.positionPiece(D3, new Knight(TypeColor.WHITE, player1));
		GameApplication game = new GameApplication(chessboard);
		
		//P1
		ResponseChessboard res = game.nextMove(E1);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		res = game.nextMove(F2);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CHECK);
		res = game.nextMove(D3);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		res = game.nextMove(F2);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		//P2
		res = game.nextMove(H4);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		res = game.nextMove(F2);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		//P1
		res = game.nextMove(E1);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		res = game.nextMove(D2);
		Assert.assertEquals(res.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
	}
	
	@Test
	public void testKingSimulationCheckMate(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.startGame();
		chessboard.squaresChessboard(F8).removePiece();
		chessboard.squaresChessboard(D8).removePiece();
		chessboard.positionPiece(H4, new Bishop(TypeColor.BLACK, player2));
		chessboard.positionPiece(F2, new Queen(TypeColor.BLACK, player2));
		GameApplication game = new GameApplication(chessboard);
		
		ResponseChessboard response = game.validateCheck();
		
		
	}
}
