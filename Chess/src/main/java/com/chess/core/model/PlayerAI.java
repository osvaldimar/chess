package com.chess.core.model;

import com.chess.core.client.PlayerMode;
import com.chess.core.enums.TypePlayer;

public class PlayerAI extends PlayerMode{
	
	public PlayerAI(TypePlayer typePlayer){
		super(typePlayer);
	}
	
	public PlayerAI(String nome, Long score, TypePlayer typePlayer) {
		super(nome, score, typePlayer);
	}

	@Override
	public boolean isAI() {
		return true;
	}
}
