package com.chess.core.model;

import com.chess.core.enums.TypePlayer;

public class Player {

	private String nome;
	private Long score;
	private TypePlayer typePlayer;
	
	public Player(){		
	}
	
	public Player(String nome, Long score, TypePlayer typePlayer) {
		this.nome = nome;
		this.score = score;
		this.typePlayer = typePlayer;
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
	
	@Override
	public String toString() {
		return this.typePlayer.toString();
	}
	
}
