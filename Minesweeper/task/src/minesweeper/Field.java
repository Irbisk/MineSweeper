package minesweeper;

import java.util.Random;

public class Field {
    int rows = 9;
    int columns = 9;
    String[][] cells = new String[rows][columns];

    public Field() {
    }

    public void initializeGameField(Field field) {
        String safeCell = ".";
        for (int i = 0; i < field.rows; i++) {
            for (int j = 0; j < field.columns; j++) {
                field.cells[i][j] = safeCell;
            }
        }
    }
    public void initializeFirstMinesField(Field field) {
        for (int i = 0; i < field.rows; i++) {
            for (int j = 0; j < field.columns; j++) {
                field.cells[i][j] = "N";
            }
        }
    }

    public void initializeField(Field field, int mines, int x, int y) {
        String mine = "X";
        String safeCell = ".";
        int countMines = mines;
        int freecells = 80;
        field.cells[x - 1][y - 1] = safeCell;
        while (countMines != 0) {
            countMines = mines;
            for (int i = 0; i < field.rows; i++) {
                for (int j = 0; j < field.columns; j++) {
                    if (field.cells[i][j].equals(safeCell)) {
                        continue;
                    } else if (countMines == 0) {
                        field.cells[i][j] = safeCell;
                        freecells--;
                    } else {
                        if (freecells == countMines) {
                            field.cells[i][j] = mine;
                            countMines--;
                            freecells--;
                        } else {
                            Random random = new Random();
                            int value = random.nextInt(7);
                            if (value == 0) {
                                field.cells[i][j] = mine;
                                countMines--;
                                freecells--;
                            } else {
                                field.cells[i][j] = safeCell;
                                freecells--;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < field.rows; i++) {
            for (int j = 0; j < field.columns; j++) {
                mines = 0;
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        try {
                            if (field.cells[i + k][j + l].equals("X")) {
                                mines++;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                    }
                }
                if (mines != 0 && !field.cells[i][j].equals("X")) {
                    field.cells[i][j] = String.valueOf(mines);
                }
            }
        }
    }

    public void showField(Field field) {
        int line = 1;
        System.out.println(" |123456789|\n" +
                "-|---------|");
        for (String[] i: field.cells) {
            System.out.print(line++ + "|");
            for (String j: i) {
                System.out.print(j);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }
}
