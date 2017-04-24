package com.chess.core.client;

import com.chess.core.GameApplication;
import com.chess.core.enums.TypePlayer;

public abstract class PlayerMode {

	private String nome;
	private Long score;
	private TypePlayer typePlayer;
	private int quantityMovement;
	
	public PlayerMode(TypePlayer typePlayer){
		this.typePlayer = typePlayer;
		this.quantityMovement = 0;
	}
	
	public PlayerMode(String nome, Long score, TypePlayer typePlayer) {
		this.nome = nome;
		this.score = score;
		this.typePlayer = typePlayer;
		this.quantityMovement = 0;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	
	public TypePlayer getTypePlayer() {
		return typePlayer;
	}
	
	public int getQuantityMovement() {
		return quantityMovement;
	}

	public void setQuantityMovement(int quantityMovement) {
		this.quantityMovement = quantityMovement;
	}
	
	public void incrementMovements() {
		this.quantityMovement++;
	}

	@Override
	public String toString() {
		return this.typePlayer.toString();
	}

	public abstract boolean isAI();

	public abstract void play(GameApplication gameApplication);
}
