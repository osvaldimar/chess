package com.chess.core.util;

import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class PieceUtils {

	public static boolean isPieceOfEnemy(Square square, Player playerCurrent){
		return square.getPiece().getPlayer().getNome().equals(playerCurrent.getNome());
	}
	
}
