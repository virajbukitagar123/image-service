package com.chess.imageservice.board;

public class Square {

    private Piece piece;
    private boolean isEmpty;

    public Square(Piece piece, boolean isEmpty) {
        this.piece = piece;
        this.isEmpty = isEmpty;
    }

    public static Square of(Piece piece) {
        boolean isEmpty = piece == null ? true : false;
        return new Square(piece, isEmpty);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
