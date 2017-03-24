package com.chess.core.model;

import java.util.Arrays;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;

public class Chessboard {

	private Square[][] squares;
	private TypeColor whiteSquares;
	private TypeColor blackSquares;
	private TypeColor blackPieces;
	private TypeColor whitePieces;
	private Player player1;
	private Player player2;
	
	public Chessboard(TypeColor blackSquares, TypeColor whiteSquares, 
			TypeColor blackPieces, TypeColor whitePieces, Player player1, Player player2){
		this.blackSquares = blackSquares;
		this.whiteSquares = whiteSquares;
		this.blackPieces = blackPieces;
		this.whitePieces = whitePieces;
		this.player1 = player1;
		this.player2 = player2;
		startChessboad();
	}
	
	public void startChessboad(){
		int cor = 0;
		this.squares = new Square[8][8];
		PositionChessboard[] posicoes = PositionChessboard.values();
		for (PositionChessboard p : posicoes) {			
			Square square = square(cor == 0 ? this.blackSquares : this.whiteSquares, p);
			this.squares[p.getLetter()][p.getNumber()] = square;
			cor = cor == 0 ? 1 : 0;
		}		
	}
	
	public void startGame(){
		this.startChessboad();
		Arrays.stream(this.squares).forEach(
				p -> {
					Arrays.stream(p).filter(
						s -> s.getPosition().getNumber() == 1).forEach(
								n->n.addPiece(new Pawn(TypePiece.PEAO, whitePieces, player1)));
					Arrays.stream(p).filter(
						s -> s.getPosition().getNumber() == 6).forEach(
								n->n.addPiece(new Pawn(TypePiece.PEAO, blackPieces, player2)));
				});
		
	}
	
	public Square[][] squaresChessboard() {
		final Square[][] squares = this.squares.clone();
		return squares;
	}
	
	public Square squaresChessboard(PositionChessboard position) {
		return squares[position.getLetter()][position.getNumber()];
	}

	private static Square square(TypeColor color, PositionChessboard position){
		return new Square(color, position);
	}
	
	public void positionPiece(PositionChessboard pos, Piece piece) {
		this.squares[pos.getLetter()][pos.getNumber()].addPiece(piece);
	}
	
	public void positionPiece(PositionChessboard newPosition, Piece piece, List<PositionChessboard> listPositions){
		
	}
	
	public void printChessboard(Chessboard board, String message){
		System.out.println("\n*** layout chess *** - " + message);
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				System.out.print(this.squares[x][y]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
