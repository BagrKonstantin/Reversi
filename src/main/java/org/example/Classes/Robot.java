package org.example.Classes;

public class Robot extends Player {

    protected Delegate.AIFunctions aiFunctions;

    Robot(Sign sign, Delegate.PlayerFunctions playerFunctions, Delegate.AIFunctions aiFunctions) {
        super(sign, playerFunctions, "Глупый робот");
        this.aiFunctions = aiFunctions;
    }

    protected Robot(Sign sign, Delegate.PlayerFunctions playerFunctions, Delegate.AIFunctions aiFunctions, String name) {
        super(sign, playerFunctions, name);
        this.aiFunctions = aiFunctions;
    }

    @Override
    public int step(int step) {
        Coords point = aiFunctions.chooseCellWithHighestValue();
        System.out.println(this + " сходил на (" + point + ')');
        return doStep(point.row(), point.col(), step);
    }
}
