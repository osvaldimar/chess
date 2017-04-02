package com.chess.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.exception.CheckMoveException;
import com.chess.core.exception.CheckStateException;
import com.chess.core.exception.CheckmateException;
import com.chess.core.model.Bishop;
import com.chess.core.model.King;
import com.chess.core.model.Knight;
import com.chess.core.model.Pawn;
import com.chess.core.model.Piece;
import com.chess.core.model.Player;
import com.chess.core.model.Queen;
import com.chess.core.model.Rook;
import com.chess.core.model.Square;
import com.chess.core.util.ChessboardPieceFactory;
import com.chess.core.util.CheckmateValidator;

public class Chessboard {

	private Square[][] squares;
	private TypeColor whiteSquares;
	private TypeColor blackSquares;
	private TypeColor blackPieces;
	private TypeColor whitePieces;
	private Player player1;
	private Player player2;
	private List<Piece> listGraveyard;
	private Square lastSquarePiceMoved;
	
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
			Square square = buildSquare(cor == 0 ? this.blackSquares : this.whiteSquares, p);
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
		this.getSquareChessboard(PositionChessboard.A1).addPiece(new Rook(whitePieces, player1));
		this.getSquareChessboard(PositionChessboard.B1).addPiece(new Knight(whitePieces, player1));
		this.getSquareChessboard(PositionChessboard.C1).addPiece(new Bishop(whitePieces, player1));
		this.getSquareChessboard(PositionChessboard.D1).addPiece(new Queen(whitePieces, player1));
		this.getSquareChessboard(PositionChessboard.E1).addPiece(new King(whitePieces, player1));
		this.getSquareChessboard(PositionChessboard.F1).addPiece(new Bishop(whitePieces, player1));
		this.getSquareChessboard(PositionChessboard.G1).addPiece(new Knight(whitePieces, player1));
		this.getSquareChessboard(PositionChessboard.H1).addPiece(new Rook(whitePieces, player1));
		
		this.getSquareChessboard(PositionChessboard.A8).addPiece(new Rook(blackPieces, player2));
		this.getSquareChessboard(PositionChessboard.B8).addPiece(new Knight(blackPieces, player2));
		this.getSquareChessboard(PositionChessboard.C8).addPiece(new Bishop(blackPieces, player2));
		this.getSquareChessboard(PositionChessboard.D8).addPiece(new Queen(blackPieces, player2));
		this.getSquareChessboard(PositionChessboard.E8).addPiece(new King(blackPieces, player2));
		this.getSquareChessboard(PositionChessboard.F8).addPiece(new Bishop(blackPieces, player2));
		this.getSquareChessboard(PositionChessboard.G8).addPiece(new Knight(blackPieces, player2));
		this.getSquareChessboard(PositionChessboard.H8).addPiece(new Rook(blackPieces, player2));		
	}
	
	private static Square buildSquare(TypeColor color, PositionChessboard position){
		return new Square(color, position);
	}
	
	public Square[][] getCloneSquaresChessboard(){
		return ChessboardPieceFactory.buildCloneSquares(squares);
	}
	
	public Square[][] getSquaresChessboard() {
		final Square[][] squares = this.squares.clone();
		return squares;
	}
	
	public Square getSquareChessboard(PositionChessboard position) {
		return squares[position.getLetter()][position.getNumber()];
	}
	
	public void positionPiece(PositionChessboard pos, Piece piece) {
		this.squares[pos.getLetter()][pos.getNumber()].addPiece(piece);
	}
	
	public void processValidateCheckmate(Player player) throws CheckmateException, CheckStateException {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(squares);
		if(CheckmateValidator.isKingInCheck(clone, player))
			CheckmateValidator.processValidatesCheckmate(clone, player);
	}
	
	public List<Piece> getPiecesEnemyDoCheck(Player player) {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(squares);
		return CheckmateValidator.getPiecesEnemyDoCheck(clone, player);
	}

	public Piece walkPieceInTheChessboard(PositionChessboard origin, PositionChessboard destiny){
		Piece gotten = getSquareChessboard(destiny).getPiece();		
		getSquareChessboard(destiny).addPiece(getSquareChessboard(origin).getPiece());
		getSquareChessboard(origin).removePiece();
		return gotten;
	}
	
	public Piece movePieceInTheChessboard(PositionChessboard origin, PositionChessboard destiny, Piece piece) throws CheckMoveException, CheckStateException {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(squares);	
		
		boolean isCheckBeforeSimulation = CheckmateValidator.isKingInCheck(clone, piece.getPlayer());		
		clone[destiny.getLetter()][destiny.getNumber()].addPiece(piece);
		clone[origin.getLetter()][origin.getNumber()].removePiece();

		//before simulation check
		if(isCheckBeforeSimulation){
			CheckmateValidator.validateKingExposedInCheckBeforeSimulation(clone, piece.getPlayer());
		}
		//after simulation check
		CheckmateValidator.validateKingExposedInCheckAfterSimulation(clone, piece.getPlayer());
		
		if(piece.getTypePiece() == TypePiece.KING) verifySpecialMovementCastling(origin, destiny, piece);		
		Piece gotten = walkPieceInTheChessboard(origin, destiny);
		getSquareChessboard(destiny).getPiece().incrementMovements();
		
		this.lastSquarePiceMoved = getSquareChessboard(destiny);		
		if(gotten != null) listGraveyard.add(gotten);
		return gotten;
	}
	
	private void verifySpecialMovementCastling(PositionChessboard origin, PositionChessboard destiny, Piece piece){
		//moves rook if king is doing castling
		if(origin == PositionChessboard.E1 && destiny == PositionChessboard.C1){
			walkPieceInTheChessboard(PositionChessboard.A1, PositionChessboard.D1);
		}else if(origin == PositionChessboard.E1 && destiny == PositionChessboard.G1){
			walkPieceInTheChessboard(PositionChessboard.H1, PositionChessboard.F1);
		}else if(origin == PositionChessboard.E8 && destiny == PositionChessboard.C8){
			walkPieceInTheChessboard(PositionChessboard.A8, PositionChessboard.D8);
		}else if(origin == PositionChessboard.E8 && destiny == PositionChessboard.G8){
			walkPieceInTheChessboard(PositionChessboard.H8, PositionChessboard.F8);
		}
	}
	
	public void printChessboard(Chessboard board, String message){
		System.out.println("\n*** layout chess *** - " + message);
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				System.out.print(this.squares[x][y]);
			}
			System.out.println("\n");
		}
	}
	
	public static void printCloneChessboard(Square[][] clone, String message){
		System.out.println("\n*** Clone layout chess *** - " + message);
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				System.out.print(clone[x][y]);
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
	public Square getLastSquarePiceMoved() {
		return lastSquarePiceMoved;
	}
	
}
