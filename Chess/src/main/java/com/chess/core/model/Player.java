package com.chess.core.model;

import com.chess.core.Chessboard;
import com.chess.core.enums.TypePlayer;

public class Player implements PlayerMode {

	private String nome;
	private Long score;
	private TypePlayer typePlayer;
	private Square[][] squares;
	
	public Player(TypePlayer typePlayer){
		this.typePlayer = typePlayer;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run() {
		
	}

	public void setSquares(Square[][] squares) {
		this.squares = squares;		
	}
	
}
