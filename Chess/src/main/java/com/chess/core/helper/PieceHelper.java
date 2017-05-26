package com.chess.core.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Piece;
import com.chess.core.model.Square;

public class PieceHelper {
	
	public static List<Piece> getListPiecesByPlayer(Square[][] squares, TypePlayer type){		
		List<Piece> listPieces = new ArrayList<>();
		for(Square[] square : squares){
			listPieces.addAll(Arrays.stream(square).filter(s -> !s.isAvailable())
				.filter(s -> s.getPiece().getPlayer().equals(type))
				.map(m -> m.getPiece()).collect(Collectors.toList()));
		}		
		return listPieces;
	}
	
	public static Double getTotalScoreChessboardPiecesByPlayer(Square[][] squares, TypePlayer type){
		List<Piece> listPieces = getListPiecesByPlayer(squares, type);
		List<Piece> listPiecesOpponent = getListPiecesByPlayer(squares, negateTypePlayer(type));
		double sumOnChessboard = listPieces.stream().mapToDouble(m -> m.getTypePiece().getValue()).sum();
		double sumOnGraveyard = getTotalScorePiecesGameStart() - 
				listPiecesOpponent.stream().mapToDouble(m -> m.getTypePiece().getValue()).sum();
		return sumOnChessboard + sumOnGraveyard;
	}
	
	/*public static Double getTotalScoreChessboardPiecesByPlayer(List<Piece> listPieces){
		return listPieces.stream().mapToDouble(m -> m.getTypePiece().getValue()).sum();
	}
	
	public static Double getTotalScoreChessboardPiecesGraveyardByPlayer(List<Piece> listPieces, TypePlayer type){
		return listPieces.stream().filter(p -> p.getPlayer().equals(type)).mapToDouble(m -> m.getTypePiece().getValue()).sum();
	}*/
	
	public static Double getTotalScorePiecesGameStart(){
		return (TypePiece.PAWN.getValue() * 8) + (TypePiece.ROOK.getValue() * 2) +
				(TypePiece.KNIGHT.getValue() * 2) + (TypePiece.BISHOP.getValue() * 2) +
				(TypePiece.QUEEN.getValue()) + (TypePiece.KING.getValue());
	}
	
	public static TypePlayer negateTypePlayer(TypePlayer type){
		return TypePlayer.W == type ? TypePlayer.B : TypePlayer.W;
	}
	
}
