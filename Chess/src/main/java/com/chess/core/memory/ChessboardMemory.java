package com.chess.core.memory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePlayer;

public class ChessboardMemory {

	private Map<TypePlayer, Queue<PositionMemory>> mapQueue;
	
	public ChessboardMemory(){
		mapQueue = new HashMap<>();
		mapQueue.put(TypePlayer.W, new LinkedList<>());
		mapQueue.put(TypePlayer.B, new LinkedList<>());
	}
	
	public void addPositionQueue(PositionMemory pos, TypePlayer player){
		if(mapQueue.get(player).size() >= 5){
			mapQueue.get(player).remove();
		}
		mapQueue.get(player).add(pos);
	}

	public boolean isLastThreeMovementsEqualPositions(){
		return validateQueuePositions(TypePlayer.W) || validateQueuePositions(TypePlayer.B);
	}

	private boolean validateQueuePositions(TypePlayer player) {
		if(mapQueue.get(player).size() >= 5){
			List<PositionMemory> collect = mapQueue.get(player).stream().collect(Collectors.toList());
			
			PositionChessboard x = collect.get(0).getActualPosition();  //actual x
			PositionChessboard y = collect.get(0).getDestinyPosition(); //destiny y
			
			if((x == collect.get(1).getDestinyPosition() && y == collect.get(1).getActualPosition() && 
					collect.get(0).getPieceGotten() == null && collect.get(1).getPieceGotten() == null ) &&
				(y == collect.get(2).getDestinyPosition() && x == collect.get(2).getActualPosition() && collect.get(2).getPieceGotten() == null ) &&
				(x == collect.get(3).getDestinyPosition() && y == collect.get(3).getActualPosition() && collect.get(3).getPieceGotten() == null ) &&
				(y == collect.get(4).getDestinyPosition() && x == collect.get(4).getActualPosition() && collect.get(4).getPieceGotten() == null ) ){		
				return true;
			}
		}
		return false;
	}
	
}
