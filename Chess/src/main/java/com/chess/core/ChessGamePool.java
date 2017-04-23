package com.chess.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

import com.chess.core.client.KeyClient;
import com.chess.core.client.KeyUUIDChess;
import com.chess.core.client.ResponseClient;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Player;
import com.chess.core.service.ChessMultiplayerOnline;

public class ChessGamePool {
	
	private Map<KeyUUIDChess, GameApplication> map = new HashMap<>();
	private Queue<KeyClient> queue = new LinkedList<>();
	
	public GameApplication findGameApp(String uuid, String typePlayer) {
		if(TypePlayer.getEnum(typePlayer) == TypePlayer.W){
			KeyUUIDChess key = new KeyUUIDChess(UUID.fromString(uuid), null);
			Optional<KeyUUIDChess> findFirst = map.keySet().stream().filter(k -> k.equals(key)).findFirst();
			if(findFirst.isPresent()) 
				return map.get(findFirst.get());
		}else if(TypePlayer.getEnum(typePlayer) == TypePlayer.B){
			KeyUUIDChess key = new KeyUUIDChess(null, UUID.fromString(uuid));
			Optional<KeyUUIDChess> findFirst = map.keySet().stream().filter(k -> k.equals(key)).findFirst();
			if(findFirst.isPresent()) 
				return map.get(findFirst.get());
		}
		return null;
	}

	public KeyClient joinPlayerOnlineCommmonChessPool() {
		return null;
	}
	
	public ResponseClient joinPlayerOnlineMultiChessPool() {
		KeyClient keyClient = this.joinPoolGameMultiAddQueue();
		while(true){
			try {
				if(this.isJoinPoolGameMulti(keyClient))
					break;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return buildResponseClientStartAdequateForThePlayer(keyClient);
	}
	
	private ResponseClient buildResponseClientStartAdequateForThePlayer(KeyClient keyClient){
		System.out.println("keyclient method build: " + keyClient);
		return new ResponseClient.Builder()
				.status(ResponseChessboard.StatusResponse.START.toString())
				.currentPlayer(TypePlayer.W.toString())
				.turn(TypePlayer.W.toString())
				.keyClient(keyClient)
				.build();
	}
	
	private synchronized KeyClient joinPoolGameMultiAddQueue(){
		if(queue.isEmpty()){
			KeyClient keyClient = new KeyClient(UUID.randomUUID(), TypePlayer.W);
			queue.add(keyClient);
			System.out.println("Thread name: " + Thread.currentThread().getName() + " - queue new W = " + keyClient);
			return keyClient;
		}else{
			KeyClient keyClientW = queue.remove();
			KeyClient keyClientB = new KeyClient(UUID.randomUUID(), TypePlayer.B);			
			ChessMultiplayerOnline chessOnline = new ChessMultiplayerOnline();
			GameApplication game = chessOnline.startChess(new Player(TypePlayer.W), new Player(TypePlayer.B));
			map.put(new KeyUUIDChess(keyClientW.getKey(), keyClientB.getKey()), game);
			System.out.println("Thread name: " + Thread.currentThread().getName() + " - queue new B = " + keyClientB);
			return keyClientB;
		}
	}
	
	private synchronized boolean isJoinPoolGameMulti(KeyClient keyClient){
		GameApplication findGameApp = this.findGameApp(keyClient.getKey().toString(), keyClient.getType().toString());
		//System.out.println("Method isJoinPoolGameMulti:  - Thread name: " + Thread.currentThread().getName() + " - " + keyClient);
		//System.out.println("Map games: " + map + " - achou game = " + (findGameApp != null));
		return findGameApp != null;
	}

	public int getTotalChessPool(){
		return this.map.size();
	}	
	public int getTotalChessQueuePending(){
		return this.queue.size();
	}
}
