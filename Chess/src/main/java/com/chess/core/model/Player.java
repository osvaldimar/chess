package com.chess.core.model;

public class Player {

	private String nome;
	private Long score;
	
	public Player(){		
	}
	
	public Player(String nome, Long score) {
		this.nome = nome;
		this.score = score;
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
	
}
