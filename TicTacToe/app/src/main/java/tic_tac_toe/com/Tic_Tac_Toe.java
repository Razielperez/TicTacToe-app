package tic_tac_toe.com;

import android.widget.ImageView;

public class Tic_Tac_Toe {
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int CIRCLE = 2;

    // Name-constants to represent the various states of the game
    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int CROSS_WON = 2;
    public static final int CIRCLE_WON = 3;

    private static final int ROWS = 3, COLS = 3;
    private static int[][] board = new int[ROWS][COLS];

    private static int currentState;
    private static int currentPlayer;
    private static int currentRow, currentCol;
    private static int numLine;
    public static void play(ImageView im,int row,int col){

            playerMove(currentPlayer,row,col,im);
            updateGame(currentPlayer, currentRow, currentCol);
            currentPlayer = (currentPlayer == CROSS) ? CIRCLE : CROSS;


    }

    public static int getCurrentPlayer() { return currentPlayer; }

    public static int getCurrentCol() { return currentCol; }

    public static int getCurrentState() { return currentState; }

    public static int getCurrentRow() { return currentRow; }

    public static int[][] getBoard() { return board; }

    public static int getNumLine() { return numLine; }

    public static void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = EMPTY;  // all cells empty
            }
        }
        currentState = PLAYING; // ready to play
        currentPlayer = CROSS;  // cross plays first
    }

    public static void playerMove(int current, int row, int col, ImageView im) {
        if (current == CROSS) {
            im.setImageResource(R.drawable.x2);
        } else {
            im.setImageResource(R.drawable.o2);
        }
        currentRow = row;
        currentCol = col;
        board[currentRow][currentCol] = current;
    }






    public static void updateGame(int current, int currentRow, int currentCol) {
        if (hasWon(current, currentRow, currentCol)) {
            currentState = (current == CROSS) ? CROSS_WON : CIRCLE_WON;
        } else if (isDraw()) {  // check for draw
            currentState = DRAW;
        }

    }
    public static boolean isDraw() {
        boolean draw = true;
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == EMPTY){ // if there is empty cell
                    draw = false;
                    break;
                }
            }
        }
        return draw;
    }

    public static boolean hasWon(int current, int currentRow, int currentCol) {
        if (validate3inDiagonal(currentRow, currentCol, current)){

            return true;
        }
        if (validate3inColumn(currentCol, current)) {
            numLine=currentCol+5;
            return true;
        }
        if (validate3inRow(currentRow, current)) {
            numLine=currentRow+2;
            return true;
        }
        return false;

    }


    public static boolean validate3inDiagonal(int currentRow, int currentCol, int current) {

        if (backwardDiagonal(currentRow, currentCol, current)) {
            numLine=0;
            return true;
        }
        if (forwardDiagonal(currentRow, currentCol, current)){
            numLine=1;
            return true;
        }
        return false;


    }

    public static boolean backwardDiagonal(int currentRow, int currentCol, int current) {
        return ((currentRow==0 &&currentCol==2)||(currentRow==1 &&currentCol==1)||(currentRow==2 &&currentCol==0))
                && board[0][2] == current
                && board[1][1] == current
                && board[2][0] == current;
    }

    public static boolean forwardDiagonal(int currentRow, int currentCol, int current) {
        return (currentRow==currentCol)
                    && board[0][0] == current
                && board[1][1] == current
                && board[2][2] == current;
    }



    public static boolean validate3inColumn(int currentCol, int current) {

        return ( board[0][currentCol] == current
                && board[1][currentCol] == current
                && board[2][currentCol] == current);

    }

    public static boolean validate3inRow(int currentRow, int current) {

        return board[currentRow][0] == current
                && board[currentRow][1] == current
                && board[currentRow][2] == current;

    }


}
