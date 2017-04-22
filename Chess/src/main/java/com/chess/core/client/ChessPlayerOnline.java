package com.chess.core.client;

import com.chess.core.GameApplication;
import com.chess.core.client.ChessPlayer;
import com.chess.core.client.KeyUUIDChess;

public interface ChessPlayerOnline<T> extends ChessPlayer<T> {

	GameApplication findGameOnline(KeyUUIDChess key);
}
