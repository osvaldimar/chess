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

	public boolean isAvailable(){
		return this.piece != null;
	}
	
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public TypeColor getColor() {
		return color;
	}

	public void setColor(TypeColor color) {
		this.color = color;
	}

	public PositionChessboard getPosition() {
		return position;
	}

	public void setPosition(PositionChessboard position) {
		this.position = position;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
