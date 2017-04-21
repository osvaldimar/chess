package com.chess.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.exception.CheckMoveException;
import com.chess.core.exception.CheckStateException;
import com.chess.core.exception.CheckmateException;
import com.chess.core.exception.Draw3PositionsException;
import com.chess.core.exception.Draw50MovementsException;
import com.chess.core.exception.DrawStalemateException;
import com.chess.core.memory.ChessboardMemory;
import com.chess.core.model.Pawn;
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
	public static void validateKingExposedInCheckBeforeSimulation(Square[][] clone, TypePlayer player) throws CheckStateException{
		if(isKingInCheck(clone, player))
			throw new CheckStateException();
	}
	/**
	 * Method validate if king is exposed in check after a simulation of piece moves
	 * @param clone
	 * @param player
	 * @throws CheckMoveException
	 */
	public static void validateKingExposedInCheckAfterSimulation(Square[][] clone, TypePlayer player) throws CheckMoveException{
		if(isKingInCheck(clone, player))
			throw new CheckMoveException();
	}

	/**
	 * Method validate if king of player is in check
	 * @param clone
	 * @param player
	 * @return true if king is in check
	 */
	public static boolean isKingInCheck(Square[][] clone, TypePlayer player) {
		
		PositionChessboard positionKing = PieceUtils.getPositionKingInChessboard(clone, player);		
		//lambda validate if  exist any piece of enemy that is available to take position square of my king
		Stream<Square[]> filterSquarePieceEnemy = Arrays.stream(clone).filter(s -> Arrays.stream(s)
				.filter(f -> !f.isAvailable()).filter(p -> p.getPiece().getPlayer()!=player)
				.filter(q -> q.getPiece().movementAvailableToTakePieces(q.getPosition(), clone)
				.contains(positionKing)).map(map -> map.getPosition()).findFirst().isPresent());
		
		if(filterSquarePieceEnemy.findFirst().isPresent())
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	public static List<Piece> getPiecesEnemyDoCheck(Square[][] clone, TypePlayer player) {
		List<Piece> list = new ArrayList<>();
		PositionChessboard positionKing = PieceUtils.getPositionKingInChessboard(clone, player);
		for(Square[] s : clone){
			list.addAll(Arrays.stream(s)
				.filter(f -> !f.isAvailable()).filter(p -> p.getPiece().getPlayer()!=player)
				.filter(q -> q.getPiece().movementAvailableToTakePieces(q.getPosition(), clone)
				.contains(positionKing)).map(m -> m.getPiece()).collect(Collectors.toList()));
		}
		return list;
	}
	
	public static void processValidatesCheckmate(Square[][] clone, TypePlayer player, Square lastSquarePiceMovedChessboard) 
			throws CheckmateException, CheckStateException {
		
		List<Square> listSquareOfAllMyPieces = new ArrayList<>();
		for(Square[] s : clone){
			listSquareOfAllMyPieces.addAll(Arrays.stream(s).filter(f -> !f.isAvailable())
			.filter(p -> p.getPiece().getPlayer()==player)
			.sorted((o1,o2) -> {
					if(o1.getPiece().getTypePiece().getValue() < o2.getPiece().getTypePiece().getValue())
						return -1;
					if(o1.getPiece().getTypePiece().getValue() > o2.getPiece().getTypePiece().getValue())
						return 1;
					return 0;
			}).collect(Collectors.toList()));
		}
		
		List<Square> listPossiblePiecesPreventCheckmate = listSquareOfAllMyPieces.stream()
				.filter(mySqu -> executePossibleMovements(clone, mySqu, lastSquarePiceMovedChessboard)
				.stream().filter(walkPos -> !isCheckOfPiecePositionMovedInSimulation(clone, mySqu, walkPos, player))
				.collect(Collectors.toList()).size()>=1).collect(Collectors.toList());
		
		System.out.println("\nlist only possible pieces to take or block the enemy check: \n" 
				+ listPossiblePiecesPreventCheckmate);

		if(listPossiblePiecesPreventCheckmate.isEmpty()) {
			throw new CheckmateException();
		}else{
			throw new CheckStateException();
		}
	}
	
	private static List<PositionChessboard> executePossibleMovements(Square[][] clone, Square mySqu, Square lastSquarePiceMovedChessboard) {
		List<PositionChessboard> listPositions = new ArrayList<>();
		listPositions.addAll(mySqu.getPiece().movementAvailable(mySqu.getPosition(), clone));
		listPositions.addAll(mySqu.getPiece().movementAvailableToTakePieces(mySqu.getPosition(), clone));
		//verify if pawn can take a pawn in el passant, then add list to take
		if(mySqu.getPiece().getTypePiece() == TypePiece.PAWN){
			Pawn pawn = (Pawn)mySqu.getPiece();
			listPositions.addAll(pawn.specialMovementPassant(mySqu.getPosition(), clone, lastSquarePiceMovedChessboard));
		}
		return listPositions;
	}
	
	private static boolean isCheckOfPiecePositionMovedInSimulation(Square[][] clone, Square mySqu, PositionChessboard walkPos, TypePlayer player) {
		boolean isCheck;
		Piece myPieceTemp = mySqu.getPiece();
		Piece enemyPieceTemp = clone[walkPos.getLetter()][walkPos.getNumber()].getPiece();
		clone[walkPos.getLetter()][walkPos.getNumber()].addPiece(myPieceTemp);
		clone[mySqu.getPosition().getLetter()][mySqu.getPosition().getNumber()].removePiece();
		//verifi my pawn has a square of passant to take
		if(myPieceTemp.getTypePiece() == TypePiece.PAWN){
			Pawn pawn = (Pawn) myPieceTemp;
			if(pawn.isPositionDestinyTakeElPassant(walkPos)){
				PositionChessboard posPawnEnemyPassant = pawn.getSquarePassantToTakePawnEnemy().getPosition();
				clone[posPawnEnemyPassant.getLetter()][posPawnEnemyPassant.getNumber()].removePiece();	//remove pawn enemy taking by passant
			}
		}
		isCheck = isKingInCheck(clone, player);
		clone[mySqu.getPosition().getLetter()][mySqu.getPosition().getNumber()].addPiece(myPieceTemp);
		clone[walkPos.getLetter()][walkPos.getNumber()].addPiece(enemyPieceTemp);
		return isCheck;
	}
	
	public static void processValidatesDraw(Square[][] clone, TypePlayer typePlayerTurn, 
			Square lastSquarePiceMoved, Player player1, Player player2, ChessboardMemory chessMemory) 
					throws DrawStalemateException, Draw50MovementsException, Draw3PositionsException {		
		//stalemate
		try {
			processValidatesCheckmate(clone, typePlayerTurn, lastSquarePiceMoved);
		} catch (CheckmateException e) {
			throw new DrawStalemateException();
		} catch (CheckStateException e) {
			//not draw
		}		
		//50 movements
		if(player1.getQuantityMovement() == 50 && player2.getQuantityMovement() == 50){
			throw new Draw50MovementsException();
		}		
		//3 last movements equal, rule three positions
		if(chessMemory.isLastThreeMovementsEqualPositions()){
			throw new Draw3PositionsException();
		}
	}
	
	
}
