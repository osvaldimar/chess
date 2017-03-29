package com.chess.core.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.exception.CheckMoveException;
import com.chess.core.exception.CheckStateException;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class ValidateCheck {

	public static void validateKingExposedInCheckBeforeSimulation(Square[][] clone, Player player) throws CheckStateException{
		if(validateKingInCheck(clone, player))
			throw new CheckStateException();
	}
	public static void validateKingExposedInCheckAfterSimulation(Square[][] clone, Player player) throws CheckMoveException{
		if(validateKingInCheck(clone, player))
			throw new CheckMoveException();
	}

	public static boolean validateKingInCheck(Square[][] clone, Player player) {
		
		PositionChessboard positionKing = PieceUtils.getPositionKingInChessboard(clone, player);		
		//lambda validate if  exist any piece of enemy that is available to take position square of my king
		for(Square[] s : clone){
			Stream<PositionChessboard> positionStream = Arrays.stream(s).filter(f -> !f.isAvailable())
			.filter(p -> p.getPiece().getPlayer().getTypePlayer()!=player.getTypePlayer())
			.filter(q -> q.getPiece().movementAvailableToTakePieces(q.getPosition(), clone)
					.contains(positionKing)).map(map -> map.getPosition());
			System.out.println("POSITIONS TO TAKES KING: " + positionStream);
			Optional<PositionChessboard> findFirst = positionStream.findFirst();
			if(findFirst.isPresent())
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
}
