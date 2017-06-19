package com.chess.core.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.movement.BehaviorChess;
import com.chess.core.util.MovementUtils;

public class Rook extends Piece implements BehaviorChess {

	private static final long serialVersionUID = -8352502203453846071L;

	public Rook() {
	}

	public Rook(final TypeColor color, final TypePlayer player) {
		super(TypePiece.ROOK, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(final PositionChessboard position, final Square[][] squares) {
		final List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableFront(8, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableBack(8, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeft(8, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRight(8, position, squares, Boolean.FALSE, this.getPlayer()));
		return list;
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces(final PositionChessboard position,
			final Square[][] squares) {
		final List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableFront(8, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableBack(8, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeft(8, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRight(8, position, squares, Boolean.TRUE, this.getPlayer()));
		return list;
	}

}
