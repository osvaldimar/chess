package com.chess.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chess.core.client.PlayerMode;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypeColor;
import com.chess.core.enums.TypePiece;
import com.chess.core.enums.TypePlayer;
import com.chess.core.exception.CheckMoveException;
import com.chess.core.exception.CheckStateException;
import com.chess.core.exception.CheckmateException;
import com.chess.core.exception.Draw3PositionsException;
import com.chess.core.exception.Draw50MovementsException;
import com.chess.core.exception.DrawStalemateException;
import com.chess.core.memory.ChessboardMemory;
import com.chess.core.memory.PositionMemory;
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
	private PlayerMode player1;
	private PlayerMode player2;
	private List<Piece> listGraveyard;
	private Square lastSquarePiceMoved;
	private PositionChessboard positionPromotionPawn;
	
	private ChessboardMemory chessMemory;
	private int totalMovements;
	
	public Chessboard(PlayerMode player1, PlayerMode player2){
		this.player1 = player1;
		this.player2 = player2;
		this.listGraveyard = new ArrayList<>();
		this.chessMemory = new ChessboardMemory();
		startChessboad();
	}
	
	private void startChessboad(){
		int cor = 0;
		this.squares = new Square[8][8];
		PositionChessboard[] posicoes = PositionChessboard.values();
		for (PositionChessboard p : posicoes) {			
			Square square = buildSquare(cor == 0 ? TypeColor.BLACK : TypeColor.WHITE, p);
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
								n->n.addPiece(new Pawn(TypeColor.WHITE, player1.getTypePlayer())));
					Arrays.stream(p).filter(
						s -> s.getPosition().getNumber() == 6).forEach(
								n->n.addPiece(new Pawn(TypeColor.BLACK, player2.getTypePlayer())));
				});
		this.getSquareChessboard(PositionChessboard.A1).addPiece(new Rook(TypeColor.WHITE, player1.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.B1).addPiece(new Knight(TypeColor.WHITE, player1.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.C1).addPiece(new Bishop(TypeColor.WHITE, player1.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.D1).addPiece(new Queen(TypeColor.WHITE, player1.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.E1).addPiece(new King(TypeColor.WHITE, player1.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.F1).addPiece(new Bishop(TypeColor.WHITE, player1.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.G1).addPiece(new Knight(TypeColor.WHITE, player1.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.H1).addPiece(new Rook(TypeColor.WHITE, player1.getTypePlayer()));
		
		this.getSquareChessboard(PositionChessboard.A8).addPiece(new Rook(TypeColor.BLACK, player2.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.B8).addPiece(new Knight(TypeColor.BLACK, player2.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.C8).addPiece(new Bishop(TypeColor.BLACK, player2.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.D8).addPiece(new Queen(TypeColor.BLACK, player2.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.E8).addPiece(new King(TypeColor.BLACK, player2.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.F8).addPiece(new Bishop(TypeColor.BLACK, player2.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.G8).addPiece(new Knight(TypeColor.BLACK, player2.getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.H8).addPiece(new Rook(TypeColor.BLACK, player2.getTypePlayer()));		
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
	
	public void processValidateDraw(PlayerMode player) throws DrawStalemateException, Draw50MovementsException, Draw3PositionsException {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(squares);
		if(!CheckmateValidator.isKingInCheck(clone, player.getTypePlayer()))
			CheckmateValidator.processValidatesDraw(clone, player.getTypePlayer(), lastSquarePiceMoved, player1, player2, chessMemory);
	}
	
	public void processValidateCheckmate(PlayerMode player) throws CheckmateException, CheckStateException {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(squares);
		if(CheckmateValidator.isKingInCheck(clone, player.getTypePlayer()))
			CheckmateValidator.processValidatesCheckmate(clone, player.getTypePlayer(), lastSquarePiceMoved);
	}
	
	public void processPromotionOfPawn(PositionChessboard positionSelected, Piece piece) {
		this.positionPiece(positionSelected, piece);
		this.positionPromotionPawn = null;
	}
	
	public List<Piece> getPiecesEnemyDoCheck(PlayerMode player) {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(squares);
		return CheckmateValidator.getPiecesEnemyDoCheck(clone, player.getTypePlayer());
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
		if(piece.getTypePiece() == TypePiece.PAWN){
			Pawn pawn = (Pawn) piece;
			if(pawn.isPositionDestinyTakeElPassant(destiny)){
				PositionChessboard posPawnEnemyPassant = pawn.getSquarePassantToTakePawnEnemy().getPosition();
				clone[posPawnEnemyPassant.getLetter()][posPawnEnemyPassant.getNumber()].removePiece();	//remove pawn enemy taking by passant
			}
		}
		clone[destiny.getLetter()][destiny.getNumber()].addPiece(piece);
		clone[origin.getLetter()][origin.getNumber()].removePiece();

		//before simulation check
		if(isCheckBeforeSimulation){
			CheckmateValidator.validateKingExposedInCheckBeforeSimulation(clone, piece.getPlayer());	//you are in check
		}
		//after simulation check
		CheckmateValidator.validateKingExposedInCheckAfterSimulation(clone, piece.getPlayer());			//you can't expose king in check
		
		if(piece.getTypePiece() == TypePiece.KING) verifySpecialMovementCastling(origin, destiny, piece);
		if(piece.getTypePiece() == TypePiece.PAWN) verifySpecialMovementPassant(origin, destiny, piece);
		if(piece.getTypePiece() == TypePiece.PAWN) verifyPromotionPawn(destiny, piece);
		Piece gotten = walkPieceInTheChessboard(origin, destiny);
		getSquareChessboard(destiny).getPiece().incrementMovements();
				
		this.lastSquarePiceMoved = getSquareChessboard(destiny);		
		if(gotten != null) listGraveyard.add(gotten);
		
		//quantity movement of players
		if(piece.getTypePiece() == TypePiece.PAWN || gotten != null){
			player1.setQuantityMovement(0);
			player2.setQuantityMovement(0);
		}else{
			if(piece.getPlayer() == player1.getTypePlayer()) player1.incrementMovements();
			else player2.incrementMovements();
		}
		
		//memory queue
		this.chessMemory.addPositionQueue(new PositionMemory(origin, destiny, piece, gotten), piece.getPlayer());
		this.totalMovements++;
		return gotten;
	}
	
	private void verifyPromotionPawn(PositionChessboard destiny, Piece piece) {
		if( (piece.getPlayer() == TypePlayer.W && destiny.getNumber() == 7) 
				|| (piece.getPlayer() == TypePlayer.B && destiny.getNumber() == 0) ){
			positionPromotionPawn = destiny;
		}else{
			positionPromotionPawn = null;
		}
	}

	private void verifySpecialMovementPassant(PositionChessboard origin, PositionChessboard destiny, Piece piece) {
		Pawn pawn = (Pawn) piece;
		if(pawn.isPositionDestinyTakeElPassant(destiny)){
			//take pawn in passant and put on destiny that my pawn will go take it
			walkPieceInTheChessboard(pawn.getSquarePassantToTakePawnEnemy().getPosition(), destiny);
		}
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
	
	public PlayerMode getPlayerByType(String type){
		if(player1.getTypePlayer() == TypePlayer.getEnum(type)){
			return player1;
		}else if(player2.getTypePlayer() == TypePlayer.getEnum(type)){
			return player2;
		}else{
			return null;
		}		
	}
	
	public void printLayoutChessboard() {
		System.out.print(getLayoutChessboard());
	}
	
	public String getLayoutChessboard(){
		StringBuilder builder = new StringBuilder("\n");
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				builder.append(this.squares[x][y]);
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public void printDebugChessboard(Chessboard board, String message){
		System.out.println("\n*** layout chess *** - " + message);
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				System.out.print(this.squares[x][y]);
			}
			System.out.println();
		}
	}
	
	public static void printCloneDebugChessboard(Square[][] clone, String message){
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

	public PlayerMode getPlayer1() {
		return player1;
	}

	public void setPlayer1(PlayerMode player1) {
		this.player1 = player1;
	}

	public PlayerMode getPlayer2() {
		return player2;
	}

	public void setPlayer2(PlayerMode player2) {
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
	public PositionChessboard getPositionPromotionPawn() {
		return positionPromotionPawn;
	}
	public ChessboardMemory getChessMemory() {
		return chessMemory;
	}
	public int getTotalMovements() {
		return totalMovements;
	}
}
