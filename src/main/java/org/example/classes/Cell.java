package org.example.classes;

public final class Cell {
    Sign[] condition = new Sign[100];

    private int value = 0;
    private final double cellPositionValue;

    Cell(double value) {
        cellPositionValue = value;
        condition[0] = Sign.empty;
    }

    Cell(Sign sign) {
        condition[0] = sign;
        cellPositionValue = 0;
    }

    public boolean isUnderAttack() {
        return value > 0;
    }

    public double getValue() {
        return value + cellPositionValue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty(int step) {
        return condition[step] == Sign.empty;
    }

    public Sign getSign(int step) {
        return condition[step];
    }

    public void setSigh(Sign sign, int step) {
        condition[step] = sign;
    }

    public String toString(int step) {
        if (isUnderAttack()) return Sign.underAttack.getSymbol();
        return getSign(step).getSymbol();
    }
}
