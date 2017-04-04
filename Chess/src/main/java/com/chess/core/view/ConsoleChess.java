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
		
		Player player1 = new Player("mano1", 100L, TypePlayer.W);
		Player player2 = new Player("mano2", 100L, TypePlayer.B);
		Chessboard chessboard = new Chessboard(player1, player2);		
		
		chessboard.startGame();
		GameApplication game = new GameApplication(chessboard);		
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			String input = scanner.nextLine();
			PositionChessboard position = PositionChessboard.getEnum(input);			
			ResponseChessboard response = game.nextMove(position);	//next movement		
			
			if (response.getStatusResponse() == StatusResponse.MOVED) {
				response = game.verifyCheckmateValidator();			//verify checkmate
			}
			if(response.getStatusResponse() == StatusResponse.CHECKMATE){
				break;
			}
		}		
	}

}
