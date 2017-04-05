package com.chess.core.view;

import java.util.Scanner;

import com.chess.core.service.ChessService;

public class ConsoleServiceChess {
	
	public static void main(String[] args) {
		
		ChessService service = new ChessService();	
		service.startChess();
		Scanner scanner = new Scanner(System.in);		
		while(true){
			String[] input = scanner.nextLine().split(" ");
			if(input.length != 2) continue;
			String response = service.selectAndMovePiece(input[0], input[1]);
			service.printInfoResponseJson(response);
			if(response.contains("PAWN_PROMOTION")){
				input = scanner.nextLine().split(" ");
				response = service.choosePromotion(input[0], input[1]);
				service.printInfoResponseJson(response);
			}
			if (response.contains("MOVED")) {
				service.printLayoutChessboard();
				response = service.verifyCheckmateTurn();
			}
			if(response.contains("CHECKMATE")){
				break;
			}
		}		
	}
}
