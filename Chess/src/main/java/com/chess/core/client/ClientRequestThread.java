package com.chess.core.client;

import com.chess.core.ChessGamePool;

public class ClientRequestThread extends Thread {

	private ResponseClient response;
	private ChessGamePool pool;
	
	public ClientRequestThread(ChessGamePool pool) {
		this.pool = pool;		
	}

	@Override
	public void run() {
		this.response = this.pool.joinPlayerOnlineMultiChessPool();		
	}

	public ResponseClient getResponseClient() {
		return response;
	}
	
}
