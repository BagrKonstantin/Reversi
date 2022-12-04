package org.example.Classes;

public enum Sign {
    BLACK(" \u25A0 "),
    WHITE(" \u25EF "),
    EMPTY("   "),
    UNDER_ATTACK(" \u00B7 ");
    private final String symbol;

    Sign(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
