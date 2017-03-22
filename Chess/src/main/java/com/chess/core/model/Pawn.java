package com.chess.core.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.movement.BehaviorChess;
import com.chess.core.util.MovementUtils;

public class Pawn extends Piece implements BehaviorChess {

	public Pawn(TypePiece typePiece, TypeColor color, Player player){
		super(typePiece, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(PositionChessboard position, Square[][] squares) {
		return MovementUtils.movementAvailableFront(1, position, squares, Boolean.FALSE, this.getPlayer());
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces() {
		List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableLeftUp(1, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightUp(1, Boolean.TRUE, this.getPlayer()));
		return list;
	}
	
	@Override
	public void defend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString(){
		return super.getTypePiece().toString();
	}


	
	
}
