package com.domingo.sudoku;

import android.widget.Toast;

public class CheckConditions {

    protected boolean verificationOfDecisions(int number, int rowPosition, int columnPosition ,int[][] solvedSudoku) {
        if(solvedSudoku[rowPosition][columnPosition] == number)
            return true;
        else
            return false;
    }

    protected boolean winConditions(int[][] notSolvedSudoku) {

        for(int i = 0; i < notSolvedSudoku.length; i++) {
            for(int j = 0; j < notSolvedSudoku.length; j++) {
                if(notSolvedSudoku[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
