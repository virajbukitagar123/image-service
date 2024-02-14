package com.chess.imageservice.board;

import com.chess.imageservice.board.pieces.Bishop;
import com.chess.imageservice.board.pieces.King;
import com.chess.imageservice.board.pieces.Knight;
import com.chess.imageservice.board.pieces.Pawn;
import com.chess.imageservice.board.pieces.Queen;
import com.chess.imageservice.board.pieces.Rook;

public class BoardBuilder {
    
    public static Square blackRook() {
        return Square.of(new Rook(Color.BLACK));
    }
    
    public static Square whiteRook() {
        return Square.of(new Rook(Color.WHITE));
    }
    
    public static Square blackQueen() {
        return Square.of(new Queen(Color.BLACK));
    }

    public static Square whiteQueen() {
        return Square.of(new Queen(Color.WHITE));
    }
    
    public static Square blackKing() {
        return Square.of(new King(Color.BLACK));
    }
    
    public static Square whiteKing() {
        return Square.of(new King(Color.WHITE));
    }

    public static Square blackKnight() {
        return Square.of(new Knight(Color.BLACK));
    }

    public static Square whiteKnight() {
        return Square.of(new Knight(Color.WHITE));
    }
    
    public static Square whiteBishop() {
        return Square.of(new Bishop(Color.WHITE));
    }

    public static Square blackBishop() {
        return Square.of(new Bishop(Color.BLACK));
    }

    public static Square whitePawn() {
        return Square.of(new Pawn(Color.WHITE));
    }

    public static Square blackPawn() {
        return Square.of(new Pawn(Color.BLACK));
    }

    public static Square emptySquare() {
        return Square.of(null);
    }
}
