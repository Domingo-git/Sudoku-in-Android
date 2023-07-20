package com.domingo.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MakeGridSudoku gridSudoku;
    private CheckConditions checkConditions;

    private int[][] solvedSudoku, notSolvedSudoku;
    private int num = 0, help = 5;

    int rowPosition, columnPosition;

    private boolean ifLock = true, trueNumber, numberSelected, penActive = false;

    private TextView[][] cells;
    private Button[] inputNumbers;
    private TextView penColor, helper;

    private static final String NEW_INTENT_TAG = "MainActivity.newIntent";

    public static Intent newIntent(int level) {
        Intent intent = new Intent();
        intent.putExtra(NEW_INTENT_TAG, level);
        return intent;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridSudoku = new MakeGridSudoku();
        checkConditions = new CheckConditions();

       inputNumbers = new Button[] {findViewById(R.id.One), findViewById(R.id.Two), findViewById(R.id.Three), findViewById(R.id.Four), findViewById(R.id.Five), findViewById(R.id.Six), findViewById(R.id.Seven), findViewById(R.id.Eight), findViewById(R.id.Nine)};

        cells = new TextView[][] {
                {findViewById(R.id.cell_1_1), findViewById(R.id.cell_1_2), findViewById(R.id.cell_1_3), findViewById(R.id.cell_1_4), findViewById(R.id.cell_1_5), findViewById(R.id.cell_1_6), findViewById(R.id.cell_1_7), findViewById(R.id.cell_1_8), findViewById(R.id.cell_1_9)},
                {findViewById(R.id.cell_2_1), findViewById(R.id.cell_2_2), findViewById(R.id.cell_2_3), findViewById(R.id.cell_2_4), findViewById(R.id.cell_2_5), findViewById(R.id.cell_2_6), findViewById(R.id.cell_2_7), findViewById(R.id.cell_2_8), findViewById(R.id.cell_2_9)},
                {findViewById(R.id.cell_3_1), findViewById(R.id.cell_3_2), findViewById(R.id.cell_3_3), findViewById(R.id.cell_3_4), findViewById(R.id.cell_3_5), findViewById(R.id.cell_3_6), findViewById(R.id.cell_3_7), findViewById(R.id.cell_3_8), findViewById(R.id.cell_3_9)},
                {findViewById(R.id.cell_4_1), findViewById(R.id.cell_4_2), findViewById(R.id.cell_4_3), findViewById(R.id.cell_4_4), findViewById(R.id.cell_4_5), findViewById(R.id.cell_4_6), findViewById(R.id.cell_4_7), findViewById(R.id.cell_4_8), findViewById(R.id.cell_4_9)},
                {findViewById(R.id.cell_5_1), findViewById(R.id.cell_5_2), findViewById(R.id.cell_5_3), findViewById(R.id.cell_5_4), findViewById(R.id.cell_5_5), findViewById(R.id.cell_5_6), findViewById(R.id.cell_5_7), findViewById(R.id.cell_5_8), findViewById(R.id.cell_5_9)},
                {findViewById(R.id.cell_6_1), findViewById(R.id.cell_6_2), findViewById(R.id.cell_6_3), findViewById(R.id.cell_6_4), findViewById(R.id.cell_6_5), findViewById(R.id.cell_6_6), findViewById(R.id.cell_6_7), findViewById(R.id.cell_6_8), findViewById(R.id.cell_6_9)},
                {findViewById(R.id.cell_7_1), findViewById(R.id.cell_7_2), findViewById(R.id.cell_7_3), findViewById(R.id.cell_7_4), findViewById(R.id.cell_7_5), findViewById(R.id.cell_7_6), findViewById(R.id.cell_7_7), findViewById(R.id.cell_7_8), findViewById(R.id.cell_7_9)},
                {findViewById(R.id.cell_8_1), findViewById(R.id.cell_8_2), findViewById(R.id.cell_8_3), findViewById(R.id.cell_8_4), findViewById(R.id.cell_8_5), findViewById(R.id.cell_8_6), findViewById(R.id.cell_8_7), findViewById(R.id.cell_8_8), findViewById(R.id.cell_8_9)},
                {findViewById(R.id.cell_9_1), findViewById(R.id.cell_9_2), findViewById(R.id.cell_9_3), findViewById(R.id.cell_9_4), findViewById(R.id.cell_9_5), findViewById(R.id.cell_9_6), findViewById(R.id.cell_9_7), findViewById(R.id.cell_9_8), findViewById(R.id.cell_9_9)},
        };

        penColor = findViewById(R.id.penColor);
        helper = findViewById(R.id.help);

        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells.length; j++) {
                cells[i][j].setOnClickListener(this::onClick);
            }
        }

        for(int i = 0; i < inputNumbers.length; i++) {
            inputNumbers[i].setOnClickListener(this::onClickNumber);
        }

        
    }

    public void onClickStart(View view) {

        if(ifLock) {
            this.setTextColor_black();
            help = 5;
            helper.setText(Integer.toString(help));

            solvedSudoku = gridSudoku.makeSolvedSudoku(3);
            notSolvedSudoku = gridSudoku.makeNotSolvedSudoku(50, solvedSudoku);

            this.showGrid();
        }
    }


    public void onClickLock(View view) {
        Button start = findViewById(R.id.Start);

        if(ifLock) {
            ifLock = false;
            start.setText("Блок");
        } else {
            ifLock = true;
            start.setText("Создать судоку");
        }
    }

    @Override
    public void onClick(View v) {
        if(notSolvedSudoku != null) {
            this.setColor_White();
            for(int i = 0; i < cells.length; i++) {
                for(int j = 0; j < cells.length; j++) {
                    if(cells[i][j].getId() == v.getId()) {
                        numberSelected = true;

                        rowPosition = i;
                        columnPosition = j;

                        this.setColor_SelectedCell();
                    }
                }
            }
        }
    }

    public void onClickNumber(View v) {
        if(numberSelected && notSolvedSudoku != null) {
            for(int i = 0; i < inputNumbers.length; i++) {
                if(inputNumbers[i].getId() == v.getId()) {
                    num = (i+1);
                }
            }

            if(!penActive) {
                this.checkerAllConditions();
            } else {
                if(notSolvedSudoku[rowPosition][columnPosition] == 0) {
                    cells[rowPosition][columnPosition].setTextColor(getResources().getColor(R.color.Pen_color));
                    cells[rowPosition][columnPosition].setText(Integer.toString(num));
                }
            }
            numberSelected = false;
            this.setColor_White();
        }
    }

    public void onClickHelp(View view) {
        if(numberSelected && notSolvedSudoku != null) {
            if(help != 0 && notSolvedSudoku[rowPosition][columnPosition] == 0) {
                notSolvedSudoku[rowPosition][columnPosition] = solvedSudoku[rowPosition][columnPosition];

                cells[rowPosition][columnPosition].setTextColor(Color.BLACK);
                cells[rowPosition][columnPosition].setText(Integer.toString(notSolvedSudoku[rowPosition][columnPosition]));

                help--;

                helper.setText(Integer.toString(help));
                this.setColor_White();
            } else {
                if(notSolvedSudoku[rowPosition][columnPosition] != 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Это значение известно!", Toast.LENGTH_SHORT);
                    toast.show();
                    this.setColor_White();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Подсказок больше нет!", Toast.LENGTH_SHORT);
                    toast.show();
                    this.setColor_White();
                }
            }
        }
    }

    public void onClickPen(View view) {
        if(penActive) {
            penColor.setBackgroundColor(getResources().getColor(R.color.Red_Block));
            penActive = false;
        } else {
            penColor.setBackgroundColor(getResources().getColor(R.color.Green_Block));
            penActive = true;
        }
    }

    public void onClickEraser(View view) {
        if(numberSelected && notSolvedSudoku != null) {
            if(notSolvedSudoku[rowPosition][columnPosition] == 0) {
                cells[rowPosition][columnPosition].setText("");
                this.setColor_White();
            }
        }
    }

    private void checkerAllConditions() {
        trueNumber = checkConditions.verificationOfDecisions(num, rowPosition, columnPosition, solvedSudoku);

        if(trueNumber) {
            notSolvedSudoku[rowPosition][columnPosition] = num;
            cells[rowPosition][columnPosition].setTextColor(Color.BLACK);
            cells[rowPosition][columnPosition].setText(Integer.toString(num));
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Неверное значение!", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(checkConditions.winConditions(notSolvedSudoku)) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы победили!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void showGrid() {

        for(int i = 0; i < notSolvedSudoku.length; i++) {
            for(int j = 0; j < notSolvedSudoku.length; j++) {
                if(notSolvedSudoku[i][j] != 0) {
                    cells[i][j].setText(Integer.toString(notSolvedSudoku[i][j]));
                } else {
                    cells[i][j].setText("");
                }
            }
        }
    }

    private void setColor_SelectedCell() {
        int row = -1, col =-1;

        if(rowPosition < 3) {
            row = 0;
        } else if (rowPosition < 6) {
            row = 3;
        } else if (rowPosition < 9) {
            row = 6;
        }
        if(columnPosition < 3) {
            col = 0;
        } else if (columnPosition < 6) {
            col = 3;
        } else if (columnPosition < 9) {
            col = 6;
        }

        for(int k = row; k < (row+3); k++) {
            for(int n = col; n < (col+3); n++) {
                this.cells[k][n].setBackgroundColor((getResources().getColor(R.color.Light_Green)));
            }
        }

        for(int k = 0; k < cells.length; k++) {
            cells[rowPosition][k].setBackgroundColor(getResources().getColor(R.color.Light_Green));
        }

        for(int k = 0; k < cells.length; k++) {
            cells[k][columnPosition].setBackgroundColor(getResources().getColor(R.color.Light_Green));
        }
        cells[rowPosition][columnPosition].setBackgroundColor(getResources().getColor(R.color.Light_Yellow));
    }

    private void setColor_White() {
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells.length; j++) {
                cells[i][j].setBackgroundColor(getResources().getColor(R.color.Light_white));
            }
        }
    }

    private void setTextColor_black() {
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells.length; j++) {
                cells[i][j].setTextColor(Color.BLACK);
            }
        }
    }
}

