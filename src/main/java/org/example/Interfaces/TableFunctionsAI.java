package org.example.Interfaces;

import org.example.classes.Coords;
import org.example.classes.Sign;

public interface TableFunctionsAI extends TableFunctionsPlayers {
    Coords chooseCellWithHighestValue();
    Coords chooseCellWisely(Sign symbol);
}
