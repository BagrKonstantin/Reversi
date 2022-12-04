package org.example.classes;

public enum Sign {
    black(" \u25A0 "),
    white(" \u25EF "),
    empty("   "),
    underAttack(" \u00B7 ");

    private final String symbol;
    Sign(String symbol) {
        this.symbol = symbol;
    }
    public String getSymbol() {
        return symbol;
    }
}
