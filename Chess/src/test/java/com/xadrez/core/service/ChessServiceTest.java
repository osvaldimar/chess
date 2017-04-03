package com.xadrez.core.service;

import org.junit.Test;

import com.chess.core.service.ChessService;

public class ChessServiceTest {

	
	@Test
	public void test(){
		
		ChessService service = new ChessService();
		service.startChess();
		service.selectPiece("A2");
		service.movePiece("A4");
		service.verifyCheckmate();
		
		service.selectPiece("A7");
		service.movePiece("A5");
		service.verifyCheckmate();
		service.selectPiece("A5");
		
	}
	
}
