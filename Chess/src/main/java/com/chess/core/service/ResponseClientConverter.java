package com.chess.core.service;

import java.util.List;
import java.util.stream.Collectors;

import com.chess.core.ResponseChessboard;

public final class ResponseClientConverter {

	public static ResponseClient convert(ResponseChessboard res){
		
		switch (res.getStatusResponse()) {
		case CLICKED: 			
			break;
		case MOVED: 			
			break;
		default:
			
			break;
		}
		
		return null;
		//START, OFF, NONE_ACTION, OPPONENT_TURN, CLICKED, MOVED, MOVED_PROMOTION, MARK_OFF, 
		//NONE_CHECK, EXPOSED_CHECK, CHECK, CHECKMATE;
		
	}
	
}
