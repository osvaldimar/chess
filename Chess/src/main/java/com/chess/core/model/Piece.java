package com.chess.core.model;

import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;

public abstract class Piece {

	private TypePiece tipoPeca;
	private TypeColor cor;
	private Player jogador;
	
	public Piece(){
	}
	
	public Piece(TypePiece tipoPeca, TypeColor cor, Player jogador) {
		this.tipoPeca = tipoPeca;
		this.cor = cor;
		this.jogador = jogador;
	}
	
	public TypePiece getTipoPeca() {
		return tipoPeca;
	}

	public void setTipoPeca(TypePiece tipoPeca) {
		this.tipoPeca = tipoPeca;
	}

	public TypeColor getCor() {
		return cor;
	}

	public void setCor(TypeColor cor) {
		this.cor = cor;
	}

	public Player getJogador() {
		return jogador;
	}
	public void setJogador(Player jogador) {
		this.jogador = jogador;
	}
	
}
