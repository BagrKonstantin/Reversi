package org.example.Classes;

import java.util.Scanner;


final public class Gameplay {
    private static final Scanner input = new Scanner(System.in);
    private static final Sign player1 = Sign.WHITE;
    private static final Sign player2 = Sign.BLACK;
    private Delegate.TableFunctions table;
    private int stupidRobotScore;
    private int smartRobotScore;
    private final static String instruction = """
            Ход игры:
            Ход можно сделать на клетку отмеченную символом %s
            Для хода введите 2 числа через пробел (строку [1, 8], стобец [1, 8]) куда вы хотите поставить фишку
            Если вы ввели невалидные данные, или пытаетесь сходить на клетку, на которую нельзя сходить,
            программа запросит повторный ввод.
                        
            Для выхода нажмите Enter
            """.formatted(Sign.UNDER_ATTACK.toString());

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
        Delegate delegate = new Delegate();
        table = delegate.getGameplayFunctions();

        Player[] players = switch (option) {
            case 1 -> new Player[]{
                    new Human(player1, delegate.getPlayerFunctions(), input),
                    new Human(player2, delegate.getPlayerFunctions(), input)};
            case 2 -> new Player[]{
                    new Robot(player1, delegate.getAIFunctions()),
                    new Human(player2, delegate.getPlayerFunctions(), input)};
            case 3 -> new Player[]{
                    new SmartRobot(player1, delegate.getAIFunctions()),
                    new Human(player2, delegate.getPlayerFunctions(), input)};
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
            step = currentPlayer.step(step);
            if (step == -1) {
                return;
            }
        }
    }

    void end(Player[] player, int step) {
        System.out.println("Игра окончена за " + (step - 2) + " ходов");
        int p1score = table.countPlayerChips(player1);
        int p2score = table.countPlayerChips(player2);

        if (player[0] instanceof Human p1 && player[1] instanceof Human p2) {
            if (p1score > p2score) System.out.println(p1 + " победил!");
            else if (p1score < p2score) System.out.printf(p2 + " победил!");
            else System.out.println("Ничья!");
            System.out.println(p1 + " набрал " + p1score + " очков");
            System.out.println(p2 + " набрал " + p2score + " очков");
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
        input.nextLine();
    }
}
