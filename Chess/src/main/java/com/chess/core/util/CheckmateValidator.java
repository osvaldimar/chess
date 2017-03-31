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
import com.chess.core.exception.CheckmateException;
import com.chess.core.model.Piece;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class CheckmateValidator {
	
	/**
	 * Method validate if king is in check before of a simulation of piece moves
	 * @param clone
	 * @param player
	 * @throws CheckStateException
	 */
	public static void validateKingExposedInCheckBeforeSimulation(Square[][] clone, Player player) throws CheckStateException{
		if(isKingInCheck(clone, player))
			throw new CheckStateException();
	}
	/**
	 * Method validate if king is exposed in check after a simulation of piece moves
	 * @param clone
	 * @param player
	 * @throws CheckMoveException
	 */
	public static void validateKingExposedInCheckAfterSimulation(Square[][] clone, Player player) throws CheckMoveException{
		if(isKingInCheck(clone, player))
			throw new CheckMoveException();
	}

	/**
	 * Method validate if king of player is in check
	 * @param clone
	 * @param player
	 * @return true if king is in check
	 */
	public static boolean isKingInCheck(Square[][] clone, Player player) {
		
		PositionChessboard positionKing = PieceUtils.getPositionKingInChessboard(clone, player);		
		//lambda validate if  exist any piece of enemy that is available to take position square of my king
		Stream<Square[]> filterSquarePieceEnemy = Arrays.stream(clone).filter(s -> Arrays.stream(s)
				.filter(f -> !f.isAvailable()).filter(p -> p.getPiece().getPlayer().getTypePlayer()!=player.getTypePlayer())
				.filter(q -> q.getPiece().movementAvailableToTakePieces(q.getPosition(), clone)
				.contains(positionKing)).map(map -> map.getPosition()).findFirst().isPresent());
		
		if(filterSquarePieceEnemy.findFirst().isPresent())
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	public static void processValidatesCheckmate(Square[][] clone, Player player) throws CheckmateException, CheckStateException {
		
		List<Square> listSquareOfAllMyPieces = new ArrayList<>();
		for(Square[] s : clone){
			listSquareOfAllMyPieces.addAll(Arrays.stream(s).filter(f -> !f.isAvailable())
			.filter(p -> p.getPiece().getPlayer().getTypePlayer()==player.getTypePlayer())
			.sorted((o1,o2) -> {
					if(o1.getPiece().getTypePiece().getValue() < o2.getPiece().getTypePiece().getValue())
						return -1;
					if(o1.getPiece().getTypePiece().getValue() > o2.getPiece().getTypePiece().getValue())
						return 1;
					return 0;
			}).collect(Collectors.toList()));
		}
		
		List<Square> listPossiblePiecesPreventCheckmate = listSquareOfAllMyPieces.stream()
				.filter(mySqu -> executePossibleMovements(clone, mySqu)
				.stream().filter(walkPos -> !isCheckOfPiecePositionMovedInSimulation(clone, mySqu, walkPos, player))
				.collect(Collectors.toList()).size()>=1).collect(Collectors.toList());
		
		System.out.println("\nlist all my pieces: \n" + listSquareOfAllMyPieces);
		System.out.println("\nlist only possible pieces to take or block the enemy check: \n" + listPossiblePiecesPreventCheckmate);

		if(listPossiblePiecesPreventCheckmate.isEmpty()) {
			throw new CheckmateException();
		}else{
			throw new CheckStateException();
		}
	}
	
	private static List<PositionChessboard> executePossibleMovements(Square[][] clone, Square mySqu) {
		List<PositionChessboard> listPositions = new ArrayList<>();
		listPositions.addAll(mySqu.getPiece().movementAvailable(mySqu.getPosition(), clone));
		listPositions.addAll(mySqu.getPiece().movementAvailableToTakePieces(mySqu.getPosition(), clone));
		return listPositions;
	}
	
	private static boolean isCheckOfPiecePositionMovedInSimulation(Square[][] clone, Square mySqu, PositionChessboard walkPos, Player player) {
		boolean isCheck;
		Piece myPieceTemp = mySqu.getPiece();
		Piece enemyPieceTemp = clone[walkPos.getLetter()][walkPos.getNumber()].getPiece();
		clone[walkPos.getLetter()][walkPos.getNumber()].addPiece(myPieceTemp);
		clone[mySqu.getPosition().getLetter()][mySqu.getPosition().getNumber()].removePiece();
		isCheck = isKingInCheck(clone, player);
		clone[mySqu.getPosition().getLetter()][mySqu.getPosition().getNumber()].addPiece(myPieceTemp);
		clone[walkPos.getLetter()][walkPos.getNumber()].addPiece(enemyPieceTemp);
		return isCheck;
	}
	
	
}
