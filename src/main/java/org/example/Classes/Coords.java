package org.example.Classes;

public record Coords(int row, int col) {
    @Override
    public String toString() {
        return Integer.toString(row + 1) + ' ' + (col + 1);
    }
}
