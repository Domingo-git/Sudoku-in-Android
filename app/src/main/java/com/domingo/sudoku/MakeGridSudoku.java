package com.domingo.sudoku;

import java.util.Random;

public class MakeGridSudoku {

    protected int[][] makeSolvedSudoku(int n) {
        MakeGridSudoku sudoku = new MakeGridSudoku();
        int[][] SolvedSudoku = new int[n*n][n*n];

        SolvedSudoku = this.makeDefaultGrid(n);
        SolvedSudoku = randomMix(SolvedSudoku);

        return SolvedSudoku;
    }

    protected int[][] makeNotSolvedSudoku(int level, int[][] SolvedSudoku) {
        MakeGridSudoku sudoku = new MakeGridSudoku();
        int[][] NotSolvedSudoku = new int[SolvedSudoku.length][SolvedSudoku.length];

        for (int i = 0; i < SolvedSudoku.length; i++) {
            for(int j = 0; j < SolvedSudoku.length; j++) {
                NotSolvedSudoku[i][j] = SolvedSudoku[i][j];
            }
        }

        NotSolvedSudoku = deleteRandomCell(NotSolvedSudoku, level);
        return NotSolvedSudoku;
    }

    private int[][] makeDefaultGrid(int n) {

        int [][]DefaultGrid = new int[n*n][n*n];

        for (int i = 0; i < DefaultGrid.length; i++) {
            for (int j = 0; j < DefaultGrid.length; j++) {
                DefaultGrid[i][j] = ((i * n + i / n + j) % (n * n) + 1);
            }
        }
        return DefaultGrid;
    }

    private int[][] transposing(int[][] gridSudoku) {

        int[][] transposingGridSudoku = new int[gridSudoku.length][gridSudoku.length];

        for (int i = 0; i < gridSudoku.length; i++) {
            for (int j = 0; j < gridSudoku.length; j++) {
                transposingGridSudoku[j][i] = gridSudoku[i][j];
            }
        }
        return transposingGridSudoku;
    }

    private int[][] swap_rows_in_area(int[][] gridSudoku) {
        Random rand = new Random();
        int randomArea = rand.nextInt(3);
        int line1 = rand.nextInt(3);
        int line2 = -1;

        int swapRowGridSudoku[][] = new int[gridSudoku.length][gridSudoku.length];

        while (true) {
            line2 = rand.nextInt(3);
            if (line2 != line1)
                break;
        }

        for (int i = 0; i < gridSudoku.length; i++) {
            for (int j = 0; j < gridSudoku.length; j++) {
                swapRowGridSudoku[i][j] = gridSudoku[i][j];
            }
        }

        int N1 = ((randomArea * 3) + line1), N2 = ((randomArea * 3) + line2);

        for (int i = 0; i < gridSudoku.length; i++) {
            swapRowGridSudoku[N1][i] = gridSudoku[N2][i];
            swapRowGridSudoku[N2][i] = gridSudoku[N1][i];
        }
        return swapRowGridSudoku;
    }

    private int[][] swap_column_in_area(int[][] gridSudoku) {

        MakeGridSudoku grid = new MakeGridSudoku();

        gridSudoku = grid.transposing(gridSudoku);
        gridSudoku = grid.swap_rows_in_area(gridSudoku);
        gridSudoku = grid.transposing(gridSudoku);

        return gridSudoku;
    }

    private int[][] swap_rows_area(int[][] gridSudoku) {

        Random rand = new Random();

        int area1 = (rand.nextInt(3) * 3);
        int area2;

        int swapAreaSudoku[][] = new int[gridSudoku.length][gridSudoku.length];

        while (true) {
            area2 = (rand.nextInt(3) * 3);
            if (area2 != area1)
                break;
        }

        for (int i = 0; i < gridSudoku.length; i++) {
            for (int j = 0; j < gridSudoku.length; j++) {
                swapAreaSudoku[i][j] = gridSudoku[i][j];
            }
        }

        for (int i = area1, k = 0; i < (area1 + 3); i++, k++) {
            for (int j = 0; j < gridSudoku.length; j++) {
                swapAreaSudoku[i][j] = gridSudoku[area2 + k][j];
                swapAreaSudoku[area2 + k][j] = gridSudoku[i][j];
            }
        }
        return swapAreaSudoku;
    }

    private int[][] swap_column_area(int[][] gridSudoku) {

        MakeGridSudoku grid = new MakeGridSudoku();

        gridSudoku = grid.transposing(gridSudoku);
        gridSudoku = grid.swap_rows_area(gridSudoku);
        gridSudoku = grid.transposing(gridSudoku);

        return gridSudoku;
    }

    private int[][] randomMix(int[][] SolvedSudoku) {

        Random rand = new Random();
        MakeGridSudoku sudoku = new MakeGridSudoku();
        int numberMix, methodMix;

        numberMix = 10;

        for (int i = 0; i < numberMix; i++) {
            methodMix = rand.nextInt(5);
            switch (methodMix) {
                case 0:
                    SolvedSudoku = sudoku.transposing(SolvedSudoku);
                    break;
                case 1:
                    SolvedSudoku = sudoku.swap_rows_in_area(SolvedSudoku);
                    break;
                case 2:
                    SolvedSudoku = sudoku.swap_column_in_area(SolvedSudoku);
                    break;
                case 3:
                    SolvedSudoku = sudoku.swap_rows_area(SolvedSudoku);
                    break;
                case 4:
                    SolvedSudoku = sudoku.swap_column_area(SolvedSudoku);
                    break;
            }
        }
        return SolvedSudoku;
    }

    private int[][] deleteRandomCell(int[][] NotSolvedSudoku, int level) {

        Random rand = new Random();
        int RowPosition, ColumnPosition;

        for(int i = 0; i < level; i++) {
            RowPosition = rand.nextInt(9);
            ColumnPosition = rand.nextInt(9);

            if(NotSolvedSudoku[RowPosition][ColumnPosition] != 0) {
                NotSolvedSudoku[RowPosition][ColumnPosition] = 0;
            } else i--;
        }

        return NotSolvedSudoku;
    }
}