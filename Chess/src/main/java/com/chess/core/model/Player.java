package com.chess.core.model;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.chess.core.Chessboard;
import com.chess.core.client.PlayerMode;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePlayer;

public class Player extends PlayerMode {

	private static final long serialVersionUID = -1984094323086984495L;

	public Player() {
	}

	public Player(final TypePlayer typePlayer) {
		super(typePlayer);
	}

	public Player(final String nome, final Long score, final TypePlayer typePlayer) {
		super(nome, score, typePlayer);
	}

	@Override
	public boolean isAI() {
		return false;
	}

	@Override
	public ImmutablePair<PositionChessboard, PositionChessboard> play(final Chessboard chessboard) {
		return null;
	}

}
