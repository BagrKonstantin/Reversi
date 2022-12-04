package org.example.classes;

import org.example.Interfaces.TableFunctionsAI;
import org.example.Interfaces.TableFunctionsGameplay;
import org.example.Interfaces.TableFunctionsPlayers;

import java.util.Scanner;


final public class Gameplay {
    private static final Scanner input = new Scanner(System.in);
    private TableFunctionsGameplay table;
    private int stupidRobotScore;
    private int smartRobotScore;
    private final static String instruction = """
            Ход игры:
            Ход можно сделать на клетку отмеченную символом %s
            Для хода введите 2 числа через пробел (строку [1, 8], стобец [1, 8]) куда вы хотите поставить фишку
            Если вы ввели невалидные данные, или пытаетесь сходить на клетку, на которую нельзя сходить,
            программа запросит повторный ввод.
                        
            Для выхода нажмите Enter
            """.formatted(Sign.underAttack.getSymbol());

    private final static String greetings = """    
            Добро пожаловать в Реверси
            Команды:
            0 - инструкция
            1 - играть против человека
            2 - играть против тупого робота
            3 - играть против умного робота
            4 - посмотреть статистику
            5 - выход
            """;
    private static final Sign p1 = Sign.white;
    private static final Sign p2 = Sign.black;

    Gameplay() {
        stupidRobotScore = 0;
        smartRobotScore = 0;
    }


    private void showStatistics() {
        System.out.print("\033[H\033[J");

        System.out.println("Здесь вы сможете увидеть свои лучшие результаты");

        System.out.println("Лучший результат игры против тупого робота: " + stupidRobotScore);
        System.out.println("Лучший результат игры против умного робота: " + smartRobotScore);
        System.out.println("\nДля выхода нажмите Enter");
        input.nextLine();
        input.nextLine();
    }

    private void showInstruction() {
        System.out.print("\033[H\033[J");
        System.out.println(instruction);
        input.nextLine();
        input.nextLine();
    }


    public void startScreen() {
        int command = 0;
        while (command != 5) {
            System.out.print("\033[H\033[J");
            System.out.println(greetings);
            if (!input.hasNextInt()) {
                input.nextLine();
                System.out.println("Пожалуйста, вводите только числа!");
                continue;
            }
            command = input.nextInt();
            switch (command) {
                case 0 -> showInstruction();
                case 1, 2, 3 -> start(command);
                case 4 -> showStatistics();
            }

        }
    }

    public void start(int option) {
        table = Table.getGameplayFunctions();
        Player[] players = switch (option) {
            case 1 -> new Player[]{
                    new Human(p1, table.getTableFunctionsForPlayers(), input),
                    new Human(p2, table.getTableFunctionsForPlayers(), input)};
            case 2 -> new Player[]{
                    new Robot(p1, table.getTableFunctionsForAI()),
                    new Human(p2, table.getTableFunctionsForPlayers(), input)};
            case 3 -> new Player[]{
                    new SmartRobot(p1, table.getTableFunctionsForAI()),
                    new Human(p2, table.getTableFunctionsForPlayers(), input)};
            default -> new Player[]{};

        };
        onGoing(players);
    }

    public void onGoing(Player[] players) {
        int step = 1;
        boolean prev = false;
        Player currentPlayer;
        while (true) {
            System.out.print("\033[H\033[J");
            currentPlayer = players[step % 2];
            table.prepBeforeStep(step, currentPlayer.getSign());
            if (!table.isPossibleToStep()) {
                System.out.println(players[step % 2] + " не может сделать ход");
                if (!prev) {
                    prev = true;
                    ++step;
                    continue;
                }
                System.out.println(table);
                end(players, step);
                break;
            }
            prev = false;
            System.out.println("Сейчас ходит " + players[step % 2]);
            System.out.println(table);
            step = currentPlayer.Step(step);
            if (step == -1) {
                return;
            }
        }
    }

    void end(Player[] player, int step) {
        System.out.println("Игра окончена за " + (step - 2) + " ходов");
        int p1score = table.countPlayerChips(p1);
        int p2score = table.countPlayerChips(p2);

        if (player[0] instanceof Human player1 && player[1] instanceof Human player2) {
            if (p1score > p2score) System.out.println(player1 + " победил!");
            else if (p1score < p2score) System.out.printf(player2 + " победил!");
            else System.out.println("Ничья!");
            System.out.println(player1 + " набрал " + p1score + " очков");
            System.out.println(player2 + " набрал " + p2score + " очков");
        } else {
            if (p1score > p2score) System.out.println("Вы проиграли!");
            else if (p1score < p2score) System.out.println("Вы победили!");
            else System.out.println("Ничья!");
            System.out.println("Ваш счёт " + p2score);
            if (player[0] instanceof SmartRobot) {
                if (p2score > smartRobotScore) smartRobotScore = p2score;
            } else {
                if (p2score > stupidRobotScore) stupidRobotScore = p2score;
            }
        }

        System.out.println("\nДля выхода нажмите Enter");
        input.nextLine();
    }
}
