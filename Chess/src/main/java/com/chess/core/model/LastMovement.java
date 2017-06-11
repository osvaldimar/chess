package com.chess.core.model;

import java.io.Serializable;

import com.chess.core.enums.PositionChessboard;

public class LastMovement implements Serializable {

	private static final long serialVersionUID = 6736865738507588400L;
	
	private int idMovement;
	private NameMovement name;
	private PositionChessboard destroyed;
	private PositionChessboard movedFrom;
	private PositionChessboard movedTo;
	private PositionChessboard castlingFrom;
	private PositionChessboard castlingTo;
	
	public enum NameMovement{
		MOVED, TAKEN, PASSANT, CASTLING;
	}
	
	public LastMovement() {
	}

	public int getIdMovement() {
		return idMovement;
	}
	public void setIdMovement(int idMovement) {
		this.idMovement = idMovement;
	}
	public NameMovement getName() {
		return name;
	}
	public void setName(NameMovement name) {
		this.name = name;
	}
	public PositionChessboard getDestroyed() {
		return destroyed;
	}
	public void setDestroyed(PositionChessboard destroyed) {
		this.destroyed = destroyed;
	}
	public PositionChessboard getMovedFrom() {
		return movedFrom;
	}
	public void setMovedFrom(PositionChessboard movedFrom) {
		this.movedFrom = movedFrom;
	}
	public PositionChessboard getMovedTo() {
		return movedTo;
	}
	public void setMovedTo(PositionChessboard movedTo) {
		this.movedTo = movedTo;
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
