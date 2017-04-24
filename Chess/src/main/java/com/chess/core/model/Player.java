package com.chess.core.model;

import com.chess.core.GameApplication;
import com.chess.core.client.PlayerMode;
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
	public void play(GameApplication gameApplication) {
	}
	
}
