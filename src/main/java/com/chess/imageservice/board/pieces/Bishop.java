package com.chess.imageservice.board.pieces;

import com.chess.imageservice.board.Color;
import com.chess.imageservice.board.Piece;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }


    @Override
    public String toString() {
        return super.getColor().getValue() + "b";
    }


}
