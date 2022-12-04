package org.example.Classes;

public final class SmartRobot extends Robot {
    SmartRobot(Sign sign, Delegate.PlayerFunctions functionsAI, Delegate.AIFunctions aiFunctions) {
        super(sign, functionsAI, aiFunctions, "Умный робот");
    }

    @Override
    public int step(int step) {
        Coords point = aiFunctions.chooseCellWisely(getSign());
        System.out.println(this + " сходил на " + point);
        return doStep(point.row(), point.col(), step);
    }
}
