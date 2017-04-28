package com.xadrez.core.time;

import org.junit.Assert;
import org.junit.Test;

import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.client.ChessServiceRemote;
import com.chess.core.client.ResponseClient;
import com.chess.core.service.ChessServiceImpl;
import com.chess.core.service.ChessSinglePlayerCommon;


public class ChessClockTest {

	@Test
	public void testProgressClockWhiteAndBlack(){
		ChessSinglePlayerCommon single = new ChessSinglePlayerCommon();
		ChessClock clock = new ChessClock(ChessClock.FIVE_MINUTES);
		GameApplication game = single.startChess(clock);
		ChessServiceRemote service = new ChessServiceImpl();
		service.play(game);
		
		ResponseClient response = service.verifyCheckmateTurn();
		Assert.assertEquals(response.getClockWhite(), "5:00");
		Assert.assertEquals(response.getClockBlack(), "5:00");
		
		response = service.selectAndMovePiece("A2", "W");
		response = service.selectAndMovePiece("A4", "W");
		Assert.assertEquals(response.getStatus(), ResponseChessboard.StatusResponse.MOVED.toString());
		response = service.verifyCheckmateTurn();
		Assert.assertEquals(response.getClockWhite(), "5:00");
		Assert.assertEquals(response.getClockBlack(), "5:00");		
	}
}
