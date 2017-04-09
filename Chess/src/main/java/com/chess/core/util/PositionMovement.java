package com.chess.core.util;

import com.chess.core.enums.PositionChessboard;

public class PositionMovement {

	private PositionChessboard actualPosition;
	private PositionChessboard destinyPosition;
	
	public PositionMovement(PositionChessboard actualPosition, PositionChessboard destinyPosition) {
		this.actualPosition = actualPosition;
		this.destinyPosition = destinyPosition;
	}

	public PositionChessboard getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(PositionChessboard actualPosition) {
		this.actualPosition = actualPosition;
	}

	public PositionChessboard getDestinyPosition() {
		return destinyPosition;
	}

	public void setDestinyPosition(PositionChessboard destinyPosition) {
		this.destinyPosition = destinyPosition;
	}
	
}
