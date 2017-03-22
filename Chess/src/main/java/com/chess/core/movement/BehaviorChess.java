package com.chess.core.movement;

import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.Square;

public interface BehaviorChess {

	List<PositionChessboard> movementAvailable(PositionChessboard position, Square[][] squares);
	List<PositionChessboard> movementAvailableToTakePieces();
	void defend();
	
	
}
