package com.chess.imageservice.board.pieces;

import com.chess.imageservice.board.Color;
import com.chess.imageservice.board.Piece;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.getColor().getValue() + "n";
    }
}
