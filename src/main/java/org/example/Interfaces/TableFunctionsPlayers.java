package org.example.Interfaces;

import org.example.classes.Sign;

public interface TableFunctionsPlayers {
    boolean AddSymbolToBoard(int a, int b, Sign sign, int step);
    void WriteEveryPossibleCell();
}
