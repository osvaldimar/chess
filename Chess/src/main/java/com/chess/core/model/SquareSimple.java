package com.chess.core.model;

import java.io.Serializable;

public class SquareSimple implements Serializable {

	private static final long serialVersionUID = -2977076638220889762L;

	private String peca;
	private String cor;
	private String pos;

	public SquareSimple(String namePiece, String color, String position) {
		this.peca = namePiece;
		this.cor = color != null ? color.substring(0, 1) : color;
		this.pos = position;
	}
	public SquareSimple(String position) {
		this.pos = position;
	}

	public String getPeca() {
		return peca;
	}
	public String getCor() {
		return cor;
	}
	public String getPos() {
		return pos;
	}
	
	@Override
	public String toString() {
		String str = "[" + this.pos;
		if (this.peca != null) {
			str += " - " + this.peca + " - " + this.cor;
		}
		str += "]";
		return str;
	}
}
