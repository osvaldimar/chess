package com.xadrez.core.time;

import java.util.Timer;
import java.util.TimerTask;

import com.chess.core.enums.TypePlayer;

public class ChessClock {

	private ChessTime time;
	private Timer timeWhite;
	private Timer timeBlack;
	private TimerTask task;
	
	public enum ChessTime{
		FIVE_MINUTES(5);
		private int time;
		
		ChessTime(int time){
			this.time = time;			
		}
		public int getTime() {
			return time;
		}
	}
	
	public ChessClock(ChessTime time) {
		this.time = time;
		task = TimerTask
	}
	
	public resumePlayerStopOpponent(TypePlayer typePlayerResume){
		if(typePlayerResume == TypePlayer.W){
			timeWhite.schedule(task, delay);
			timeWhite.g
		}else{
			timeBlack.resume();
		}
	}
}
