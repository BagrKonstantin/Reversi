package org.example.classes;

import org.example.Interfaces.TableFunctionsAI;
import org.example.Interfaces.TableFunctionsGameplay;
import org.example.Interfaces.TableFunctionsPlayers;

import java.util.ArrayList;

public final class Table implements TableFunctionsGameplay, TableFunctionsAI {
    private static final int table_size = 8;
    private final Cell[][] table;
    private int currentStep;
    private double max_value = 0.0;
    private final ArrayList<Coords> cellsUnderAttack;

    private Table() {
        table = new Cell[table_size][table_size];
        cellsUnderAttack = new ArrayList<>();
        for (int i = 0; i < table_size; i++) {
            for (int j = 0; j < table_size; j++) {
                double value = 0;
                if (i == 0 || i == 7 || j == 0 || j == 7) value = 0.4;
                if (i == j && (i == 0 || i == 7)) value = 0.8;
                if (i == 0 && j == 7 || i == 7 && j == 0) value = 0.8;

                table[i][j] = new Cell(value);
            }
        }
        table[3][4] = new Cell(Sign.white);
        table[4][3] = new Cell(Sign.white);
        table[3][3] = new Cell(Sign.black);
        table[4][4] = new Cell(Sign.black);
    }

    public static TableFunctionsGameplay getGameplayFunctions() {
        return new Table();
    }

    private boolean checkCoords(int row, int col) {
        return 0 <= row && row < 8 && 0 <= col && col < 8;
    }

    private void setBeginOfTheStep(int step) {
        for (int i = 0; i < table_size; i++)
            for (int j = 0; j < table_size; j++)
                table[i][j].setSigh(table[i][j].getSign(step - 1), step);
    }

    private int whoIsOwnerOFTheCell(int row, int col, Sign sign) {
        if (table[row][col].isEmpty(currentStep)) return 0;
        if (table[row][col].getSign(currentStep) == sign) return 1;
        return -1;
    }

    private void fillEnemyCells(int row, int col, int a, int b, int k, Sign sign) {
        int flag = 0;
        for (int i = 0; checkCoords(row + a * i, col + b * i); i += k) {
            if (i == 0) continue;
            flag = whoIsOwnerOFTheCell(row + a * i, col + b * i, sign);
            if (flag >= 0) break;
        }
        if (flag > 0) {
            for (int i = 0; checkCoords(row + a * i, col + b * i); i += k) {
                if (i == 0) continue;
                if (table[row + a * i][col + b * i].getSign(currentStep) == sign) break;
                table[row + a * i][col + b * i].setSigh(sign, currentStep);
            }
        }
    }


    private void fillCells(int row, int col, Sign sign) {
        for (int i = 0; i <= 1; i++)
            for (int j = -1; j <= 1; j += 2)
                fillEnemyCells(row, col, i, Math.abs(i - 1), j, sign);

        for (int i = -1; i <= 1; i += 2)
            for (int j = -1; j <= 1; j += 2)
                fillEnemyCells(row, col, 1, i, j, sign);
    }

    private int pointsForCell(int row, int col) {
        if (row == 0 || col == 0 || row == 7 || col == 7) return 2;
        return 1;
    }

    private int countValuesOfCell(int row, int col, int a, int b, int k, Sign sign) {
        int sum = 0;
        int flag = 0;
        for (int i = 0; checkCoords(row + a * i, col + b * i); i += k) {
            if (i == 0) continue;
            flag = whoIsOwnerOFTheCell(row + a * i, col + b * i, sign);
            if (flag >= 0) break;
            sum += pointsForCell(row + a * i, col + b * i);
        }
        if (flag > 0) return sum;
        return 0;
    }


