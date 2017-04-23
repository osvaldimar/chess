package com.xadrez.core.service;

import org.junit.Assert;
import org.junit.Test;

import com.chess.core.ResponseChessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.service.ChessServiceImpl;
import com.chess.core.client.ResponseClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChessServiceTest {

	
	@Test
	public void testChessServiceMethodsFacade(){
		
		ChessServiceImpl service = new ChessServiceImpl();
		//service.choosePromotion("queen", "w");
		String responseJson = service.startChess();
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
		
		responseJson = service.selectAndMovePiece("A2", "W");
		resClient = new Gson().fromJson(responseJson, ResponseClient.class);
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.CLICKED);
		service.printInfoResponseJson(responseJson);
		
		responseJson = service.selectAndMovePiece("A4", "W");
		resClient = new Gson().fromJson(responseJson, ResponseClient.class);
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.MOVED);
		Assert.assertEquals(TypePlayer.getEnum(resClient.getCurrentPlayer()), TypePlayer.W);
		Assert.assertEquals(TypePlayer.getEnum(resClient.getTurn()), TypePlayer.B);
		service.printInfoResponseJson(responseJson);
		
		responseJson = service.verifyCheckmateTurn();
		resClient = new Gson().fromJson(responseJson, ResponseClient.class);
		Assert.assertEquals("Expected NONE_CHECK", ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.NONE_CHECK);
		
		service.printInfoResponseJson(responseJson);
		service.printLayoutChessboard();
		
		responseJson = service.selectAndMovePiece("A7", "B");
		resClient = new Gson().fromJson(responseJson, ResponseClient.class);
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
		
		responseJson = service.selectAndMovePiece("A5", "B");
		resClient = new Gson().fromJson(responseJson, ResponseClient.class);
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.MOVED);
		service.printInfoResponseJson(responseJson);
		service.printLayoutChessboard();
		
		
		responseJson = service.choosePromotion("queen", "B");
		resClient = new Gson().fromJson(responseJson, ResponseClient.class);
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.OPPONENT_TURN);
		responseJson = service.choosePromotion("queen", "W");
		resClient = new Gson().fromJson(responseJson, ResponseClient.class);
		Assert.assertEquals(ResponseChessboard.StatusResponse.valueOf(resClient.getStatus()), 
				ResponseChessboard.StatusResponse.NONE_ACTION);
	}
	
	@Test
	public void testCallMethodsGetsOfChessService(){
		ChessServiceImpl service = new ChessServiceImpl();
		service.startChess();
		service.printSquaresChessboardJson();
	}
}
