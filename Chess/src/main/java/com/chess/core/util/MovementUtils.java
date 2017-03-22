package com.chess.core.util;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeWalk;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class MovementUtils {
	
	private static List<PositionChessboard> movementAvailableGeneric(int limit, PositionChessboard myPosition, 
			Square[][] squares, boolean capture, Player playerCurrent, TypeWalk walk){
		
		List<PositionChessboard> list = new ArrayList<>();
		PositionChessboard current = myPosition;	//position current
		int cont = 0;
		while(cont < limit){
			Square squareWalk;
			try {
				squareWalk = Square.requiredNextWalk(squares, current, walk);
			} catch (IndexOutOfBoundsException e) {
				return list;
			}
			if(squareWalk.isAvailable()){
				if(!capture){
					//TODO validation king in check
					list.add(squareWalk.getPosition());
				}
			}else{
				if(capture && PieceUtils.isPieceOfEnemy(squareWalk, playerCurrent)){
					//TODO validation king in check
					list.add(squareWalk.getPosition());
				}
				break;
			}
			cont++;
			current = squareWalk.getPosition();	//current position now is square walked front
		}
			
		return list;
	}
	
	public static List<PositionChessboard> movementAvailableFront(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, Player playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.FRONT);
	}
	
	public static List<PositionChessboard> movementAvailableBack(){
		return null;
	}
	
	public static List<PositionChessboard> movementAvailableLeft(){
		return null;
	}
	
	public static List<PositionChessboard> movementAvailableRight(){
		return null;
	}
	
	public static List<PositionChessboard> movementAvailableLeftUp(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, Player playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.LEFT_UP);
	}
	
	public static List<PositionChessboard> movementAvailableRightUp(int limit, PositionChessboard myPosition, Square[][] squares, boolean capture, Player playerCurrent){
		return movementAvailableGeneric(limit, myPosition, squares, capture, playerCurrent, TypeWalk.RIGHT_UP);
	}
	
	public static List<PositionChessboard> movementAvailableLeftDown(){
		return null;
	}
	
	public static List<PositionChessboard> movementAvailableRightDown(){
		return null;
	}
	
}
