package org.example.Interfaces;

import org.example.Classes.Sign;

public interface TableFunctionsGameplay {
    int countPlayerChips(Sign sign);

    boolean isPossibleToStep();

    void prepBeforeStep(int step, Sign sign);
}
