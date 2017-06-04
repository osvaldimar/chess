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
import com.chess.core.helper.PieceHelper;
import com.chess.core.memory.ChessboardMemory;
import com.chess.core.memory.PositionMemory;
import com.chess.core.model.Bishop;
import com.chess.core.model.King;
import com.chess.core.model.Knight;
import com.chess.core.model.Pawn;
import com.chess.core.model.Piece;
import com.chess.core.model.Queen;
import com.chess.core.model.Rook;
import com.chess.core.model.Square;
import com.chess.core.model.LastMovement;
import com.chess.core.util.CheckmateValidator;
import com.chess.core.util.ChessboardPieceFactory;

public class Chessboard {

	private ChessboardModel model = new ChessboardModel();

	public Chessboard(PlayerMode player1, PlayerMode player2){
		this.model.setPlayer1(player1);
		this.model.setPlayer2(player2);
		this.model.setListGraveyard(new ArrayList<>());
		this.model.setChessMemory(new ChessboardMemory());
		startChessboad();
	}
	
	private void startChessboad(){
		int cor = 0;
		this.model.setSquares(new Square[8][8]);
		PositionChessboard[] posicoes = PositionChessboard.values();
		for (PositionChessboard p : posicoes) {			
			Square square = buildSquare(cor == 0 ? TypeColor.BLACK : TypeColor.WHITE, p);
			this.model.getSquares()[p.getLetter()][p.getNumber()] = square;
			cor = cor == 0 ? 1 : 0;
		}		
	}
	
