package org.example.classes;

import org.example.Interfaces.TableFunctionsPlayers;

import java.util.Scanner;

public final class Human extends Player {
    private enum Options {
        Undo,
        End,
        Wrong,
        Fine,
        Print
    }

    private final Scanner input;

    Human(Sign sign, TableFunctionsPlayers tableFunctions, Scanner in) {
        super(sign, tableFunctions);
        input = in;
    }

    @Override
    public String toString() {
        return "Игрок " + getSign().getSymbol();
    }

    private Options checkForNextInt() {
        if (!input.hasNextInt()) {
            String text = input.nextLine();
            if (text.equals("exit")) return Options.End;
            if (text.equals("undo")) return Options.Undo;
            if (text.equals("print")) {
                tableFunctions.WriteEveryPossibleCell();
                return checkForNextInt();
            }
            return Options.Wrong;
        }
        return Options.Fine;
    }

    private int decide(Options in, int step) {
        return switch (in) {
            case End -> -1;
            case Wrong -> step;
            case Undo -> step > 2 ? step - 2 : step;
            case Fine, Print -> 0;
        };
    }

    @Override
    public int Step(int step) {
        System.out.println("Введите координаты клетки, куда вы хотите сходить.\n" +
                "undo - отмена хода\texit - выйти\tprint - вывести ходы");
        Options in = checkForNextInt();
        if (in != Options.Fine) return decide(in, step);
        int row = input.nextInt();

        in = checkForNextInt();
        if (in != Options.Fine) return decide(in, step);
        int col = input.nextInt();

        return doStep(row - 1, col - 1, step);
    }
}
