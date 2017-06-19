package com.chess.core.model;

import java.io.Serializable;

import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.movement.BehaviorChess;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class Piece implements BehaviorChess, Serializable {

	private static final long serialVersionUID = 8969772025960660892L;

	private TypePiece typePiece;
	private TypeColor color;
	private TypePlayer player;
	private int countMovements;

	public Piece() {
	}

	public Piece(final TypePiece typePiece, final TypeColor color, final TypePlayer player) {
		this.typePiece = typePiece;
		this.color = color;
		this.player = player;
		this.countMovements = 0;
	}

	public void incrementMovements() {
		this.countMovements++;
	}

	public TypePiece getTypePiece() {
		return this.typePiece;
	}

	public void setTypePiece(final TypePiece typePiece) {
		this.typePiece = typePiece;
	}

	public TypeColor getColor() {
		return this.color;
	}

	public void setColor(final TypeColor color) {
		this.color = color;
	}

	public TypePlayer getPlayer() {
		return this.player;
	}

	public void setPlayer(final TypePlayer player) {
		this.player = player;
	}

	public int getCountMovements() {
		return this.countMovements;
	}

	@Override
	public String toString() {
		return this.typePiece.toString();
	}
}
