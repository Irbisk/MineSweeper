package minesweeper;

import java.util.Scanner;

public class Action {
    Scanner scanner = new Scanner(System.in);
    Field minesField = new Field();
    Field gameField = new Field();

    public void startGame() {
        System.out.println("How many mines do you want on the field?");
        int mines = scanner.nextInt();
        gameField.initializeGameField(gameField);
        gameField.showField(gameField);
        minesField.initializeFirstMinesField(minesField);
        setDeleteMines(mines);
    }

    public void setDeleteMines(int mines) {
        boolean gameFinished = false;
        boolean firststep = true;
        while (!gameFinished) {
            System.out.println("Set/unset mines marks or claim a cell as free:");
            int y = scanner.nextInt();
            int x = scanner.nextInt();
            String action = scanner.next();
            if (firststep) {
                minesField.initializeField(minesField, mines, x, y);
                firststep = false;
            }
            if (gameField.cells[x - 1][y - 1].matches("\\d")) {
                System.out.println("There is a number here!");
                continue;
            } else if (gameField.cells[x - 1][y - 1].equals("/")) {
                System.out.println("You've already opened this cell!");
                continue;
            }
            switch (action) {
                case "free" :
                    if (minesField.cells[x - 1][y - 1].equals(".")) {
                        openSafeCells(minesField, x - 1, y - 1);
                        gameField.showField(gameField);
                    } else if (minesField.cells[x - 1][y - 1].matches("\\d")) {
                        gameField.cells[x - 1][y - 1] = minesField.cells[x - 1][y - 1];
                        gameField.showField(gameField);
                        if (allCellsExplored(mines)) {
                            System.out.println("Congratulations! You found all the mines!");
                            gameFinished = true;
                        }
                    } else if (minesField.cells[x - 1][y - 1].equals("X")) {
                        System.out.println("You stepped on a mine and failed!");
                        gameFinished = true;
                    }
                    break;
                case "mine":
                    if (!gameField.cells[x - 1][y - 1].equals("*")) {
                        gameField.cells[x - 1][y - 1] = "*";
                        gameField.showField(gameField);
                        if (minesField.cells[x - 1][y - 1].equals("X")) {
                            mines--;
                        } else {
                            mines++;
                        }
                        if (mines == 0) {
                            System.out.println("Congratulations! You found all the mines!");
                            gameFinished = true;
                        }
                    } else {
                        gameField.cells[x - 1][y - 1] = ".";
                        gameField.showField(gameField);
                        if (minesField.cells[x - 1][y - 1].equals("X")) {
                            mines++;
                        } else {
                            mines--;
                        }
                        if (mines == 0) {
                            System.out.println("Congratulations! You found all the mines!");
                            gameFinished = true;
                        }
                    }
                    break;
                default:
                    System.out.println("Enter correct command!");
            }
        }
    }

    public boolean allCellsExplored(int mines) {
        boolean explored = false;
        for (int i = 0; i < gameField.rows; i++) {
            for (int j = 0; j < gameField.columns; j++) {
                if (gameField.cells[i][j].equals(".") && minesField.cells[i][j].equals("X")) {
                    mines--;
                } else if (gameField.cells[i][j].equals(".")) {
                            mines++;
                }

            }
        }
        if (mines == 0) {
            explored = true;
        }
        return explored;
    }

    public void openSafeCells(Field minesField, int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    gameField.cells[x + i][y + j] = minesField.cells[x + i][y + j];
                    if (minesField.cells[x + i][y + j].equals(".")) {
                        minesField.cells[x + i][y + j] = "/";
                        gameField.cells[x + i][y + j] = "/";
                        openSafeCells(minesField, x + i, y + j);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

}
