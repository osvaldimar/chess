package com.chess.core.util;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePlayer;
import com.chess.core.enums.TypeWalk;
import com.chess.core.model.Square;

public class MovementUtils {
	
	private static List<PositionChessboard> movementAvailableGeneric(int limit, PositionChessboard myPosition, 
			Square[][] squares, boolean capture, TypePlayer playerCurrent, TypeWalk walk){
		
		List<PositionChessboard> list = new ArrayList<>();
		PositionChessboard current = myPosition;	//position current
		int cont = 0;
		while(cont < limit){
			Square squareWalk = Square.requiredNextWalk(squares, current, walk);
			if(squareWalk == null){
				return list;
			}
			if(squareWalk.isAvailable()){
				if(!capture){
					list.add(squareWalk.getPosition());
				}
			}else{
				if(capture && PieceUtils.isPieceOfEnemy(squareWalk, playerCurrent)){
					list.add(squareWalk.getPosition());
				}
				break;
			}
			cont++;
			current = squareWalk.getPosition();	//current position now is square walked front
		}			
		return list;
	}
	
	public static List<PositionChessboard> movementAvailableFront(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.FRONT);
	}
	
	public static List<PositionChessboard> movementAvailableBack(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.BACK);
	}
	
	public static List<PositionChessboard> movementAvailableLeft(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.LEFT);
	}
	
	public static List<PositionChessboard> movementAvailableRight(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.RIGHT);
	}
	
	public static List<PositionChessboard> movementAvailableLeftUp(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.LEFT_UP);
	}
	
	public static List<PositionChessboard> movementAvailableRightUp(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.RIGHT_UP);
	}
	
	public static List<PositionChessboard> movementAvailableLeftDown(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.LEFT_DOWN);
	}
	
	public static List<PositionChessboard> movementAvailableRightDown(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.RIGHT_DOWN);
	}
	
	public static List<PositionChessboard> movementAvailableKnight(PositionChessboard myPosition, Square[][] squares, boolean capture, TypePlayer playerCurrent){
		
		List<PositionChessboard> list = new ArrayList<>();
		movementAvailableKnight(capture, playerCurrent, list, squares, myPosition.getLetter()-1, myPosition.getNumber()+2);
		movementAvailableKnight(capture, playerCurrent, list, squares, myPosition.getLetter()+1, myPosition.getNumber()+2);
		movementAvailableKnight(capture, playerCurrent, list, squares, myPosition.getLetter()+2, myPosition.getNumber()+1);
		movementAvailableKnight(capture, playerCurrent, list, squares, myPosition.getLetter()+2, myPosition.getNumber()-1);
		movementAvailableKnight(capture, playerCurrent, list, squares, myPosition.getLetter()+1, myPosition.getNumber()-2);
		movementAvailableKnight(capture, playerCurrent, list, squares, myPosition.getLetter()-1, myPosition.getNumber()-2);
		movementAvailableKnight(capture, playerCurrent, list, squares, myPosition.getLetter()-2, myPosition.getNumber()-1);
		movementAvailableKnight(capture, playerCurrent, list, squares, myPosition.getLetter()-2, myPosition.getNumber()+1);
		return list;
	}

	private static void movementAvailableKnight(boolean capture, TypePlayer playerCurrent, List<PositionChessboard> list,
			Square[][] squares, int x, int y) {		
		try {
			Square squareWalk = squares[x][y];
			if(squareWalk.isAvailable()){
				if(!capture){
					list.add(squareWalk.getPosition());
				}
			}else{
				if(capture && PieceUtils.isPieceOfEnemy(squareWalk, playerCurrent)){
					list.add(squareWalk.getPosition());
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return;
		}
	}
	
}
