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
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Bishop;
import com.chess.core.model.King;
import com.chess.core.model.Knight;
import com.chess.core.model.Pawn;
import com.chess.core.model.Player;
import com.chess.core.model.Queen;
import com.chess.core.model.Rook;
import com.chess.core.util.PieceUtils;



@RunWith(MockitoJUnitRunner.class)
public class KingDrawTest {

	private Player player1;
	private Player player2;
	private Chessboard chessboard;
	private King king;
	
	@Before
	public void setUp(){
		player1 = new Player("Joao", 100L, TypePlayer.W);
		player2 = new Player("Maria", 100L, TypePlayer.B);
		chessboard = new Chessboard(player1, player2);
		king = new King(TypeColor.WHITE, player1.getTypePlayer());
	}

	@Test
	public void testKingStalemateResultInDraw(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.positionPiece(A8, new King(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(E1, new King(TypeColor.WHITE, player1.getTypePlayer()));
		chessboard.positionPiece(H7, new Rook(TypeColor.WHITE, player1.getTypePlayer()));
		chessboard.positionPiece(C1, new Queen(TypeColor.WHITE, player1.getTypePlayer()));
		GameApplication game = new GameApplication(chessboard);
		
		ResponseChessboard response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_CHECK);
		
		response = game.selectAndMove(C1, player1);
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);
		Assert.assertEquals(response.getListPositionsAvailable().size(), 17);
		Assert.assertEquals(response.getListPositionsToTake().size(), 0);
		
		response = game.selectAndMove(C7, player1);
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(response.getTurn().getTypePlayer(), player2.getTypePlayer());
		
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.DRAW_STALEMATE);
		
