package com.xadrez.core.service;

import org.junit.Test;

import com.chess.core.service.ChessService;

public class ChessServiceTest {

	
	@Test
	public void test(){
		
		ChessService service = new ChessService();
		service.startChess();
		
		service.selectAndMovePiece("A2", "W");
		service.selectAndMovePiece("A4", "W");
		service.verifyCheckmateTurn();
		
		service.selectAndMovePiece("A7", "B");
		service.selectAndMovePiece("A5", "B");
		service.verifyCheckmateTurn();
		
	}
	
}
