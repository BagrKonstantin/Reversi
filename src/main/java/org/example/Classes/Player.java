package org.example.Classes;


public abstract class Player {
    protected Delegate.PlayerFunctions playerFunctions;
    private final String name;
    private final Sign sign;

    protected Player(Sign sign, Delegate.PlayerFunctions functionsPlayers, String name) {
        this.sign = sign;
        this.playerFunctions = functionsPlayers;
        this.name = name;
    }

    public Sign getSign() {
        return sign;
    }

    public abstract int step(int step);

    protected int doStep(int a, int b, int step) {
        if (0 <= a && a < 8 && 0 <= b && b < 8 && playerFunctions.addChipToBoard(a, b, getSign(), step)) {
            return step + 1;
        }
        return step;
    }

    @Override
    public String toString() {
        return name + ' ' + sign;
    }
}
