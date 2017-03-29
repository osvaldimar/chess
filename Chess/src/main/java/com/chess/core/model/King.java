package com.chess.core.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.movement.BehaviorChess;
import com.chess.core.util.MovementUtils;

public class King extends Piece implements BehaviorChess {

	private boolean isFirstMovement = Boolean.TRUE;
	
	public King(TypeColor color, Player player) {
		super(TypePiece.KING, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(PositionChessboard position, Square[][] squares) {
		List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableFront(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableBack(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeft(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRight(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftDown(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightDown(1, position, squares, Boolean.FALSE, getPlayer()));
		return list;
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces(PositionChessboard position, Square[][] squares) {
		List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableFront(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableBack(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeft(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRight(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftDown(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightDown(1, position, squares, Boolean.TRUE, getPlayer()));
		return list;
	}

	public boolean isFirstMovement() {
		return isFirstMovement;
	}
}
