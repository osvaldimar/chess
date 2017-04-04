package com.chess.core.view;

import java.util.Scanner;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.ResponseChessboard.StatusResponse;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;
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
