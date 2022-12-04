package org.example.Classes;

import org.example.Interfaces.TableFunctionsAI;
import org.example.Interfaces.TableFunctionsGameplay;
import org.example.Interfaces.TableFunctionsPlayers;

public final class Delegate {

    private final Table table;
    Delegate() {
        table = new Table();
    }

    public TableFunctions getGameplayFunctions() {
        return new TableFunctions();
    }

    public  PlayerFunctions getPlayerFunctions() {
        return new PlayerFunctions();
    }

    public AIFunctions getAIFunctions() {
        return new AIFunctions();
    }

    public final class AIFunctions implements TableFunctionsAI{

        @Override
        public Coords chooseCellWithHighestValue() {
            return table.chooseCellWithHighestValue();
        }

        @Override
        public Coords chooseCellWisely(Sign sign) {
            return table.chooseCellWisely(sign);
        }
    }

    public final class PlayerFunctions implements TableFunctionsPlayers {
        @Override
        public boolean addChipToBoard(int row, int col, Sign sign, int step) {
            return table.addChipToBoard(row, col, sign, step);
        }

        @Override
        public void writeEveryPossibleCell() {
            table.writeEveryPossibleCell();
        }
    }

    public final class TableFunctions  implements TableFunctionsGameplay{
        @Override
        public int countPlayerChips(Sign sign) {
            return table.countPlayerChips(sign);
        }

        @Override
        public boolean isPossibleToStep() {
            return table.isPossibleToStep();
        }

        @Override
        public void prepBeforeStep(int step, Sign sign) {
            table.prepBeforeStep(step, sign);
        }
        @Override
        public String toString() {
            return table.toString();
        }
    }
}
