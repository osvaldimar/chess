package com.chess.core.model;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.chess.core.Chessboard;
import com.chess.core.GameApplication;
import com.chess.core.client.PlayerMode;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePlayer;

public class Player extends PlayerMode{
	
	
	public Player(TypePlayer typePlayer){
		super(typePlayer);
	}
	
	public Player(String nome, Long score, TypePlayer typePlayer) {
		super(nome, score, typePlayer);
	}

	@Override
	public boolean isAI() {
		return false;
	}

	@Override
	public ImmutablePair<PositionChessboard, PositionChessboard> play(Chessboard chessboard) {
		return null;
	}
	
}
