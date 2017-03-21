package com.chess.core.model;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;

public class Square {

	private Piece piece;
	private TypeColor color;
	private PositionChessboard position;
	private int x;
	private int y;
	
	public Square(TypeColor color, PositionChessboard position){
		this.color = color;
		this.position = position;		
	}
	
	public Square(Piece piece, TypeColor color, PositionChessboard position){
		this.piece = piece;
		this.color = color;
		this.position = position;		
	}
	
}
