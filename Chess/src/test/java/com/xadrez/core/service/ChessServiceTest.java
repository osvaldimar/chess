package com.xadrez.core.service;

import org.junit.Assert;
import org.junit.Test;

import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.service.ChessServiceImpl;
import com.chess.core.service.ChessSinglePlayerCommon;
import com.chess.core.client.ChessServiceRemote;
import com.chess.core.client.ResponseClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChessServiceTest {

	
	@Test
	public void testChessServiceMethodsFacade(){
		
		ChessSinglePlayerCommon chessPlayer = new ChessSinglePlayerCommon();
		GameApplication game = chessPlayer.startChess();		
		ChessServiceRemote service = new ChessServiceImpl();
		service.play(game);
		
		String responseJson = service.startChessOfflineCommon();
		service.printInfoResponseJson(responseJson);
		
		//json as json element java
		JsonElement jsonElement = new JsonParser().parse(responseJson);
		JsonObject asJsonObject = jsonElement.getAsJsonObject();
		JsonElement jsonElementStatus = asJsonObject.get("status");
		String asString = jsonElementStatus.getAsString();
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(asString), ResponseChessboard.StatusResponse.START);
		
		//json to java object
		ResponseClient resClient = new Gson().fromJson(responseJson, ResponseClient.class);
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.START);
		
		resClient = service.selectAndMovePiece("A2", "W");
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.CLICKED);
		service.printInfoResponseJson(responseJson);
		
		resClient = service.selectAndMovePiece("A4", "W");
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(TypePlayer.getEnum(resClient.getCurrentPlayer()), TypePlayer.W);
		Assert.assertEquals(TypePlayer.getEnum(resClient.getTurn()), TypePlayer.B);
		service.printInfoResponseJson(responseJson);
		
		resClient = service.verifyCheckmateTurn();
		Assert.assertEquals("Expected NONE_CHECK", ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.NONE_CHECK);
		
		service.printInfoResponseJson(responseJson);
		service.printLayoutChessboard();
		
		resClient = service.selectAndMovePiece("A7", "B");
		Assert.assertEquals(TypePlayer.getEnum(resClient.getCurrentPlayer()), TypePlayer.B);
		Assert.assertEquals(TypePlayer.getEnum(resClient.getTurn()), TypePlayer.B);
		Assert.assertEquals(TypePiece.getEnum(resClient.getPieceClicked()), TypePiece.PAWN);
		Assert.assertEquals(PositionChessboard.getEnum(resClient.getPositionSelected()), PositionChessboard.A7);
		Assert.assertEquals(resClient.getPositionsAvailable().length, 2);
		Assert.assertEquals(resClient.getPositionsToTake().length, 0);
		Assert.assertNull(TypePlayer.getEnum(resClient.getWinner()));
		Assert.assertNull(TypePlayer.getEnum(resClient.getPieceGotten()));
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.CLICKED);
		service.printInfoResponseJson(responseJson);
		
		resClient = service.selectAndMovePiece("A5", "B");
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.MOVED);
		service.printInfoResponseJson(responseJson);
		service.printLayoutChessboard();
		
		
		resClient = service.choosePromotion("queen", "B");
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.OPPONENT_TURN);
		resClient = service.choosePromotion("queen", "W");
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.NONE_ACTION);
	}
	
	@Test
	public void testCallMethodsGetsOfChessService(){
		ChessSinglePlayerCommon chessPlayer = new ChessSinglePlayerCommon();
		GameApplication game = chessPlayer.startChess();		
		ChessServiceRemote service = new ChessServiceImpl();
		service.play(game);
		service.printSquaresChessboardJson();
	}
}
