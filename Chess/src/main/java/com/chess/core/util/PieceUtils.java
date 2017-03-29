package com.chess.core.util;

import java.util.Arrays;
import java.util.Optional;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class PieceUtils {

	public static boolean isPieceOfEnemy(Square square, Player playerCurrent){
		if(square != null){
			return !(square.getPiece().getPlayer().getTypePlayer() == playerCurrent.getTypePlayer());
		}
		return false;
	}
	
	public static PositionChessboard getPositionKingInChessboard(Square[][] squares, Player player){
		for(Square[] s : squares){
			Optional<Square> findFirst = Arrays.stream(s).filter(p -> !p.isAvailable())
			.filter(k->k.getPiece().getTypePiece()==TypePiece.KING && 
			k.getPiece().getPlayer().getTypePlayer()==player.getTypePlayer())
			.findFirst();
			if(findFirst.isPresent())
				return findFirst.get().getPosition();
		}
		return null;
	}
	
}
