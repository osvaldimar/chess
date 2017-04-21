package com.chess.core.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.movement.BehaviorChess;
import com.chess.core.util.MovementUtils;

public class Knight extends Piece implements BehaviorChess {

	public Knight(TypeColor color, TypePlayer player) {
		super(TypePiece.KNIGHT, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(PositionChessboard position, Square[][] squares) {
		List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableKnight(position, squares, Boolean.FALSE, getPlayer()));
		return list;
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces(PositionChessboard position, Square[][] squares) {
		List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableKnight(position, squares, Boolean.TRUE, getPlayer()));
		return list;
	}

}
