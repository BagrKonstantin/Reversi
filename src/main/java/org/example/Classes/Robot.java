package org.example.Classes;

public class Robot extends Player {
    Robot(Sign sign, Delegate.PlayerFunctions playerFunctions) {
        super(sign, playerFunctions, "Глупый робот");
    }

    protected Robot(Sign sign, Delegate.PlayerFunctions playerFunctions, String name) {
        super(sign, playerFunctions, name);
    }

    @Override
    public int step(int step) {
        Coords point = ((Delegate.AIFunctions)playerFunctions).chooseCellWithHighestValue();
        System.out.println(this + " сходил на (" + point + ')');
        return doStep(point.row(), point.col(), step);
    }
}
