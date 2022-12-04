package org.example.Interfaces;

import org.example.Classes.Sign;

public interface TableFunctionsPlayers {
    boolean addChipToBoard(int row, int col, Sign sign, int step);
    void writeEveryPossibleCell();
}
