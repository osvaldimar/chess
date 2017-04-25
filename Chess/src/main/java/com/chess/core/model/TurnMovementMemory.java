package com.chess.core.model;

import com.chess.core.enums.PositionChessboard;

public class TurnMovementMemory {

	private String nameMovement;
	private PositionChessboard destroyed;
	private PositionChessboard from;
	private PositionChessboard to;
	private PositionChessboard castlingFrom;
	private PositionChessboard castlingTo;
	
	public TurnMovementMemory() {
	}
	
	public String getNameMovement() {
		return nameMovement;
	}
	public void setNameMovement(String nameMovement) {
		this.nameMovement = nameMovement;
	}
	public PositionChessboard getDestroyed() {
		return destroyed;
	}
	public void setDestroyed(PositionChessboard destroyed) {
		this.destroyed = destroyed;
	}
	public PositionChessboard getFrom() {
		return from;
	}
	public void setFrom(PositionChessboard from) {
		this.from = from;
	}
	public PositionChessboard getTo() {
		return to;
	}
	public void setTo(PositionChessboard to) {
		this.to = to;
	}
	public PositionChessboard getCastlingFrom() {
		return castlingFrom;
	}
	public void setCastlingFrom(PositionChessboard castlingFrom) {
		this.castlingFrom = castlingFrom;
	}
	public PositionChessboard getCastlingTo() {
		return castlingTo;
	}
	public void setCastlingTo(PositionChessboard castlingTo) {
		this.castlingTo = castlingTo;
	}
	
}
