package com.chess.core.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.movement.BehaviorChess;
import com.chess.core.util.CheckmateValidator;
import com.chess.core.util.ChessboardPieceFactory;
import com.chess.core.util.MovementUtils;

public class King extends Piece implements BehaviorChess {

	public King(TypeColor color, TypePlayer player) {
		super(TypePiece.KING, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(PositionChessboard position, Square[][] squares) {
		List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableFront(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableBack(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeft(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRight(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftDown(1, position, squares, Boolean.FALSE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightDown(1, position, squares, Boolean.FALSE, getPlayer()));
		return list;
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces(PositionChessboard position, Square[][] squares) {
		List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableFront(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableBack(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeft(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRight(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftDown(1, position, squares, Boolean.TRUE, getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightDown(1, position, squares, Boolean.TRUE, getPlayer()));		
		return list;
	}
	
	public List<PositionChessboard> specialMovementCastling(Square[][] squares){
		List<PositionChessboard> list = new ArrayList<>();
		if(!CheckmateValidator.isKingInCheck(ChessboardPieceFactory.buildCloneSquares(squares), getPlayer())
				&& getCountMovements() == 0){
			if(getColor() == TypeColor.WHITE){
				specialCastlingWhite(squares, list);
			}else{
				specialCastlingBlack(squares, list);
			}
		}
		return list;
	}

	private void specialCastlingWhite(Square[][] squares, List<PositionChessboard> list) {
		Piece rookA = squares[PositionChessboard.A1.getLetter()][PositionChessboard.A1.getNumber()].getPiece();
		Piece rookH = squares[PositionChessboard.H1.getLetter()][PositionChessboard.H1.getNumber()].getPiece();
		if(rookA != null && rookA.getTypePiece() == TypePiece.ROOK && rookA.getCountMovements() == 0){
			if(MovementUtils.movementAvailableLeft(3, PositionChessboard.E1, squares, Boolean.FALSE, getPlayer()).size() == 3){
				list.add(PositionChessboard.C1);
			}
		}
		if(rookH != null && rookH.getTypePiece() == TypePiece.ROOK && rookH.getCountMovements() == 0){
			if(MovementUtils.movementAvailableRight(2, PositionChessboard.E1, squares, Boolean.FALSE, getPlayer()).size() == 2){
				list.add(PositionChessboard.G1);
			}
		}
	}
	
	private void specialCastlingBlack(Square[][] squares, List<PositionChessboard> list) {
		Piece rookA = squares[PositionChessboard.A8.getLetter()][PositionChessboard.A8.getNumber()].getPiece();
		Piece rookH = squares[PositionChessboard.H8.getLetter()][PositionChessboard.H8.getNumber()].getPiece();
		if(rookA != null && rookA.getTypePiece() == TypePiece.ROOK && rookA.getCountMovements() == 0){
			if(MovementUtils.movementAvailableLeft(3, PositionChessboard.E8, squares, Boolean.FALSE, getPlayer()).size() == 3){
				list.add(PositionChessboard.C8);
			}
		}
		if(rookH != null && rookH.getTypePiece() == TypePiece.ROOK && rookH.getCountMovements() == 0){
			if(MovementUtils.movementAvailableRight(2, PositionChessboard.E8, squares, Boolean.FALSE, getPlayer()).size() == 2){
				list.add(PositionChessboard.G8);
			}
		}
	}
}
