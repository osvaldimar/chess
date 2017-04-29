package com.chess.core.client;

import com.chess.core.ChessGamePool;

public class ClientRequestThreadMulti extends Thread {

	private ResponseClient responseMulti;
	private ChessGamePool pool;
	
	public ClientRequestThreadMulti(ChessGamePool pool) {
		this.pool = pool;		
	}

	@Override
	public void run() {
		this.responseMulti = this.pool.joinMultiPlayerOnlineChessPool();
	}

	public ResponseClient getResponseClientMultiplayerOnline() {
		return responseMulti;
	}	
}
