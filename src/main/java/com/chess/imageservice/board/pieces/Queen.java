package com.chess.imageservice.board.pieces;

import com.chess.imageservice.board.Color;
import com.chess.imageservice.board.Piece;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.getColor().getValue() + "q";
    }
}