	public void startGame(){
		this.startChessboad();
		Arrays.stream(this.model.getSquares()).forEach(
				p -> {
					Arrays.stream(p).filter(
						s -> s.getPosition().getNumber() == 1).forEach(
								n->n.addPiece(new Pawn(TypeColor.WHITE, model.getPlayer1().getTypePlayer())));
					Arrays.stream(p).filter(
						s -> s.getPosition().getNumber() == 6).forEach(
								n->n.addPiece(new Pawn(TypeColor.BLACK, model.getPlayer2().getTypePlayer())));
				});
		this.getSquareChessboard(PositionChessboard.A1).addPiece(new Rook(TypeColor.WHITE, model.getPlayer1().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.B1).addPiece(new Knight(TypeColor.WHITE, model.getPlayer1().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.C1).addPiece(new Bishop(TypeColor.WHITE, model.getPlayer1().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.D1).addPiece(new Queen(TypeColor.WHITE, model.getPlayer1().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.E1).addPiece(new King(TypeColor.WHITE, model.getPlayer1().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.F1).addPiece(new Bishop(TypeColor.WHITE, model.getPlayer1().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.G1).addPiece(new Knight(TypeColor.WHITE, model.getPlayer1().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.H1).addPiece(new Rook(TypeColor.WHITE, model.getPlayer1().getTypePlayer()));
		
		this.getSquareChessboard(PositionChessboard.A8).addPiece(new Rook(TypeColor.BLACK, model.getPlayer2().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.B8).addPiece(new Knight(TypeColor.BLACK, model.getPlayer2().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.C8).addPiece(new Bishop(TypeColor.BLACK, model.getPlayer2().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.D8).addPiece(new Queen(TypeColor.BLACK, model.getPlayer2().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.E8).addPiece(new King(TypeColor.BLACK, model.getPlayer2().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.F8).addPiece(new Bishop(TypeColor.BLACK, model.getPlayer2().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.G8).addPiece(new Knight(TypeColor.BLACK, model.getPlayer2().getTypePlayer()));
		this.getSquareChessboard(PositionChessboard.H8).addPiece(new Rook(TypeColor.BLACK, model.getPlayer2().getTypePlayer()));		
	}
	
	private static Square buildSquare(TypeColor color, PositionChessboard position){
		return new Square(color, position);
	}
	
	public Square[][] getCloneSquaresChessboard(){
		return ChessboardPieceFactory.buildCloneSquares(model.getSquares());
	}
	
	public Square[][] getSquaresChessboard() {
		return model.getSquares();
	}
	
	public void setSquares(Square[][] squares) {
		this.model.setSquares(squares);
	}
	
	public Square getSquareChessboard(PositionChessboard position) {
		return model.getSquares()[position.getLetter()][position.getNumber()];
	}
	
	public void positionPiece(PositionChessboard pos, Piece piece) {
		this.model.getSquares()[pos.getLetter()][pos.getNumber()].addPiece(piece);
	}
	
	public void processValidateDraw(PlayerMode player) throws DrawStalemateException, Draw50MovementsException, Draw3PositionsException {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(model.getSquares());
		if(!CheckmateValidator.isKingInCheck(clone, player.getTypePlayer()))
			CheckmateValidator.processValidatesDraw(clone, player.getTypePlayer(), model.getLastSquarePiceMoved(), model.getPlayer1(), model.getPlayer2(), model.getChessMemory());
	}
	
	public void processValidateCheckmate(PlayerMode player) throws CheckmateException, CheckStateException {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(model.getSquares());
		if(CheckmateValidator.isKingInCheck(clone, player.getTypePlayer()))
			CheckmateValidator.processValidatesCheckmate(clone, player.getTypePlayer(), model.getLastSquarePiceMoved());
	}
	
	public void processPromotionOfPawn(PositionChessboard positionSelected, Piece piece) {
		this.positionPiece(positionSelected, piece);
		this.model.setPositionPromotionPawn(null);
	}
	
	public List<Piece> getPiecesEnemyDoCheck(PlayerMode player) {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(model.getSquares());
		return CheckmateValidator.getPiecesEnemyDoCheck(clone, player.getTypePlayer());
	}

	public Piece walkPieceInTheChessboard(PositionChessboard origin, PositionChessboard destiny){
		Piece gotten = getSquareChessboard(destiny).getPiece();		
		getSquareChessboard(destiny).addPiece(getSquareChessboard(origin).getPiece());
		getSquareChessboard(origin).removePiece();
		return gotten;
	}
	
	public Piece movePieceInTheChessboard(PositionChessboard origin, PositionChessboard destiny, Piece piece) throws CheckMoveException, CheckStateException {
		Square[][] clone = ChessboardPieceFactory.buildCloneSquares(model.getSquares());		
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
		
		this.model.setLastMovement(new LastMovement());
		if(piece.getTypePiece() == TypePiece.KING) verifySpecialMovementCastling(origin, destiny, piece);
		if(piece.getTypePiece() == TypePiece.PAWN) verifySpecialMovementPassant(origin, destiny, piece);
		if(piece.getTypePiece() == TypePiece.PAWN) verifyPromotionPawn(destiny, piece);
		Piece gotten = walkPieceInTheChessboard(origin, destiny);
		getSquareChessboard(destiny).getPiece().incrementMovements();
				
		this.model.setLastSquarePiceMoved(getSquareChessboard(destiny));		
		if(gotten != null) model.getListGraveyard().add(gotten);
		
		//quantity movement of players
		if(piece.getTypePiece() == TypePiece.PAWN || gotten != null){
			model.getPlayer1().setQuantityMovement(0);
			model.getPlayer2().setQuantityMovement(0);
		}else{
			if(piece.getPlayer() == model.getPlayer1().getTypePlayer()) model.getPlayer1().incrementMovements();
			else model.getPlayer2().incrementMovements();
		}
		
		//info movements
		this.model.setTotalMovements(this.model.getTotalMovements() + 1);
		this.model.getLastMovement().setIdMovement(model.getTotalMovements());
		this.model.getLastMovement().setMovedFrom(origin);
		this.model.getLastMovement().setMovedTo(destiny);
		if(this.model.getLastMovement().getDestroyed() == null) {
			if(gotten != null) this.model.getLastMovement().setDestroyed(destiny);
		}
		if(this.model.getLastMovement().getName() == null) {
			if(gotten == null){
				this.model.getLastMovement().setName(LastMovement.NameMovement.MOVED);
			}else{
				this.model.getLastMovement().setName(LastMovement.NameMovement.TAKEN);
			}
		}
		this.model.getChessMemory().addPositionQueue(new PositionMemory(origin, destiny, piece, gotten), piece.getPlayer());
		return gotten;
	}
	
	private void verifyPromotionPawn(PositionChessboard destiny, Piece piece) {
		if( (piece.getPlayer() == TypePlayer.W && destiny.getNumber() == 7) 
				|| (piece.getPlayer() == TypePlayer.B && destiny.getNumber() == 0) ){
			model.setPositionPromotionPawn(destiny);
		}else{
			model.setPositionPromotionPawn(null);
		}
	}

	private void verifySpecialMovementPassant(PositionChessboard origin, PositionChessboard destiny, Piece piece) {
		Pawn pawn = (Pawn) piece;
		if(pawn.isPositionDestinyTakeElPassant(destiny)){
			//take pawn in passant and put on destiny that my pawn will go take it
			walkPieceInTheChessboard(pawn.getSquarePassantToTakePawnEnemy().getPosition(), destiny);
			this.model.getLastMovement().setDestroyed(pawn.getSquarePassantToTakePawnEnemy().getPosition());
			this.model.getLastMovement().setName(LastMovement.NameMovement.PASSANT);
		}
	}

	private void verifySpecialMovementCastling(PositionChessboard origin, PositionChessboard destiny, Piece piece){
		//moves rook if king is doing castling
		if(origin == PositionChessboard.E1 && destiny == PositionChessboard.C1){
			walkPieceInTheChessboard(PositionChessboard.A1, PositionChessboard.D1);
			this.model.getLastMovement().setCastlingFrom( PositionChessboard.A1);
			this.model.getLastMovement().setCastlingTo( PositionChessboard.D1);
		}else if(origin == PositionChessboard.E1 && destiny == PositionChessboard.G1){
			walkPieceInTheChessboard(PositionChessboard.H1, PositionChessboard.F1);
			this.model.getLastMovement().setCastlingFrom( PositionChessboard.H1);
			this.model.getLastMovement().setCastlingTo( PositionChessboard.F1);
		}else if(origin == PositionChessboard.E8 && destiny == PositionChessboard.C8){
			walkPieceInTheChessboard(PositionChessboard.A8, PositionChessboard.D8);
			this.model.getLastMovement().setCastlingFrom( PositionChessboard.A8);
			this.model.getLastMovement().setCastlingTo( PositionChessboard.D8);
		}else if(origin == PositionChessboard.E8 && destiny == PositionChessboard.G8){
			walkPieceInTheChessboard(PositionChessboard.H8, PositionChessboard.F8);
			this.model.getLastMovement().setCastlingFrom( PositionChessboard.H8);
			this.model.getLastMovement().setCastlingTo( PositionChessboard.F8);
		}
		if(this.model.getLastMovement().getCastlingFrom() != null) 
			this.model.getLastMovement().setName(LastMovement.NameMovement.CASTLING);
	}
	
	public PlayerMode getPlayerByType(String type){
		if(model.getPlayer1().getTypePlayer() == TypePlayer.getEnum(type)){
			return model.getPlayer1();
		}else if(model.getPlayer2().getTypePlayer() == TypePlayer.getEnum(type)){
			return model.getPlayer2();
		}else{
			return null;
		}		
	}
	
	public void removePiece(List<PositionChessboard> listPositions){
		listPositions.forEach(p -> getSquareChessboard(p).removePiece());
	}
	
	public void addPiece(PositionChessboard pos, Piece piece) {
		this.getSquareChessboard(pos).addPiece(piece);
	}
	
	public void printLayoutChessboard() {
		System.out.print(getLayoutChessboard());
	}
	
	public String getLayoutChessboard(){
		StringBuilder builder = new StringBuilder("\n");
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				builder.append(this.model.getSquares()[x][y]);
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public void printDebugChessboard(Chessboard board, String message){
		System.out.println("\n*** layout chess *** - " + message);
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				System.out.print(this.model.getSquares()[x][y]);
			}
			System.out.println();
		}
		resultScoreChessboardPlayers(this.model.getSquares());
	}
	
	public static void printCloneDebugChessboard(Square[][] clone, String message){
		System.out.println("\n*** Clone layout chess *** - " + message);
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				System.out.print(clone[x][y]);
			}
			System.out.println();
		}
		resultScoreChessboardPlayers(clone);
	}

	private static void resultScoreChessboardPlayers(Square[][] squares) {
		Double w = PieceHelper.getTotalScoreChessboardPiecesByPlayer(squares, TypePlayer.W);
		Double b = PieceHelper.getTotalScoreChessboardPiecesByPlayer(squares, TypePlayer.B);
		System.out.println("Result Score - W: " + w + " - B: " + b);
	}

	@Override
	public String toString() {
		String str = "";
		for(int y = 7; y >= 0; y--){
			for(int x = 0; x <= 7; x++){
				str += this.model.getSquares()[x][y];
			}
			str += "\n";
		}
		return str;
	}

	public PlayerMode getPlayer1() {
		return model.getPlayer1();
	}

	public void setPlayer1(PlayerMode player1) {
		this.model.setPlayer1(player1);
	}

	public PlayerMode getPlayer2() {
		return model.getPlayer2();
	}

	public void setPlayer2(PlayerMode player2) {
		this.model.setPlayer2(player2);
	}

	public List<Piece> getListGraveyard() {
		return model.getListGraveyard();
	}

	public void setListGraveyard(List<Piece> listGraveyard) {
		this.model.setListGraveyard(listGraveyard);
	}
	public Square getLastSquarePiceMoved() {
		return model.getLastSquarePiceMoved();
	}
	public PositionChessboard getPositionPromotionPawn() {
		return model.getPositionPromotionPawn();
	}
	public ChessboardMemory getChessMemory() {
		return model.getChessMemory();
	}
	public int getTotalMovements() {
		return model.getTotalMovements();
	}
	public LastMovement getLastMovement() {
		return model.getLastMovement();
	}

}
