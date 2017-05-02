package com.xadrez.core.service;

import org.junit.Assert;
import org.junit.Test;

import com.chess.core.ChessGamePool;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard.StatusResponse;
import com.chess.core.client.ChessServiceRemote;
import com.chess.core.client.ClientRequestThreadMulti;
import com.chess.core.client.ClientRequestThreadSingle;
import com.chess.core.client.ResponseClient;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;
import com.chess.core.model.PlayerAI;
import com.chess.core.service.ChessMultiplayerAI;
import com.chess.core.service.ChessServiceImpl;
import com.chess.core.service.ChessSinglePlayerCommon;


public class ChessPoolSinglePlayerAndMultiplayerTest {

	
	@Test
	public void testStartChessWithSinglePlayerCommon(){
		System.out.println("\n------------------------------------------------------------------------------");		
		ChessSinglePlayerCommon chessPlayer = new ChessSinglePlayerCommon();
		GameApplication game = chessPlayer.startChess();
		
		ChessServiceRemote remote = new ChessServiceImpl();
		remote.play(game);
		
		ResponseClient response = remote.selectAndMovePiece("A2", "W");
		Assert.assertEquals(response.getStatus(), StatusResponse.CLICKED.toString());
	}
	
	@Test
	public void testStartChessWithMultiplayerAI(){
		System.out.println("\n------------------------------------------------------------------------------");		
		Player p1 = new Player(TypePlayer.W);
		PlayerAI p2 = new PlayerAI(TypePlayer.B);
		ChessMultiplayerAI chessPlayer = new ChessMultiplayerAI();
		GameApplication game = chessPlayer.startChess(p1, p2);
		
		ChessServiceRemote remote = new ChessServiceImpl();
		remote.play(game);
		
		ResponseClient response = remote.selectAndMovePiece("A2", "W");
		Assert.assertEquals(response.getStatus(), StatusResponse.CLICKED.toString());
	}
	
	@Test
	public void testStartChessWithSinglePlayerCommonOnline(){
		System.out.println("\n------------------------------------------------------------------------------");		
		ChessGamePool pool = new ChessGamePool();
		ClientRequestThreadSingle client1 = new ClientRequestThreadSingle(pool);
		client1.start();	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ResponseClient responseClient1 = client1.getResponseClientSingleplayerOnline();
		Assert.assertEquals(responseClient1.getKeyClientType(), TypePlayer.W.toString());
		
		GameApplication game1 = pool.findGameApp(responseClient1.getKeyClientID().toString(), 
				responseClient1.getKeyClientType().toString());
		Assert.assertEquals(pool.getTotalChessPool(), 1);
		
		ChessServiceRemote remote = new ChessServiceImpl();
		remote.play(game1);	
		
		ResponseClient response = remote.selectAndMovePiece("A2", "W");
		Assert.assertEquals(response.getStatus(), StatusResponse.CLICKED.toString());
	}
	
	@Test
	public void testStartChessWithMultiplayerOnline(){
		System.out.println("\n------------------------------------------------------------------------------");		
		ChessGamePool pool = new ChessGamePool();
		ClientRequestThreadMulti client1 = new ClientRequestThreadMulti(pool);
		client1.start();
		ClientRequestThreadMulti client2 = new ClientRequestThreadMulti(pool);
		client2.start();		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ResponseClient responseClient1 = client1.getResponseClientMultiplayerOnline();
		ResponseClient responseClient2 = client2.getResponseClientMultiplayerOnline();
		Assert.assertEquals(responseClient1.getKeyClientType(), TypePlayer.W.toString());
		Assert.assertEquals(responseClient2.getKeyClientType(), TypePlayer.B.toString());
		
		GameApplication game1 = pool.findGameApp(responseClient1.getKeyClientID().toString(), 
				responseClient1.getKeyClientType().toString());
		GameApplication game2 = pool.findGameApp(responseClient2.getKeyClientID().toString(), 
				responseClient2.getKeyClientType().toString());
		Assert.assertEquals(pool.getTotalChessPool(), 1);
		Assert.assertEquals(game1, game2);
		
		ChessServiceRemote remote = new ChessServiceImpl();
		remote.play(game1);		
		ResponseClient response = remote.selectAndMovePiece("A2", "W");
		Assert.assertEquals(response.getStatus(), StatusResponse.CLICKED.toString());
		
		remote.play(game2);
		response = remote.selectAndMovePiece("A2", "W");
		Assert.assertEquals(response.getStatus(), StatusResponse.MARK_OFF.toString());
	}
	
	@Test
	public void testStartChessWithMultiplayerOnline2(){
		System.out.println("\n------------------------------------------------------------------------------");		
		ChessGamePool pool = new ChessGamePool();
		ClientRequestThreadMulti client1 = new ClientRequestThreadMulti(pool);
		ClientRequestThreadMulti client2 = new ClientRequestThreadMulti(pool);
		ClientRequestThreadMulti client3 = new ClientRequestThreadMulti(pool);
		ClientRequestThreadMulti client4 = new ClientRequestThreadMulti(pool);
		ClientRequestThreadMulti client5 = new ClientRequestThreadMulti(pool);
		client1.start();
		client2.start();
		client3.start();
		client4.start();
		client5.start();		
		try {
			Thread.sleep(7000);
			client1.interrupt();
			client2.interrupt();
			client3.interrupt();
			client4.interrupt();
			client5.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ResponseClient responseClient1 = client1.getResponseClientMultiplayerOnline();
		ResponseClient responseClient2 = client2.getResponseClientMultiplayerOnline();
		ResponseClient responseClient3 = client2.getResponseClientMultiplayerOnline();
		ResponseClient responseClient4 = client2.getResponseClientMultiplayerOnline();
		ResponseClient responseClient5 = client2.getResponseClientMultiplayerOnline();
		
		Assert.assertEquals(pool.getTotalChessPool(), 2);
		Assert.assertEquals(pool.getTotalChessQueuePending(), 1);
		System.out.println("1 = " + responseClient1);
		System.out.println("2 = " + responseClient2);
		System.out.println("3 = " + responseClient3);
		System.out.println("4 = " + responseClient4);
		System.out.println("5 = " + responseClient5);
	}
}
