package com.chess.core.client;

import java.io.Serializable;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.chess.core.Chessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePlayer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class PlayerMode implements Serializable {

	private static final long serialVersionUID = -8871506825016951192L;

	private String nome;
	private Long score;
	private TypePlayer typePlayer;
	private int quantityMovement;

	public PlayerMode() {
	}

	public PlayerMode(final TypePlayer typePlayer) {
		this.typePlayer = typePlayer;
		this.quantityMovement = 0;
	}

	public PlayerMode(final String nome, final Long score, final TypePlayer typePlayer) {
		this.nome = nome;
		this.score = score;
		this.typePlayer = typePlayer;
		this.quantityMovement = 0;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public Long getScore() {
		return this.score;
	}

	public void setScore(final Long score) {
		this.score = score;
	}

	public TypePlayer getTypePlayer() {
		return this.typePlayer;
	}

	public int getQuantityMovement() {
		return this.quantityMovement;
	}

	public void setQuantityMovement(final int quantityMovement) {
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

	public abstract ImmutablePair<PositionChessboard, PositionChessboard> play(Chessboard chessboard);
}
