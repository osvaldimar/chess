package com.chess.core.memory;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.Piece;

public class PositionMemory {

	private PositionChessboard actualPosition;
	private PositionChessboard destinyPosition;
	private Piece pieceMoved;
	private Piece pieceGotten;
	
	public PositionMemory(PositionChessboard actualPosition, PositionChessboard destinyPosition, Piece pieceMoved, Piece pieceGotten) {
		this.actualPosition = actualPosition;
		this.destinyPosition = destinyPosition;
		this.pieceMoved = pieceMoved;
		this.pieceGotten = pieceGotten;		
	}
	
	public PositionChessboard getActualPosition() {
		return actualPosition;
	}
	public PositionChessboard getDestinyPosition() {
		return destinyPosition;
	}
	public Piece getPieceMoved() {
		return pieceMoved;
	}	
	public Piece getPieceGotten() {
		return pieceGotten;
	}
}
