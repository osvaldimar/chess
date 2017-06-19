package com.chess.core.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.enums.TypeWalk;
import com.chess.core.util.MovementUtils;

public class Pawn extends Piece {

	private static final long serialVersionUID = 1482781427128515524L;

	private Square squarePassantToTakePawnEnemy;
	private PositionChessboard positionDestinyTakeElPassant;

	public Pawn() {
	}

	public Pawn(final TypeColor color, final TypePlayer player) {
		super(TypePiece.PAWN, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(final PositionChessboard position, final Square[][] squares) {
		return (this.getPlayer() == TypePlayer.W
				? MovementUtils.movementAvailableFront(this.getCountMovements() < 1 ? 2 : 1, position, squares,
						Boolean.FALSE, this.getPlayer())
				: MovementUtils.movementAvailableBack(this.getCountMovements() < 1 ? 2 : 1, position, squares,
						Boolean.FALSE, this.getPlayer()));
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces(final PositionChessboard position,
			final Square[][] squares) {
		final List<PositionChessboard> list = new ArrayList<>();
		if (this.getPlayer() == TypePlayer.W) {
			list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.TRUE, this.getPlayer()));
			list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.TRUE, this.getPlayer()));
		} else {
			list.addAll(MovementUtils.movementAvailableLeftDown(1, position, squares, Boolean.TRUE, this.getPlayer()));
			list.addAll(MovementUtils.movementAvailableRightDown(1, position, squares, Boolean.TRUE, this.getPlayer()));
		}
		return list;
	}

	public List<PositionChessboard> specialMovementPassant(final PositionChessboard positionSelected,
			final Square[][] squaresChessboard, final Square lastSquarePiceMoved) {
		this.squarePassantToTakePawnEnemy = null;
		this.positionDestinyTakeElPassant = null;
		final List<PositionChessboard> list = new ArrayList<>();

		if (lastSquarePiceMoved != null && lastSquarePiceMoved.getPiece().getTypePiece() == TypePiece.PAWN
				&& lastSquarePiceMoved.getPiece().getCountMovements() == 1
				&& lastSquarePiceMoved.getPosition().getNumber() == positionSelected.getNumber()) { // last
																									// is
																									// pawn,
																									// side
																									// to
																									// side
			if (this.getColor() == TypeColor.WHITE && positionSelected.getNumber() == 4) { // number
																							// 5-1
																							// start
																							// zero
				if (Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT) != null
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT)
								.getPosition() == lastSquarePiceMoved.getPosition()
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT_UP)
								.isAvailable()) {
					// el passant
					this.squarePassantToTakePawnEnemy = lastSquarePiceMoved;
					list.add(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT_UP)
							.getPosition());

				} else if (Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT) != null
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT)
								.getPosition() == lastSquarePiceMoved.getPosition()
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT_UP)
								.isAvailable()) {
					// el passant
					this.squarePassantToTakePawnEnemy = lastSquarePiceMoved;
					list.add(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT_UP)
							.getPosition());
				}

			} else if (this.getColor() == TypeColor.BLACK && positionSelected.getNumber() == 3) { // number
																									// 4-1
																									// start
																									// zero
				if (Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT) != null
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT)
								.getPosition() == lastSquarePiceMoved.getPosition()
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT_DOWN)
								.isAvailable()) {
					// el passant
					this.squarePassantToTakePawnEnemy = lastSquarePiceMoved;
					list.add(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT_DOWN)
							.getPosition());

				} else if (Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT) != null
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT)
								.getPosition() == lastSquarePiceMoved.getPosition()
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT_DOWN)
								.isAvailable()) {
					// el passant
					this.squarePassantToTakePawnEnemy = lastSquarePiceMoved;
					list.add(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT_DOWN)
							.getPosition());
				}
			}
		}
		if (list.size() == 1) {
			this.positionDestinyTakeElPassant = list.get(0);
		}
		return list;
	}

	public boolean isPositionDestinyTakeElPassant(final PositionChessboard destiny) {
		if (this.squarePassantToTakePawnEnemy != null && this.positionDestinyTakeElPassant == destiny) {
			return true;
		}
		return false;
	}

	public Square getSquarePassantToTakePawnEnemy() {
		return this.squarePassantToTakePawnEnemy;
	}

	@Override
	public String toString() {
		return super.getTypePiece().toString();
	}

}