    private void countValuesOfCells(int row, int col, Sign sign) {
        int total_sum = 0;
        for (int i = 0; i <= 1; i++)
            for (int j = -1; j <= 1; j += 2)
                total_sum += countValuesOfCell(row, col, i, Math.abs(i - 1), j, sign);

        for (int i = -1; i <= 1; i += 2)
            for (int j = -1; j <= 1; j += 2)
                total_sum += countValuesOfCell(row, col, 1, i, j, sign);

        if (total_sum > 0) cellsUnderAttack.add(new Coords(row, col));
        table[row][col].setValue(total_sum);
        if (total_sum > max_value) {
            max_value = table[row][col].getValue();
        }
    }

    private boolean isEnemyAround(int row, int col, Sign sign) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!checkCoords(row + i, col + j)) continue;
                if (!(i == 0 && j == 0)) {
                    if (!table[row + i][col + j].isEmpty(currentStep)) {
                        if (table[row + i][col + j].getSign(currentStep) != sign) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void whereCanBePlaced(Sign sign) {
        for (int i = 0; i < table_size; i++) {
            for (int j = 0; j < table_size; j++) {
                if (table[i][j].isEmpty(currentStep)) {
                    if (isEnemyAround(i, j, sign)) countValuesOfCells(i, j, sign);
                }
            }
        }
    }

    private void clearValues() {
        for (var cell: cellsUnderAttack)
            table[cell.row()][cell.col()].setValue(0);
        max_value = 0;
        cellsUnderAttack.clear();
    }

    @Override
    public boolean isPossibleToStep() {
        return cellsUnderAttack.size() > 0;
    }

    @Override
    public void prepBeforeStep(int step, Sign sign) {
        clearValues();
        currentStep = step;
        setBeginOfTheStep(step);
        whereCanBePlaced(sign);
    }

    @Override
    public TableFunctionsAI getTableFunctionsForAI() {
        return this;
    }

    @Override
    public TableFunctionsPlayers getTableFunctionsForPlayers() {
        return this;
    }


    @Override
    public int countPlayerChips(Sign sign) {
        int total = 0;
        for (int i = 0; i < table_size; i++)
            for (int j = 0; j < table_size; j++)
                if (table[i][j].getSign(currentStep) == sign) ++total;
        return total;
    }

    @Override
    public String toString() {
        String h = "   +-----+-----+-----+-----+-----+-----+-----+-----+";
        StringBuilder sb = new StringBuilder();
        sb.append("      ");
        for (int i = 0; i < table_size; i++) {
            sb.append(i + 1);
            sb.append("     ");
        }
        sb.append('\n');
        sb.append(h);
        sb.append('\n');
        for (int i = 0; i < table_size; i++) {
            sb.append(i + 1);
            sb.append("  | ");
            for (int j = 0; j < table_size; j++) {
                sb.append(table[i][j].toString(currentStep));
                sb.append(" | ");
            }
            sb.append('\n');
            sb.append(h);
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean AddSymbolToBoard(int row, int col, Sign sign, int step) {
        if (table[row][col].isUnderAttack()) {
            table[row][col].setSigh(sign, step);
            fillCells(row, col, sign);
            return true;
        }
        return false;
    }

    @Override
    public void WriteEveryPossibleCell() {
        for (var item : cellsUnderAttack) {
            System.out.print("(" + item + ") ");
        }
        System.out.println();
    }

    @Override
    public Coords chooseCellWithHighestValue() {
        for (var cell : cellsUnderAttack)
            if (table[cell.row()][cell.col()].getValue() == max_value) return cell;
        return new Coords(0, 0);
    }

    @Override
    public Coords chooseCellWisely(Sign symbol) {
        int step = currentStep;
        double max_val = -1000;
        Coords point = new Coords(0, 0);
        Sign enemy_symbol = symbol == Sign.white ? Sign.black : Sign.white;
        for (var cell : new ArrayList<>(cellsUnderAttack)) {
            double value = table[cell.row()][cell.col()].getValue();
            AddSymbolToBoard(cell.row(), cell.col(), symbol, step);
            prepBeforeStep(currentStep + 1, enemy_symbol);
            value -= max_value;
            if (value > max_val) {
                point = new Coords(cell.row(), cell.col());
                max_val = value;
            }
            prepBeforeStep(step, symbol);
        }
        return point;
    }
}
