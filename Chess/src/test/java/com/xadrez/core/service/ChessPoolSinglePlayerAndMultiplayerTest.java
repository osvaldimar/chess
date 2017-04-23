package com.xadrez.core.service;

import org.junit.Assert;
import org.junit.Test;

import com.chess.core.ChessGamePool;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard.StatusResponse;
import com.chess.core.client.ChessServiceRemote;
import com.chess.core.client.ClientRequestThread;
import com.chess.core.client.ResponseClient;
import com.chess.core.client.TransformJson;
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
	public void testStartChessWithMultiplayerOnline(){
		System.out.println("\n------------------------------------------------------------------------------");		
		ChessGamePool pool = new ChessGamePool();
		ClientRequestThread client1 = new ClientRequestThread(pool);
		client1.start();
		ClientRequestThread client2 = new ClientRequestThread(pool);
		client2.start();		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ResponseClient responseClient1 = client1.getResponseClient();
		ResponseClient responseClient2 = client2.getResponseClient();
		Assert.assertEquals(responseClient1.getKeyClient().getType(), TypePlayer.W);
		Assert.assertEquals(responseClient2.getKeyClient().getType(), TypePlayer.B);
		
		GameApplication game1 = pool.findGameApp(responseClient1.getKeyClient().getKey().toString(), 
				responseClient1.getKeyClient().getType().toString());
		GameApplication game2 = pool.findGameApp(responseClient2.getKeyClient().getKey().toString(), 
				responseClient2.getKeyClient().getType().toString());
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
		ClientRequestThread client1 = new ClientRequestThread(pool);
		ClientRequestThread client2 = new ClientRequestThread(pool);
		ClientRequestThread client3 = new ClientRequestThread(pool);
		ClientRequestThread client4 = new ClientRequestThread(pool);
		ClientRequestThread client5 = new ClientRequestThread(pool);
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
		
		ResponseClient responseClient1 = client1.getResponseClient();
		ResponseClient responseClient2 = client2.getResponseClient();
		ResponseClient responseClient3 = client2.getResponseClient();
		ResponseClient responseClient4 = client2.getResponseClient();
		ResponseClient responseClient5 = client2.getResponseClient();
		
		Assert.assertEquals(pool.getTotalChessPool(), 2);
		Assert.assertEquals(pool.getTotalChessQueuePending(), 1);
		System.out.println("1 = " + responseClient1);
		System.out.println("2 = " + responseClient2);
		System.out.println("3 = " + responseClient3);
		System.out.println("4 = " + responseClient4);
		System.out.println("5 = " + responseClient5);
	}
}
