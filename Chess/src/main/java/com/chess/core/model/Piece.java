package com.chess.core.model;

import java.io.Serializable;

import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.movement.BehaviorChess;

public abstract class Piece implements BehaviorChess, Serializable {

	private static final long serialVersionUID = 8969772025960660892L;
	
	private TypePiece typePiece;
	private TypeColor color;
	private TypePlayer player;
	private int countMovements;
	
	public Piece(TypePiece typePiece, TypeColor color, TypePlayer player){
		this.typePiece = typePiece;
		this.color = color;
		this.player = player;
		this.countMovements = 0;
	}

	public void incrementMovements(){
		countMovements++;
	}
	
	public TypePiece getTypePiece() {
		return typePiece;
	}

	public void setTypePiece(TypePiece typePiece) {
		this.typePiece = typePiece;
	}

	public TypeColor getColor() {
		return color;
	}

	public void setColor(TypeColor color) {
		this.color = color;
	}

	public TypePlayer getPlayer() {
		return player;
	}

	public void setPlayer(TypePlayer player) {
		this.player = player;
	}
	
	public int getCountMovements() {
		return countMovements;
	}
	
	@Override
	public String toString() {
		return this.typePiece.toString();
	}	
}
