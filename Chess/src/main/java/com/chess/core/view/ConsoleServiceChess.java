package com.chess.core.view;

import java.util.Scanner;

import com.chess.core.client.TransformJson;
import com.chess.core.service.ChessServiceImpl;

public class ConsoleServiceChess {
	
	public static void main(String[] args) {
		
		ChessServiceImpl service = new ChessServiceImpl();	
		service.startChess();
		Scanner scanner = new Scanner(System.in);		
		while(true){
			String[] input = scanner.nextLine().split(" ");
			if(input.length != 2) continue;
			String response = TransformJson.createResponseJson(service.selectAndMovePiece(input[0], input[1]));
			service.printInfoResponseJson(response);
			if(response.contains("PAWN_PROMOTION")){
				while(true){
					input = scanner.nextLine().split(" ");
					response = TransformJson.createResponseJson(service.choosePromotion(input[0], input[1]));
					service.printInfoResponseJson(response);
					if (response.contains("MOVED")) break;
				}
			}
			if (response.contains("MOVED")) {
				service.printLayoutChessboard();
				response = TransformJson.createResponseJson(service.verifyCheckmateTurn());
			}
			if(response.contains("CHECKMATE")){
				break;
			}
		}		
	}
}
