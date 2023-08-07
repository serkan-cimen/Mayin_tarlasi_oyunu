import java.util.Scanner;
import java.util.Random;
public class MineSweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Mayın Tarlası Oyununa Hoşgeldiniz !");

        int row, col;
        System.out.print("Satır Giriniz : ");
        row = scanner.nextInt();
        System.out.print("Sütun Giriniz : ");
        col = scanner.nextInt();

        MineSweeperGame game = new MineSweeperGame(row, col);
        game.initialize();
        game.play();

        scanner.close();
    }
}

class MineSweeperGame {
    private int[][] board;
    private boolean[][] mines;
    private int rows, cols;
    private int reaminingCells;

    public MineSweeperGame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols];
        mines = new boolean[rows][cols];
        reaminingCells = rows * cols;
    }

    public void initialize() {
        int totalMines = reaminingCells / 4;
        Random random = new Random();

        for (int i = 0; i < totalMines; i++) {
            int randRow, randCol;
            do {
                randRow = random.nextInt(rows);
                randCol = random.nextInt(cols);
            } while (mines[randRow][randCol]);

            mines[randRow][randCol] = true;
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard();

            int row, col;
            do {
                System.out.print("Satır Giriniz : ");
                row = scanner.nextInt();
                System.out.print("Sütun Giriniz : ");
                col = scanner.nextInt();
            } while (!isValidCell(row, col));

            if (mines[row][col]) {
                printBoardWithMines();
                System.out.println("Game Over!!!");
                break;
            } else {
                uncoverCell(row, col);
                if (reaminingCells == 0) {
                    printBoard();
                    System.out.println("Oyunu Kazandınız !");
                    break;
                }
            }
        }

        scanner.close();
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private void uncoverCell(int row, int col) {
        if (isValidCell(row, col) && board[row][col] == 0) {
            int count = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (isValidCell(row + i, col + j) && mines[row + i][col + j]) {
                        count++;
                    }
                }
            }
            board[row][col] = count;
            reaminingCells--;

            if (count == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        uncoverCell(row + i, col + j);
                    }
                }
            }
        }
    }

    private void printBoard() {
        System.out.println("============================");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == -1) {
                    System.out.print("* ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private void printBoardWithMines() {
        System.out.println("Mayınların Konumu");
        System.out.println("=============================");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mines[i][j]) {
                    System.out.print("* ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
}