		//GAME IS OVER, ANY MOVEMENT NOT ALLOWED AND KING IS NOT IN CHECK
		response = game.selectAndMove(A8, player2);
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.DRAW_STALEMATE);
		Assert.assertNull(response.getListPositionsAvailable());
		Assert.assertNull(response.getListPositionsToTake());
	}
	
	@Test
	public void testKing50MovementsResultInDraw(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.positionPiece(A8, new King(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(E1, new King(TypeColor.WHITE, player1.getTypePlayer()));
		chessboard.positionPiece(H7, new Rook(TypeColor.WHITE, player1.getTypePlayer()));
		GameApplication game = new GameApplication(chessboard);
		chessboard.printDebugChessboard(chessboard, "testKing50MovementsResultInDraw()");
		
		ResponseChessboard response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_CHECK);
		
		player1.setQuantityMovement(48);
		player2.setQuantityMovement(48);
		
		game.selectAndMove(E1, player1);
		game.selectAndMove(E2, player1);
		Assert.assertEquals(player1.getQuantityMovement(), 49);
		game.selectAndMove(A8, player2);
		game.selectAndMove(B8, player2);
		Assert.assertEquals(player2.getQuantityMovement(), 49);
		
		response = game.selectAndMove(E2, player1);
		response = game.selectAndMove(E3, player1);
		Assert.assertEquals(player1.getQuantityMovement(), 50);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_CHECK);
		
		response = game.selectAndMove(B8, player2);
		response = game.selectAndMove(C8, player2);
		Assert.assertEquals(player2.getQuantityMovement(), 50);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(response.getTurn().getTypePlayer(), TypePlayer.W);
		
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.DRAW_50_MOVEMENTS);
		
		//GAME IS OVER, 50 MOVEMENTS WITHOUT TAKE ANY PIECE AND ANY PAWN NOT WAS MOVED 
		response = game.selectAndMove(E3, player1);
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.DRAW_50_MOVEMENTS);
	}
	
	@Test
	public void testKing50MovementsButPawnMoved(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.positionPiece(A8, new King(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(A3, new Pawn(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(E1, new King(TypeColor.WHITE, player1.getTypePlayer()));
		chessboard.positionPiece(H7, new Rook(TypeColor.WHITE, player1.getTypePlayer()));
		GameApplication game = new GameApplication(chessboard);
		chessboard.printDebugChessboard(chessboard, "testKing50MovementsButPawnMoved()");
		
		player1.setQuantityMovement(48);
		player2.setQuantityMovement(48);
		
		ResponseChessboard response = game.selectAndMove(E1, player1);
		game.selectAndMove(E2, player1);
		Assert.assertEquals(player1.getQuantityMovement(), 49);
		game.selectAndMove(A8, player2);
		game.selectAndMove(B8, player2);
		Assert.assertEquals(player2.getQuantityMovement(), 49);
		
		response = game.selectAndMove(E2, player1);
		response = game.selectAndMove(E3, player1);
		Assert.assertEquals(player1.getQuantityMovement(), 50);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_CHECK);
		
		response = game.selectAndMove(A3, player2);
		response = game.selectAndMove(A2, player2);
		Assert.assertEquals(player1.getQuantityMovement(), 0);
		Assert.assertEquals(player2.getQuantityMovement(), 0);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(response.getTurn().getTypePlayer(), TypePlayer.W);
		
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_CHECK);
	}
	
	@Test
	public void testKing50MovementsButTakeAnotherPiece(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.positionPiece(A8, new King(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(B1, new Bishop(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(E1, new King(TypeColor.WHITE, player1.getTypePlayer()));
		chessboard.positionPiece(H7, new Rook(TypeColor.WHITE, player1.getTypePlayer()));
		GameApplication game = new GameApplication(chessboard);
		chessboard.printDebugChessboard(chessboard, "testKing50MovementsButTakeAnotherPiece()");
		
		player1.setQuantityMovement(49);
		player2.setQuantityMovement(49);
		
		ResponseChessboard response = game.selectAndMove(E1, player1);
		response = game.selectAndMove(E2, player1);		
		Assert.assertEquals(player1.getQuantityMovement(), 50);
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_CHECK);
		
		response = game.selectAndMove(B1, player2);
		response = game.selectAndMove(H7, player2);
		chessboard.printDebugChessboard(chessboard, "moved - testKing50MovementsButTakeAnotherPiece()");
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(player1.getQuantityMovement(), 0);
		Assert.assertEquals(player2.getQuantityMovement(), 0);
		Assert.assertEquals(response.getTurn().getTypePlayer(), TypePlayer.W);
		
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_CHECK);
	}
	
	@Test
	public void testKingRuleThreeEqualPositions(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.positionPiece(A8, new King(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(B1, new Bishop(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(E1, new King(TypeColor.WHITE, player1.getTypePlayer()));
		chessboard.positionPiece(H7, new Rook(TypeColor.WHITE, player1.getTypePlayer()));
		GameApplication game = new GameApplication(chessboard);
		chessboard.printDebugChessboard(chessboard, "testKingRuleThreeEqualPositions()");
		
		ResponseChessboard response = game.selectAndMove(E1, player1);
		response = game.selectAndMove(E2, player1);
		response = game.selectAndMove(A8, player2);
		response = game.selectAndMove(B8, player2);
		
		response = game.selectAndMove(E2, player1);
		response = game.selectAndMove(E3, player1);
		response = game.selectAndMove(B8, player2);
		response = game.selectAndMove(A8, player2);
		
		response = game.selectAndMove(E3, player1);
		response = game.selectAndMove(F3, player1);
		response = game.selectAndMove(A8, player2);
		response = game.selectAndMove(B8, player2);
		
		response = game.selectAndMove(F3, player1);
		response = game.selectAndMove(G3, player1);
		response = game.selectAndMove(B8, player2);
		response = game.selectAndMove(A8, player2);
		
		response = game.selectAndMove(G3, player1);
		response = game.selectAndMove(G2, player1);
		response = game.selectAndMove(A8, player2);
		response = game.selectAndMove(B8, player2);
		
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.DRAW_3_POSITIONS);
		chessboard.printDebugChessboard(chessboard, "moved - testKingRuleThreeEqualPositions()");
		
		response = game.selectAndMove(G2, player1);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.DRAW_3_POSITIONS);
	}
	
	@Test
	public void testKingRuleThreeEqualPositionsButPieceMovedAndTakeChangesStateChessboard(){
		System.out.println("\n------------------------------------------------------------------------------");		
		chessboard.positionPiece(A8, new King(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(B1, new Bishop(TypeColor.BLACK, player2.getTypePlayer()));
		chessboard.positionPiece(E1, new King(TypeColor.WHITE, player1.getTypePlayer()));
		chessboard.positionPiece(H7, new Rook(TypeColor.WHITE, player1.getTypePlayer()));
		chessboard.positionPiece(B4, new Knight(TypeColor.WHITE, player1.getTypePlayer()));
		GameApplication game = new GameApplication(chessboard);
		chessboard.printDebugChessboard(chessboard, "testKingRuleThreeEqualPositions()");
		
		ResponseChessboard response = game.selectAndMove(E1, player1);
		response = game.selectAndMove(E2, player1);
		response = game.selectAndMove(A8, player2);
		response = game.selectAndMove(B8, player2);
		
		response = game.selectAndMove(E2, player1);
		response = game.selectAndMove(E3, player1);
		response = game.selectAndMove(B8, player2);
		response = game.selectAndMove(A8, player2);
		
		response = game.selectAndMove(E3, player1);
		response = game.selectAndMove(F3, player1);
		response = game.selectAndMove(A8, player2);
		response = game.selectAndMove(B8, player2);
		
		response = game.selectAndMove(B4, player1);
		response = game.selectAndMove(C6, player1);
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.IN_CHECK);
		chessboard.printDebugChessboard(chessboard, "do check with knight");
		response = game.selectAndMove(B8, player2);
		response = game.selectAndMove(A8, player2);
		
		response = game.selectAndMove(C6, player1);
		response = game.selectAndMove(B8, player1);
		chessboard.printDebugChessboard(chessboard, "move suicidal knight ");
		response = game.selectAndMove(A8, player2);
		response = game.selectAndMove(B8, player2);
		game.printInfoResponse(response);
		chessboard.printDebugChessboard(chessboard, "king take a suicidal knight");
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(response.getPieceGotten().getTypePiece(), TypePiece.KNIGHT);
		
		response = game.verifyCheckmateValidator();
		game.printInfoResponse(response);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.NONE_CHECK);
		chessboard.printDebugChessboard(chessboard, "testKingRuleThreeEqualPositionsButPieceMovedAndTakeChangesStateChessboard()");
		
		response = game.selectAndMove(F3, player1);
		Assert.assertEquals(response.getStatusResponse(), ResponseChessboard.StatusResponse.CLICKED);		
		Assert.assertEquals(game.getTotalMovementsGameChess(), 10);
	}
}
