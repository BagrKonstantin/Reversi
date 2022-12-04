package org.example.Interfaces;

import org.example.Classes.Coords;
import org.example.Classes.Sign;

public interface TableFunctionsAI {
    Coords chooseCellWithHighestValue();
    Coords chooseCellWisely(Sign sign);
}
