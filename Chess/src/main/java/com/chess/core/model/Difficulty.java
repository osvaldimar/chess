package com.chess.core.model;

import java.util.Arrays;

public class Difficulty {

	private Level levelAI;
	private Deduction levelDeduction;
	private boolean deductionLoop;

	public Difficulty(Level levelAI, Deduction levelDeduction) {
		this.levelAI = levelAI;
		this.levelDeduction = levelDeduction;
	}

	public enum Level{
		LEVEL_AI_1(1),	LEVEL_AI_2(2), LEVEL_AI_3(3), LEVEL_AI_4(4);
		private int value;
		
		Level(int value){
			this.value = value;
		}
		public static Level getEnum(int value){
			return Arrays.stream(Level.values()).filter(f->f.getValue()==value).findFirst().orElse(LEVEL_AI_1);
		}
		public int getValue() {
			return value;
		}
	}
	
	public enum Deduction{
		LEVEL_DEDUCTION_1(1),	LEVEL_DEDUCTION_2(2);
		private int value;
		
		Deduction(int value){
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}

	public Level getLevelAI() {
		return levelAI;
	}
	public Deduction getLevelDeduction() {
		return levelDeduction;
	}
	public boolean isDeductionLoop() {
		return deductionLoop;
	}
	public void setDeductionLoop(boolean deductionLoop) {
		this.deductionLoop = deductionLoop;
	}
}
