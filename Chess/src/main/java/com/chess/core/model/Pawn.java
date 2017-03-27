package com.chess.core.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.util.MovementUtils;

public class Pawn extends Piece {

	private boolean firstMovement = Boolean.TRUE;
	
	public Pawn(TypeColor color, Player player){
		super(TypePiece.PAWN, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(PositionChessboard position, Square[][] squares) {
		return (getPlayer().getTypePlayer() == TypePlayer.P1 ? 
				MovementUtils.movementAvailableFront(firstMovement ? 2:1, position, squares, Boolean.FALSE, this.getPlayer()) :
				MovementUtils.movementAvailableBack(firstMovement ? 2:1, position, squares, Boolean.FALSE, this.getPlayer()) );
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces(PositionChessboard position, Square[][] squares) {
		List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.TRUE, this.getPlayer()));
		return list;
	}

	@Override
	public String toString(){
		return super.getTypePiece().toString();
	}


	
	
}
