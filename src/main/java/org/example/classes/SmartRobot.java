package org.example.classes;

import org.example.Interfaces.TableFunctionsAI;

public final class SmartRobot extends Robot {
    SmartRobot(Sign sign, TableFunctionsAI functionsAI) {
        super(sign, functionsAI);
    }

    @Override
    public int Step(int step) {
        Coords point = ((TableFunctionsAI)tableFunctions).chooseCellWisely(getSign());
        System.out.println(this + " сходил на " + point);
        return doStep(point.row(), point.col(), step);
    }

    @Override
    public String toString() {
        return "Умный робот";
    }
}
