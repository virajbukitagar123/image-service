package com.chess.imageservice.board.pieces;

import com.chess.imageservice.board.Color;
import com.chess.imageservice.board.Piece;

public class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.getColor().getValue() + "k";
    }


}
