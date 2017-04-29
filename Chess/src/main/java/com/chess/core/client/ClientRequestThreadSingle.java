package com.chess.core.client;

import com.chess.core.ChessGamePool;

public class ClientRequestThreadSingle extends Thread {

	private ResponseClient responseSingle;
	private ChessGamePool pool;
	
	public ClientRequestThreadSingle(ChessGamePool pool) {
		this.pool = pool;		
	}

	@Override
	public void run() {
		this.responseSingle = this.pool.joinSinglePlayerOnlineChessPool();
	}

	public ResponseClient getResponseClientSingleplayerOnline() {
		return responseSingle;
	}
	
}
