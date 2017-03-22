package com.chess.core.util;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class MovementUtils {


	private static boolean movementAvailableAndCapture(List<PositionChessboard> list, Square square, 
			boolean capture, Player playerCurrent) {
		if(square.isAvailable()){
			if(!capture){
				//TODO validation king in check
				list.add(square.getPosition());
			}
		}else{
			if(capture && PieceUtils.isPieceOfEnemy(square, playerCurrent)){
				//TODO validation king in check
				list.add(square.getPosition());
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public static List<PositionChessboard> movementAvailableFront(int limit, PositionChessboard position, Square[][] squares, boolean capture, Player playerCurrent){
		
		List<PositionChessboard> list = new ArrayList<>();
		boolean loop = true;
		
		Square sq = squares[0][0];
		loop = movementAvailableAndCapture(list, sq, capture, playerCurrent);
		
		
		return list;
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
	
	public static List<PositionChessboard> movementAvailableLeftUp(int limit, boolean capture, Player playerCurrent){
		return null;
	}
	
	public static List<PositionChessboard> movementAvailableRightUp(int limit, boolean capture, Player playerCurrent){
		return null;
	}
	
	public static List<PositionChessboard> movementAvailableLeftDown(){
		return null;
	}
	
	public static List<PositionChessboard> movementAvailableRightDown(){
		return null;
	}
	
}
