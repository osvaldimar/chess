package com.chess.core.model;

import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.movimento.BehaviorChess;

public class Pawn extends Piece implements BehaviorChess {

	public Pawn(){
		this.setTipoPeca(TypePiece.PEAO);
	}

	@Override
	public List<PositionChessboard> movementAvailable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void defend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString(){
		return this.getTipoPeca().toString();		
	}


	
	
}
