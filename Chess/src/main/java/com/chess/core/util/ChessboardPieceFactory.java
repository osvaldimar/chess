package com.chess.core.util;

import java.util.HashMap;
import java.util.Map;

import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Bishop;
import com.chess.core.model.King;
import com.chess.core.model.Knight;
import com.chess.core.model.Pawn;
import com.chess.core.model.Piece;
import com.chess.core.model.Player;
import com.chess.core.model.Queen;
import com.chess.core.model.Rook;
import com.chess.core.model.Square;

public final class ChessboardPieceFactory {
	
	public static Piece buildPieceByType(TypePiece type, Player player){
		if(type != null){
			TypeColor color = player.getTypePlayer() == TypePlayer.W ? TypeColor.WHITE : TypeColor.BLACK;
			Map<TypePiece, Piece> map = new HashMap<>();
			map.put(TypePiece.QUEEN, new Queen(color, player));
			map.put(TypePiece.ROOK, new Queen(color, player));
			map.put(TypePiece.KNIGHT, new Queen(color, player));
			map.put(TypePiece.BISHOP, new Queen(color, player));
			return map.get(type);
		}
		return null;
	}
	
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
