package com.chess.core.model;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;

public class Square {

	private Piece piece;
	private TypeColor color;
	private PositionChessboard position;
	
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
		return this.piece == null;
	}
	
	public void addPiece(Piece piece){
		this.piece = piece;
	}
	
	public Piece getPiece() {
		return piece;
	}

	public void removePiece() {
		this.piece = null;
	}

	public TypeColor getColor() {
		return color;
	}

	public PositionChessboard getPosition() {
		return position;
	}

	@Override
	public String toString() {		
		return "[" + this.getPosition() + "-" + this.getPiece() + "]";
	}
}
