package org.example.Classes;

public final class SmartRobot extends Robot {
    SmartRobot(Sign sign, Delegate.AIFunctions functionsAI) {
        super(sign, functionsAI, "Умный робот");
    }

    @Override
    public int step(int step) {
        Coords point = ((Delegate.AIFunctions)playerFunctions).chooseCellWisely(getSign());
        System.out.println(this + " сходил на " + point);
        return doStep(point.row(), point.col(), step);
    }
}
