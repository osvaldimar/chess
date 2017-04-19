package com.chess.core.memory;

import java.util.ArrayList;
import java.util.List;

public class ChessboardMemory {

	private List<PositionMemory> list;
	
	public ChessboardMemory(){
		list = new ArrayList<>();
	}
	
	public void addMovement(PositionMemory positionMemory){
		list.add(positionMemory);
	}
	
	public List<PositionMemory> getListMovements() {
		return list;
	}
	
	public PositionMemory undo(){
		PositionMemory p = list.get(list.size() - 1);
		list.remove(list.size() - 1);
		return p;
	}
}
