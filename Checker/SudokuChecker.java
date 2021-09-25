//Chan Ka Wai 17220416
//This program uses 3 methods to check whether the rows, coloumns and subgrids of a Sudoku is valid. If all of these are valid,  a Sudoku board will be printed. Otherwise, nothing will be printed.
import java.io.File;
import java.util.Scanner;

public class SudokuChecker {
    public static void main(String[] args) throws Exception {
        new SudokuChecker().runApp(args);
    }

    SudokuChecker() {

    }

    void runApp(String[] args) throws Exception {
        String filename = args[0];
        boolean isOK = true;
        File inputFile = new File(filename);
        if (!inputFile.exists()) {
            System.out.println("The file " + filename + " is not found.");
            System.exit(0);
        }

        Scanner in = new Scanner(inputFile);

        int numberOfRow = in.nextInt(); // read the number of rows, i.e. m of the m*n subgrid
        int numberOfCol = in.nextInt(); // read the number of columns, i.e.  n of the m*n subgrid
        int[][] sudoku = new int[numberOfRow * numberOfCol][numberOfRow * numberOfCol];
        System.out.println("Input File: " + filename);
        System.out.println(numberOfRow * numberOfCol + "x" + numberOfCol * numberOfRow + " Sudoku Grid  (" + numberOfRow + "x" + numberOfCol + "subgrids)");

        while (in.hasNextLine()) {
            String x = in.next();  //read both the column and row, e.g. C5, A3, etc.
            char input = 0;
            char input1 = 0;
            char input2 = 0;
            char input3 = 0;
            switch (x.length()) {
                case 4:
                    input3 = x.charAt(3);  //get the fourth character from the input file
                case 3:
                    input2 = x.charAt(2);  //get the third character from the input file
                case 2:
                    input1 = x.charAt(1);  //get the second character from the input file
                case 1:
                    input = x.charAt(0);   //get the first character from the input file
            }
            int y = in.nextInt();   //read the numbers that will be put inside the sudoku
            int col = 0;
            int row = 0;
            if ((int) input1 >= 65 && (int) input1 <= 90) {  //if input1 is character
                col = (input - 64) * 26 + input1 - 65;
                if (input3 > 0) {
                    String f = "" + input2 + input3;
                    row = Integer.parseInt(f) - 1;
                } else {
                    String f = "" + input2;
                    row = Integer.parseInt(f) - 1;
                }
            } else {
                col = input - 65;
                if ((int) input2 >= 48 && (int) input2 <= 57) {  //if input2 is a digit number
                    String s = "" + input1 + input2;
                    row = Integer.parseInt(s) - 1;
                } else {
                    String str = "" + input1;
                    row = Integer.parseInt(str) - 1;
                }
            }
            System.out.print(x + " ");
            System.out.print(y);
            char c = input; //c=the first character from input=C
            char d = input1;//d=the second character from input=5
            if (sudoku[row][col]  == 0 && checkRow(sudoku, row, col, y) &&
                    checkColumn(sudoku, row, col, y) &&
                    checkGrid(sudoku, numberOfRow, numberOfCol, row, col, y)) {
                sudoku[row][col] = y; //Assign value to a cell
                System.out.print("    -->    OK");
            } else {
                System.out.print("    -->    *** FAILED!! ***");
                isOK = false;
            }

            System.out.println();
        }

        //prints output
        if (isOK) {
            System.out.print("  ");
            for (int t = 0; t < sudoku.length; t++) {
                if (t < 26) {
                    int asciiCode = 65 + t;
                    System.out.print((numberOfRow * numberOfCol >= 10 ? " " : "") + " " + (char) asciiCode); //prints A-Z on the top
                } else {
                    char row1 = (char) (t / 26 + 64);
                    char row2 = (char) (t % 26 + 65);
                    String output = "" + row1 + row2;
                    System.out.print(" " + output);

                }

            }
            System.out.println();

            System.out.print(" ");
            for (int k = 0; k < sudoku.length; k++) {
                if (numberOfRow * numberOfCol >= 26) {
                    System.out.print(k == 0 ? " +--" : "+--");
                } else {
                    System.out.print(k == 0 ? " +-" : "+-");
                }
            }
            System.out.print("+");
            System.out.println();

            for (int i = 0; i < sudoku.length; i++) {
                System.out.print(((i + 1) <= 9 ? " " + (i + 1) : (i + 1)) + "|");

                for (int j = 0; j < sudoku.length; j++) {
                    if (sudoku[i][j] < 10 && numberOfRow * numberOfCol >= 10)
                        System.out.print(" ");
                    System.out.print((sudoku[i][j] == 0 ? " " : sudoku[i][j]) + "|");
                }
                System.out.println((i + 1));
                System.out.print(" ");
                for (int k = 0; k < sudoku.length; k++) {
                    int totalAmount = numberOfRow * numberOfCol;
                    if (totalAmount >= 10) {
                        System.out.print(k == 0 ? " +--" : "+--");
                    } else {
                        System.out.print(k == 0 ? " +-" : "+-");
                    }
                }
                System.out.print("+");
                System.out.println();
            }

            System.out.print("  ");
            for (int t = 0; t < sudoku.length; t++) {
                if (t < 26) {
                    int asciiCode = 65 + t;
                    System.out.print((numberOfRow * numberOfCol >= 10 ? " " : "") + " " + (char) asciiCode); //prints A-Z at the bottom
                } else {
                    char row1 = (char) (t / 26 + 64);
                    char row2 = (char) (t % 26 + 65);
                    String output = "" + row1 + row2;
                    System.out.print(" " + output);

                }
            }
        }
    }

    // check whether there is duplication of digits in a row
    // "y" is the number assigned to the a cell
    // "row" is the total number of row in the Sudoku
    // "col" is the total number of column in the Sudoku
    boolean checkRow(int[][] sudoku, int row, int col, int y) {
        for (int i = 0; i < sudoku[row].length; i++) {
            if (sudoku[row][i] == y) {
                return false;
            }
        }
        return true;   //return true if it has no duplication in a column
    }

    // check whether there is a duplication of digits in a column
    // "y" is the number assigned to the a cell
    // "row" is the total number of row in the Sudoku
    // "col" is the total number of column in the Sudoku
    boolean checkColumn(int[][] sudoku, int row, int col, int y) {
        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[i][col] == y) {
                return false;
            }
        }
        return true;   //return true if it has no duplication in a column
    }

    //check whether there is a  duplication of digits in a grid
    /// "numberOfRow" is the number of rows in a subgrid
    // "numberOfRowCol" is the number of columns in a subgrid
    // "y" is the number assigned to the a cell
    // "row" is the total number of rows in the Sudoku
    // "col" is the total number of columns in the Sudoku
    boolean checkGrid(int[][] sudoku, int numberOfRow, int numberOfCol, int row, int col, int y) {
        int r = row - row % numberOfRow;
        int c = col - col % numberOfCol;
        for (int i = r; i < r + numberOfRow; i++) {
            for (int j = c; j < c + numberOfCol; j++) {
                if (sudoku[i][j] == y)
                    return false;

            }
        }
        return true;    //return true if it has no duplication in a subgrid
    }
}