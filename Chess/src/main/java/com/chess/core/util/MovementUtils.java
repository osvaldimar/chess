package com.chess.core.util;

import java.util.ArrayList;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.model.Player;
import com.chess.core.model.Square;

public class MovementUtils {
	
	public static List<PositionChessboard> movementAvailableFront(int limit, PositionChessboard position, Square[][] squares, boolean capture, Player playerCurrent){
		
		List<PositionChessboard> list = new ArrayList<>();		
		Square myPosition = squares[position.getLetter()][position.getNumber()];
		PositionChessboard current = myPosition.getPosition();	//position current
		int cont = 0;
		while(cont < limit){
			Square squareWalk = squares[current.getLetter()][current.getNumber()+1];	//walk front + 1
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
