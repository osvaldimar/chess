package com.chess.core.model;

import static com.chess.core.enums.PositionChessboard.C1;
import static com.chess.core.enums.PositionChessboard.C8;
import static com.chess.core.enums.PositionChessboard.D1;
import static com.chess.core.enums.PositionChessboard.D8;
import static com.chess.core.enums.PositionChessboard.E1;
import static com.chess.core.enums.PositionChessboard.E8;
import static com.chess.core.enums.PositionChessboard.F1;
import static com.chess.core.enums.PositionChessboard.F8;
import static com.chess.core.enums.PositionChessboard.G1;
import static com.chess.core.enums.PositionChessboard.G8;

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

	private static final long serialVersionUID = 5706012151965254803L;

	public King() {
	}

	public King(final TypeColor color, final TypePlayer player) {
		super(TypePiece.KING, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(final PositionChessboard position, final Square[][] squares) {
		final List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableFront(1, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableBack(1, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeft(1, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRight(1, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftDown(1, position, squares, Boolean.FALSE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightDown(1, position, squares, Boolean.FALSE, this.getPlayer()));
		return list;
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces(final PositionChessboard position,
			final Square[][] squares) {
		final List<PositionChessboard> list = new ArrayList<>();
		list.addAll(MovementUtils.movementAvailableFront(1, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableBack(1, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeft(1, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRight(1, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableLeftDown(1, position, squares, Boolean.TRUE, this.getPlayer()));
		list.addAll(MovementUtils.movementAvailableRightDown(1, position, squares, Boolean.TRUE, this.getPlayer()));
		return list;
	}

	public List<PositionChessboard> specialMovementCastling(final Square[][] squares) {
		final List<PositionChessboard> list = new ArrayList<>();
		if (!CheckmateValidator.isKingInCheck(ChessboardPieceFactory.buildCloneSquares(squares), this.getPlayer())
				&& this.getCountMovements() == 0) {
			if (this.getColor() == TypeColor.WHITE) {
				this.specialCastlingWhite(squares, list);
			} else {
				this.specialCastlingBlack(squares, list);
			}
		}
		return list;
	}

	private void specialCastlingWhite(final Square[][] squares, final List<PositionChessboard> list) {
		final Piece rookA = squares[PositionChessboard.A1.getLetter()][PositionChessboard.A1.getNumber()].getPiece();
		final Piece rookH = squares[PositionChessboard.H1.getLetter()][PositionChessboard.H1.getNumber()].getPiece();
		if (rookA != null && rookA.getTypePiece() == TypePiece.ROOK && rookA.getCountMovements() == 0) {
			if (MovementUtils.movementAvailableLeft(3, PositionChessboard.E1, squares, Boolean.FALSE, this.getPlayer())
					.size() == 3) {
				// validate position D1 and C1 no threatened by check opponent
				if (this.validatePassingSquaresOfCastlingThreatenedEnemy(D1, squares, TypePlayer.W)
						&& this.validatePassingSquaresOfCastlingThreatenedEnemy(C1, squares, TypePlayer.W)) {
					list.add(PositionChessboard.C1);
				}
			}
		}
		if (rookH != null && rookH.getTypePiece() == TypePiece.ROOK && rookH.getCountMovements() == 0) {
			if (MovementUtils.movementAvailableRight(2, PositionChessboard.E1, squares, Boolean.FALSE, this.getPlayer())
					.size() == 2) {
				// validate position G1 and F1 no threatened by check opponent
				if (this.validatePassingSquaresOfCastlingThreatenedEnemy(G1, squares, TypePlayer.W)
						&& this.validatePassingSquaresOfCastlingThreatenedEnemy(F1, squares, TypePlayer.W)) {
					list.add(PositionChessboard.G1);
				}
			}
		}
	}

	private void specialCastlingBlack(final Square[][] squares, final List<PositionChessboard> list) {
		final Piece rookA = squares[PositionChessboard.A8.getLetter()][PositionChessboard.A8.getNumber()].getPiece();
		final Piece rookH = squares[PositionChessboard.H8.getLetter()][PositionChessboard.H8.getNumber()].getPiece();
		if (rookA != null && rookA.getTypePiece() == TypePiece.ROOK && rookA.getCountMovements() == 0) {
			if (MovementUtils.movementAvailableLeft(3, PositionChessboard.E8, squares, Boolean.FALSE, this.getPlayer())
					.size() == 3) {
				// validate position D8 and C8 no threatened by check opponent
				if (this.validatePassingSquaresOfCastlingThreatenedEnemy(D8, squares, TypePlayer.B)
						&& this.validatePassingSquaresOfCastlingThreatenedEnemy(C8, squares, TypePlayer.B)) {
					list.add(PositionChessboard.C8);
				}
			}
		}
		if (rookH != null && rookH.getTypePiece() == TypePiece.ROOK && rookH.getCountMovements() == 0) {
			if (MovementUtils.movementAvailableRight(2, PositionChessboard.E8, squares, Boolean.FALSE, this.getPlayer())
					.size() == 2) {
				// validate position G8 and F8 no threatened by check opponent
				if (this.validatePassingSquaresOfCastlingThreatenedEnemy(G8, squares, TypePlayer.B)
						&& this.validatePassingSquaresOfCastlingThreatenedEnemy(F8, squares, TypePlayer.B)) {
					list.add(PositionChessboard.G8);
				}
			}
		}
	}

	private boolean validatePassingSquaresOfCastlingThreatenedEnemy(final PositionChessboard posVerify,
			final Square[][] sq, final TypePlayer type) {
		boolean isCheck;
		if (type == TypePlayer.W) {
			final Piece kingTemp = sq[E1.getLetter()][E1.getNumber()].getPiece();
			sq[E1.getLetter()][E1.getNumber()].removePiece();
			sq[posVerify.getLetter()][posVerify.getNumber()].addPiece(kingTemp);
			isCheck = CheckmateValidator.isKingInCheck(sq, type);
			sq[posVerify.getLetter()][posVerify.getNumber()].removePiece();
			sq[E1.getLetter()][E1.getNumber()].addPiece(kingTemp);
		} else {
			final Piece kingTemp = sq[E8.getLetter()][E8.getNumber()].getPiece();
			sq[E8.getLetter()][E8.getNumber()].removePiece();
			sq[posVerify.getLetter()][posVerify.getNumber()].addPiece(kingTemp);
			isCheck = CheckmateValidator.isKingInCheck(sq, type);
			sq[posVerify.getLetter()][posVerify.getNumber()].removePiece();
			sq[E8.getLetter()][E8.getNumber()].addPiece(kingTemp);
		}
		return !isCheck;
	}
}
