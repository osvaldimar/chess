package com.chess.core.model;

import java.io.Serializable;
import java.util.Arrays;

public class Difficulty implements Serializable {

	private static final long serialVersionUID = 8983849443846764927L;

	private Level levelAI;
	private Deduction levelDeductionAI;
	private boolean deductionLoopAI;

	public Difficulty() {
	}

	public Difficulty(final Level levelAI, final Deduction levelDeduction) {
		this.levelAI = levelAI;
		this.levelDeductionAI = levelDeduction;
	}

	public static Difficulty createSimpleDifficulty(final SimpleDifficulty levelDifficulty) {
		return new Difficulty(Level.getEnum(levelDifficulty.getLevel()),
				Deduction.getEnum(levelDifficulty.getDeduction()));
	}

	public enum SimpleDifficulty {
		ONE(1, 1), TWO(2, 1), THREE(3, 1), FOUR(2, 2), FIVE(4, 1), SIX(3, 2), SEVEN(4, 2), EIGTH(3, 3);
		private int level;
		private int deduction;

		SimpleDifficulty(final int level, final int deduction) {
			this.level = level;
			this.deduction = deduction;
		}

		public static SimpleDifficulty getEnum(final int value) {
			final SimpleDifficulty[] simple = { ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGTH };
			if (value >= 1 && value <= simple.length) {
				return simple[value - 1];
			} else {
				return null;
			}
		}

		public int getLevel() {
			return this.level;
		}

		public int getDeduction() {
			return this.deduction;
		}
	}

	public enum Level {
		LEVEL_AI_1(1), LEVEL_AI_2(2), LEVEL_AI_3(3), LEVEL_AI_4(4);
		private int value;

		Level(final int value) {
			this.value = value;
		}

		public static Level getEnum(final int value) {
			return Arrays.stream(Level.values()).filter(f -> f.getValue() == value).findFirst().orElse(LEVEL_AI_1);
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum Deduction {
		LEVEL_DEDUCTION_1(1), LEVEL_DEDUCTION_2(2), LEVEL_DEDUCTION_3(3);
		private int value;

		Deduction(final int value) {
			this.value = value;
		}

		public static Deduction getEnum(final int value) {
			return Arrays.stream(Deduction.values()).filter(f -> f.getValue() == value).findFirst()
					.orElse(LEVEL_DEDUCTION_1);
		}

		public int getValue() {
			return this.value;
		}
	}

	public Level getLevelAI() {
		return this.levelAI;
	}

	public void setLevelAI(final Level levelAI) {
		this.levelAI = levelAI;
	}

	public Deduction getLevelDeductionAI() {
		return this.levelDeductionAI;
	}

	public void setLevelDeductionAI(final Deduction levelDeductionAI) {
		this.levelDeductionAI = levelDeductionAI;
	}

	public boolean isDeductionLoopAI() {
		return this.deductionLoopAI;
	}

	public void setDeductionLoopAI(final boolean deductionLoopAI) {
		this.deductionLoopAI = deductionLoopAI;
	}

}
