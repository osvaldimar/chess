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

	private Square squarePassantToTakePawnEnemy;
	private PositionChessboard positionDestinyTakeElPassant;
	
	public Pawn(TypeColor color, TypePlayer player){
		super(TypePiece.PAWN, color, player);
	}

	@Override
	public List<PositionChessboard> movementAvailable(PositionChessboard position, Square[][] squares) {
		return (getPlayer() == TypePlayer.W ? 
				MovementUtils.movementAvailableFront(this.getCountMovements() < 1 ? 2:1, position, squares, Boolean.FALSE, this.getPlayer()) :
				MovementUtils.movementAvailableBack(this.getCountMovements() < 1 ? 2:1, position, squares, Boolean.FALSE, this.getPlayer()) );
	}

	@Override
	public List<PositionChessboard> movementAvailableToTakePieces(PositionChessboard position, Square[][] squares) {
		List<PositionChessboard> list = new ArrayList<>();
		if(getPlayer() == TypePlayer.W){
			list.addAll(MovementUtils.movementAvailableLeftUp(1, position, squares, Boolean.TRUE, this.getPlayer()));
			list.addAll(MovementUtils.movementAvailableRightUp(1, position, squares, Boolean.TRUE, this.getPlayer()));
		}else{
			list.addAll(MovementUtils.movementAvailableLeftDown(1, position, squares, Boolean.TRUE, this.getPlayer()));
			list.addAll(MovementUtils.movementAvailableRightDown(1, position, squares, Boolean.TRUE, this.getPlayer()));	
		}
		return list;
	}
	
	public List<PositionChessboard> specialMovementPassant(PositionChessboard positionSelected,
			Square[][] squaresChessboard, Square lastSquarePiceMoved) {
		squarePassantToTakePawnEnemy = null;
		positionDestinyTakeElPassant = null;
		List<PositionChessboard> list = new ArrayList<>();
		
		if(lastSquarePiceMoved != null && lastSquarePiceMoved.getPiece().getTypePiece() == TypePiece.PAWN
				&& lastSquarePiceMoved.getPiece().getCountMovements() == 1
				&& lastSquarePiceMoved.getPosition().getNumber() == positionSelected.getNumber()){			//last is pawn, side to side
			if(getColor() == TypeColor.WHITE && positionSelected.getNumber() == 4){ 		//number 5-1 start zero
				if(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT) != null
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT).getPosition() 
						== lastSquarePiceMoved.getPosition()
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT_UP).isAvailable()){
					//el passant
					squarePassantToTakePawnEnemy = lastSquarePiceMoved;
					list.add(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT_UP).getPosition());
					
				}else if(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT) != null
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT).getPosition() 
						== lastSquarePiceMoved.getPosition()
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT_UP).isAvailable()){
					//el passant
					squarePassantToTakePawnEnemy = lastSquarePiceMoved;
					list.add(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT_UP).getPosition());
				}
				
			}else if(getColor() == TypeColor.BLACK && positionSelected.getNumber() == 3){	//number 4-1 start zero
				if(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT) != null
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT).getPosition() 
						== lastSquarePiceMoved.getPosition()
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT_DOWN).isAvailable()){
					//el passant
					squarePassantToTakePawnEnemy = lastSquarePiceMoved;
					list.add(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.LEFT_DOWN).getPosition());
					
				}else if(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT) != null
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT).getPosition() 
						== lastSquarePiceMoved.getPosition()
						&& Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT_DOWN).isAvailable()){
					//el passant
					squarePassantToTakePawnEnemy = lastSquarePiceMoved;
					list.add(Square.requiredNextWalk(squaresChessboard, positionSelected, TypeWalk.RIGHT_DOWN).getPosition());
				}
			}
		}
		if(list.size() == 1) positionDestinyTakeElPassant = list.get(0);
		return list;
	}
	
	public boolean isPositionDestinyTakeElPassant(PositionChessboard destiny) {
		if(squarePassantToTakePawnEnemy != null
				&& positionDestinyTakeElPassant == destiny){
			return true;
		}
		return false;
	}

	public Square getSquarePassantToTakePawnEnemy() {
		return squarePassantToTakePawnEnemy;
	}
	
	@Override
	public String toString(){
		return super.getTypePiece().toString();
	}
	
}
