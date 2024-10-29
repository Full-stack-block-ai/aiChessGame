package com.aichessgame.utils;

public enum Color {
    WHITE,
    BLACK;

    // Optional method to get the opposite color
    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}