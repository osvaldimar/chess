package com.chess.core.model;

import java.util.ArrayList;
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
	private List<Piece> listGraveyard;
	
	public Chessboard(TypeColor blackSquares, TypeColor whiteSquares, 
			TypeColor blackPieces, TypeColor whitePieces, Player player1, Player player2){
		this.blackSquares = blackSquares;
		this.whiteSquares = whiteSquares;
		this.blackPieces = blackPieces;
		this.whitePieces = whitePieces;
		this.player1 = player1;
		this.player2 = player2;
		this.listGraveyard = new ArrayList<>();
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
								n->n.addPiece(new Pawn(whitePieces, player1)));
					Arrays.stream(p).filter(
						s -> s.getPosition().getNumber() == 6).forEach(
								n->n.addPiece(new Pawn(blackPieces, player2)));
				});
		this.squaresChessboard(PositionChessboard.A1).addPiece(new Rook(whitePieces, player1));
		this.squaresChessboard(PositionChessboard.B1).addPiece(new Knight(whitePieces, player1));
		this.squaresChessboard(PositionChessboard.C1).addPiece(new Bishop(whitePieces, player1));
		this.squaresChessboard(PositionChessboard.D1).addPiece(new Queen(whitePieces, player1));
		this.squaresChessboard(PositionChessboard.E1).addPiece(new King(whitePieces, player1));
		this.squaresChessboard(PositionChessboard.F1).addPiece(new Bishop(whitePieces, player1));
		this.squaresChessboard(PositionChessboard.G1).addPiece(new Knight(whitePieces, player1));
		this.squaresChessboard(PositionChessboard.H1).addPiece(new Rook(whitePieces, player1));
		
		this.squaresChessboard(PositionChessboard.A8).addPiece(new Rook(blackPieces, player2));
		this.squaresChessboard(PositionChessboard.B8).addPiece(new Knight(blackPieces, player2));
		this.squaresChessboard(PositionChessboard.C8).addPiece(new Bishop(blackPieces, player2));
		this.squaresChessboard(PositionChessboard.D8).addPiece(new Queen(blackPieces, player2));
		this.squaresChessboard(PositionChessboard.E8).addPiece(new King(blackPieces, player2));
		this.squaresChessboard(PositionChessboard.F8).addPiece(new Bishop(blackPieces, player2));
		this.squaresChessboard(PositionChessboard.G8).addPiece(new Knight(blackPieces, player2));
		this.squaresChessboard(PositionChessboard.H8).addPiece(new Rook(blackPieces, player2));
		
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
	
	public Piece movePieceIntTheChessboard(PositionChessboard origin, PositionChessboard destiny, Piece piece) {
		Piece gotten = this.squares[destiny.getLetter()][destiny.getNumber()].getPiece();
		this.squares[destiny.getLetter()][destiny.getNumber()].addPiece(piece);
		this.squares[origin.getLetter()][origin.getNumber()].removePiece();
		if(gotten != null) listGraveyard.add(gotten);
		return gotten;
	}
	
	public void printChessboard(Chessboard board, String message){
		System.out.println("\n*** layout chess *** - " + message);
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				System.out.print(this.squares[x][y]);
			}
			System.out.println();
		}
	}

	@Override
	public String toString() {
		String str = "";
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				str += this.squares[x][y];
			}
			str += "\n";
		}
		return str;
	}
	
	public TypeColor getWhiteSquares() {
		return whiteSquares;
	}

	public void setWhiteSquares(TypeColor whiteSquares) {
		this.whiteSquares = whiteSquares;
	}

	public TypeColor getBlackSquares() {
		return blackSquares;
	}

	public void setBlackSquares(TypeColor blackSquares) {
		this.blackSquares = blackSquares;
	}

	public TypeColor getBlackPieces() {
		return blackPieces;
	}

	public void setBlackPieces(TypeColor blackPieces) {
		this.blackPieces = blackPieces;
	}

	public TypeColor getWhitePieces() {
		return whitePieces;
	}

	public void setWhitePieces(TypeColor whitePieces) {
		this.whitePieces = whitePieces;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public List<Piece> getListGraveyard() {
		return listGraveyard;
	}

	public void setListGraveyard(List<Piece> listGraveyard) {
		this.listGraveyard = listGraveyard;
	}
	
	
}
