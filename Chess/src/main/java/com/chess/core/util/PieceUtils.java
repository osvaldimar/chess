package com.chess.core.util;

import java.util.Arrays;
import java.util.Optional;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class PieceUtils {

	public static boolean isPieceOfEnemy(Square square, TypePlayer playerCurrent){
		if(square != null){
			return !(square.getPiece().getPlayer() == playerCurrent);
		}
		return false;
	}
	
	public static PositionChessboard getPositionKingInChessboard(Square[][] squares, TypePlayer player){
		for(Square[] s : squares){
			Optional<Square> findFirst = Arrays.stream(s).filter(p -> !p.isAvailable())
			.filter(k->k.getPiece().getTypePiece()==TypePiece.KING && 
			k.getPiece().getPlayer()==player)
			.findFirst();
			if(findFirst.isPresent())
				return findFirst.get().getPosition();
		}
		return null;
	}
	
}
