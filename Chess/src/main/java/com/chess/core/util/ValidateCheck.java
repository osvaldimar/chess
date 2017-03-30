package com.chess.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.chess.core.Chessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.exception.CheckmateException;
import com.chess.core.exception.CheckMoveException;
import com.chess.core.exception.CheckStateException;
import com.chess.core.model.Piece;
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
	
	public static void processValidateCheckMate(Square[][] clone, Player player) throws CheckmateException {
		
		List<Square> listSquare = new ArrayList<>();
		for(Square[] s : clone){
			listSquare.addAll(Arrays.stream(s).filter(f -> !f.isAvailable())
			.filter(p -> p.getPiece().getPlayer().getTypePlayer()==player.getTypePlayer())
			.sorted((o1,o2) -> {
					if(o1.getPiece().getTypePiece().getValue() < o2.getPiece().getTypePiece().getValue())
						return -1;
					if(o1.getPiece().getTypePiece().getValue() > o2.getPiece().getTypePiece().getValue())
						return 1;
					return 0;
			})
			.collect(Collectors.toList()));
		}
		System.out.println("\nlist my pieces for take or block check enemy: \n" + listSquare);
		
		List<Square> listPiecesTakeEnemy = listSquare.stream().filter(mySqu ->
			mySqu.getPiece().movementAvailableToTakePieces(mySqu.getPosition(), clone)
				.stream()
				.filter(nextPos -> !isCheckPositionPieceSimulation(clone, mySqu, nextPos, player))
				.collect(Collectors.toList()).size()>=1).collect(Collectors.toList());
		System.out.println("listPiecesTakeEnemy: " + listPiecesTakeEnemy);
		
		List<Square> listPiecesMoveBlockEnemy = listSquare.stream().filter(mySqu ->
		mySqu.getPiece().movementAvailable(mySqu.getPosition(), clone)
			.stream()
			.filter(nextPos -> !isCheckPositionPieceSimulation(clone, mySqu, nextPos, player))
			.collect(Collectors.toList()).size()>=1).collect(Collectors.toList());
		System.out.println("listPiecesMoveBlockEnemy: " + listPiecesMoveBlockEnemy);
		
		if(listPiecesTakeEnemy.isEmpty() && listPiecesMoveBlockEnemy.isEmpty()){
			throw new CheckmateException();
		}
	}
	
	private static boolean isCheckPositionPieceSimulation(Square[][] clone, Square mySqu, PositionChessboard nextPos, Player player) {
		boolean check;
		Piece myTemp = mySqu.getPiece();
		Piece enemyTemp = clone[nextPos.getLetter()][nextPos.getNumber()].getPiece();
		//Chessboard.printCloneChessboard(clone, "Before simulation - Piece: " + mySqu.getPiece());
		clone[nextPos.getLetter()][nextPos.getNumber()].addPiece(myTemp);
		clone[mySqu.getPosition().getLetter()][mySqu.getPosition().getNumber()].removePiece();
		check = validateKingInCheck(clone, player);
		//Chessboard.printCloneChessboard(clone, "After simulation - check: " + check);
		clone[mySqu.getPosition().getLetter()][mySqu.getPosition().getNumber()].addPiece(myTemp);
		clone[nextPos.getLetter()][nextPos.getNumber()].addPiece(enemyTemp);
		//Chessboard.printCloneChessboard(clone, "Undo simulation - Piece: " + mySqu.getPiece());
		return check;
	}
	
	
}
