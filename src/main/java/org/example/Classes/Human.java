package org.example.Classes;

import java.util.Scanner;

public final class Human extends Player {
    private enum Options {
        UNDO,
        END,
        Wrong,
        FINE,
        PRINT
    }

    private final Scanner input;

    Human(Sign sign, Delegate.PlayerFunctions tableFunctions, Scanner in) {
        super(sign, tableFunctions, "Игрок");
        input = in;
    }

    private Options checkForNextInt() {
        if (!input.hasNextInt()) {
            String text = input.nextLine();
            if (text.equals("exit")) return Options.END;
            if (text.equals("undo")) return Options.UNDO;
            if (text.equals("print")) {
                playerFunctions.writeEveryPossibleCell();
                return checkForNextInt();
            }
            return Options.Wrong;
        }
        return Options.FINE;
    }

    private int decide(Options in, int step) {
        return switch (in) {
            case END -> -1;
            case Wrong -> step;
            case UNDO -> step > 2 ? step - 2 : step;
            case FINE, PRINT -> 0;
        };
    }

    @Override
    public int step(int step) {
        System.out.println("Введите координаты клетки, куда вы хотите сходить.\n" +
                "undo - отмена хода\texit - выйти\tprint - вывести ходы");
        Options in = checkForNextInt();
        if (in != Options.FINE) return decide(in, step);
        int row = input.nextInt();

        in = checkForNextInt();
        if (in != Options.FINE) return decide(in, step);
        int col = input.nextInt();

        return doStep(row - 1, col - 1, step);
    }
}
