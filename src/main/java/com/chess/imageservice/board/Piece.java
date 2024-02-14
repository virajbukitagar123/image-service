package com.chess.imageservice.board;

import java.awt.image.BufferedImage;

public abstract class Piece {

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}