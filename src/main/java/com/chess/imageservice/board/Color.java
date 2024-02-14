package com.chess.imageservice.board;

public enum Color {
    WHITE("w"),
    BLACK("b");

    private final String value;

    Color(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
