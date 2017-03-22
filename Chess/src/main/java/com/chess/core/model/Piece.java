package com.chess.core.model;

import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;

public abstract class Piece {

	private TypePiece typePiece;
	private TypeColor color;
	private Player player;
	
	public Piece(TypePiece typePiece, TypeColor color, Player player){
		this.typePiece = typePiece;
		this.color = color;
		this.player = player;
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	@Override
	public String toString() {
		return this.typePiece.toString();
	}
	
}
