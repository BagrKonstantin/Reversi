package org.example.classes;


import org.example.Interfaces.TableFunctionsPlayers;

public abstract class Player{
    protected TableFunctionsPlayers tableFunctions;
    protected Player(Sign sign, TableFunctionsPlayers functionsPlayers) {
        this.sign = sign;
        this.tableFunctions = functionsPlayers;
    }
    private final Sign sign;

    public Sign getSign() {
        return sign;
    }

    public abstract int Step(int step);

    protected int doStep(int a, int b, int step) {
        if (0 <= a && a < 8 && 0 <= b && b < 8 && tableFunctions.AddSymbolToBoard(a, b, getSign(), step)) {
            return step + 1;
        }
        return step;
    }
}
