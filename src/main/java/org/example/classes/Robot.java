package org.example.classes;

import org.example.Interfaces.TableFunctionsAI;

public class Robot extends Player {
    Robot(Sign sign, TableFunctionsAI functionsAI) {
        super(sign, functionsAI);
    }

    @Override
    public int Step(int step) {
        Coords point = ((TableFunctionsAI) tableFunctions).chooseCellWithHighestValue();
        System.out.println(this + " сходил на " + point);
        return doStep(point.row(), point.col(), step);
    }

    @Override
    public String toString() {
        return "Глупый робот";
    }
}
