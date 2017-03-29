package com.chess.core.util;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.exception.CheckMoveException;
import com.chess.core.exception.CheckStateException;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class ValidateCheck {

	public static void validateKingExposedInCheck(Square[][] clone, Player player) throws CheckMoveException, CheckStateException{
		
		PositionChessboard positionKing = PieceUtils.getPositionKingInChessboard(clone, player);
		
		
//		if(player.getTypePlayer() == TypePlayer.P1){
//			throw new CheckMoveException();
//		}
//		
//		if(player.getTypePlayer() == TypePlayer.P2){
//			throw new CheckStateException();
//		}
		
	}
	
}
