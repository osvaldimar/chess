package com.chess.core.model;

import com.chess.core.enums.TypePlayer;

public class PlayerAI implements PlayerMode {

	private String nome;
	private Long score;
	private TypePlayer typePlayer;
	
	public PlayerAI(TypePlayer typePlayer){
		this.typePlayer = typePlayer;
	}
	
	public PlayerAI(String nome, Long score, TypePlayer typePlayer) {
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

	@Override
	public String nextMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPromotion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isArtificialInteligence() {
		return true;
	}

	@Override
	public void run() {
		
		try {
			Thread.sleep(5000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
