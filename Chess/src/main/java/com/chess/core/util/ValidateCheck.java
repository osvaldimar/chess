package com.chess.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
			Optional<PositionChessboard> findFirst = positionStream.findFirst();
			if(findFirst.isPresent())
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public static boolean processValidateCheck(Square[][] clone, Player player) {
		
		PositionChessboard positionKing = PieceUtils.getPositionKingInChessboard(clone, player);	
		List<Square> listSquare = new ArrayList<>();
		for(Square[] s : clone){
			listSquare.addAll(Arrays.stream(s).filter(f -> !f.isAvailable())
			.filter(p -> p.getPiece().getPlayer().getTypePlayer()==player.getTypePlayer())
			.collect(Collectors.toList()));			
		}
		System.out.println("list square test check: " + listSquare);
		listSquare.stream().forEach(f -> {
			f.getPiece().movementAvailable(f.getPosition(), clone)
				.stream().filter(p -> {
					boolean check = false;
					clone[p.getLetter()][p.getNumber()].addPiece(f.getPiece());
					clone[f.getPosition().getLetter()][f.getPosition().getNumber()].removePiece();
					check = !validateKingInCheck(clone, player);
					clone[f.getPosition().getLetter()][f.getPosition().getNumber()].addPiece(f.getPiece());
					clone[p.getLetter()][p.getNumber()].removePiece();
					return check;
				});
		});
		
		return Boolean.FALSE;
	}

	
	
}
