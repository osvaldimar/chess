package com.chess.core.model;

import java.util.HashMap;
import java.util.Map;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypeWalk;

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

	public static Square requiredNextWalk(Square[][] squares, PositionChessboard currentPosition, TypeWalk walk){
		Square s = null;
		try {
			switch (walk) {
			case FRONT: s = squares[currentPosition.getLetter()][currentPosition.getNumber()+1];	
				break;
			case BACK: s = squares[currentPosition.getLetter()][currentPosition.getNumber()-1];	
				break;
			case LEFT: s = squares[currentPosition.getLetter()-1][currentPosition.getNumber()];	
				break;
			case RIGHT: s = squares[currentPosition.getLetter()+1][currentPosition.getNumber()];	
				break;
			case LEFT_UP: s = squares[currentPosition.getLetter()-1][currentPosition.getNumber()+1];	
				break;
			case RIGHT_UP: s = squares[currentPosition.getLetter()+1][currentPosition.getNumber()+1];	
				break;
			case LEFT_DOWN: s = squares[currentPosition.getLetter()-1][currentPosition.getNumber()-1];	
				break;
			case RIGHT_DOWN: s = squares[currentPosition.getLetter()+1][currentPosition.getNumber()-1];	
				break;
			default:
				break;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}		
		return s;
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
		return "[" + this.getPosition() + "-" + this.getPiece() + "-" + 
					(this.getPiece()!=null? this.getPiece().getPlayer() : "  ") + "]";
	}
}
