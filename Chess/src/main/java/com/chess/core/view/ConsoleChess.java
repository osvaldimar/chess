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

public class ConsoleChess {
	
	public static void main(String[] args) {
		
		Player player1 = new Player("mano1", 100L, TypePlayer.P1);
		Player player2 = new Player("mano2", 100L, TypePlayer.P2);
		Chessboard chessboard = new Chessboard(TypeColor.BLACK, TypeColor.WHITE, 
				TypeColor.BLACK, TypeColor.WHITE, player1, player2);
		
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);
		
		
		while(true){
			
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			PositionChessboard position = PositionChessboard.getEnum(input);			
			ResponseChessboard response = game.nextMove(position);	
			
			if (response.getStatusResponse() == StatusResponse.MOVED) {
				response = game.verifyCheckmateValidator();
			}
			if(response.getStatusResponse() == StatusResponse.CHECKMATE){
				break;
			}
		}		
		
	}

}