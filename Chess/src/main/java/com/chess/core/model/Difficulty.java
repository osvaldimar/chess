package com.chess.core.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

import org.mockito.internal.util.collections.Sets;

public class Difficulty implements Serializable{

	private static final long serialVersionUID = 8983849443846764927L;
	
	private Level levelAI;
	private Deduction levelDeductionAI;
	private boolean deductionLoopAI;

	public Difficulty(Level levelAI, Deduction levelDeduction) {
		this.levelAI = levelAI;
		this.levelDeductionAI = levelDeduction;
	}
	
	public static Difficulty createSimpleDifficulty(SimpleDifficulty levelDifficulty){
		return new Difficulty(Level.getEnum(levelDifficulty.getLevel()), Deduction.getEnum(levelDifficulty.getDeduction()));
	}
	
	public enum SimpleDifficulty {
		ONE(1, 1), TWO(2, 1), THREE(3, 1), FOUR(2, 2), FIVE(4, 1), SIX(3, 2), SEVEN(4, 2), EIGTH(3, 3);
		private int level;
		private int deduction;
		SimpleDifficulty(int level, int deduction){
			this.level = level;
			this.deduction = deduction;			
		}
		public static SimpleDifficulty getEnum(int value){
			SimpleDifficulty[] simple = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGTH};
			if(value >= 1 && value <= simple.length)
				return simple[value-1];
			else
				return null;
		}
		public int getLevel() {
			return level;
		}
		public int getDeduction() {
			return deduction;
		}		
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
		LEVEL_DEDUCTION_1(1), LEVEL_DEDUCTION_2(2), LEVEL_DEDUCTION_3(3);
		private int value;		
		Deduction(int value){
			this.value = value;
		}
		public static Deduction getEnum(int value){
			return Arrays.stream(Deduction.values()).filter(f->f.getValue()==value).findFirst().orElse(LEVEL_DEDUCTION_1);
		}
		public int getValue() {
			return value;
		}
	}

	public Level getLevelAI() {
		return levelAI;
	}
	public Deduction getLevelDeduction() {
		return levelDeductionAI;
	}
	public boolean isDeductionLoop() {
		return deductionLoopAI;
	}
	public void setDeductionLoop(boolean deductionLoop) {
		this.deductionLoopAI = deductionLoop;
	}
}
