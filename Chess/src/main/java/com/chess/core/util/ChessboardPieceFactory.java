package com.chess.core.util;

import com.chess.core.model.Bishop;
import com.chess.core.model.King;
import com.chess.core.model.Knight;
import com.chess.core.model.Pawn;
import com.chess.core.model.Piece;
import com.chess.core.model.Queen;
import com.chess.core.model.Rook;
import com.chess.core.model.Square;

public final class ChessboardPieceFactory {
	
	public static Piece buildClonePiece(Piece p){
		if(p == null)
			return null;
		Piece clone = null;
		switch (p.getTypePiece()) {
		case PAWN: clone = new Pawn(p.getColor(), p.getPlayer());
			break;
		case ROOK: clone = new Rook(p.getColor(), p.getPlayer());		
			break;
		case KNIGHT: clone = new Knight(p.getColor(), p.getPlayer());			
			break;
		case BISHOP: clone = new Bishop(p.getColor(), p.getPlayer());			
			break;
		case QUEEN: clone = new Queen(p.getColor(), p.getPlayer());			
			break;
		case KING: clone = new King(p.getColor(), p.getPlayer());			
			break;
		default:
			break;
		}
		return clone;
	}
	
	public static Square[][] buildCloneSquares(Square[][] squares){
		Square[][] sq = new Square[squares.length][squares.length];
		for(int i = 0; i < sq.length; i++){
			for(int j = 0; j < sq.length; j++){
				sq[i][j] = buildCloneSquare(squares[i][j]);
			}
		}		
		return sq;
	}

	private static Square buildCloneSquare(Square square) {
		return new Square(buildClonePiece(square.getPiece()), 
				square.getColor(), square.getPosition());
	}
	
}
