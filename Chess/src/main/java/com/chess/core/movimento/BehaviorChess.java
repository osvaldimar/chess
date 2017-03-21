package com.chess.core.movimento;

import java.util.List;

import com.chess.core.enums.PositionChessboard;

public interface BehaviorChess {

	List<PositionChessboard> movementAvailable();
	List<PositionChessboard> movementAvailableToTakePieces();
	void defend();
	
	
}
