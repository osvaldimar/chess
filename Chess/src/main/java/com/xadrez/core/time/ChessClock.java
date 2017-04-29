package com.xadrez.core.time;

import java.util.Timer;
import java.util.TimerTask;

import com.chess.core.enums.TypePlayer;

public class ChessClock {

	private int time;
	private Timer timeWhite;
	private Timer timeBlack;
	private TimerTask task;
	private int minute;
	private int second;
	
	
	
	/*public ChessClock(ChessTime time) {
		this.time = time;
		this.minute = time.time;
	}
	
	public resumePlayerStopOpponent(TypePlayer typePlayerResume){
		if(typePlayerResume == TypePlayer.W){
			timeWhite.schedule(task, delay);
			timeWhite.g
		}else{
			timeBlack.resume();
		}
	}*/
}
