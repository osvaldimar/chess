package com.chess.core.model;

import java.util.HashMap;
import java.util.Map;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;

import static com.chess.core.enums.TypeColor.*;
import static com.chess.core.enums.PositionChessboard.*;

public class Chessboard {

	private Square[][] squares;
	private TypeColor whiteSquares;
	private TypeColor blackSquares;
	private Map<PositionChessboard, Square> map;
	
	public Chessboard(TypeColor blackSquares, TypeColor whiteSquares){
		this.blackSquares = blackSquares;
		this.whiteSquares = whiteSquares;
		this.init();
	}
	
	private void init(){
		int contX = 0;
		int contY = 7;
		int cor = 0;
		this.map = new HashMap<>();
		PositionChessboard[] posicoes = PositionChessboard.values();
		for (PositionChessboard p : posicoes) {
			Square square = square(cor == 0 ? this.blackSquares : this.whiteSquares, p);
			this.squares[contX][contY] = square;
			this.map.put(p, square);
			cor = cor == 0 ? 1 : 0;
			if(contX >= 8){
				contX = 0;
				contY--;
			}
			System.out.println(p);
		}
		
	}
	
	

	private static Square square(TypeColor color, PositionChessboard position){
		return new Square(color, position);
	}
	
	public void positionPiece(PositionChessboard a2, Piece piece) {
		
	}
	
}
