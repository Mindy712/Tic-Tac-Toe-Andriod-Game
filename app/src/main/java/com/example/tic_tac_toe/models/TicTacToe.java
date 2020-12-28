package com.example.tic_tac_toe.models;

import android.util.Pair;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.Arrays;

public class TicTacToe
{
    private char[][] board = new char[3][3];
    private Button[][] mBtnBoard;

    private boolean player;
//    private boolean gameEnd = false;
    private int mNumberOfGamesPlayed = 0;
    private final int[] mArrayPlayerWinCount;

//    //TODO: Saved state
//    public TicTacToe(Button[][] state)
//    {
//        //TODO: pass in old array
//        mArrayPlayerWinCount = new int[2];
//    }
//
//
//    public TicTacToe()
//    {
//        mArrayPlayerWinCount = new int[2];
//        startGame();
//    }

    public TicTacToe(Button[][] btnBoard)
    {
        mBtnBoard = btnBoard;
        mArrayPlayerWinCount = new int[2];
        //startGame(); // makes the button click "freeze"
    }

    public void startGame()
    {
        player = true;
        mNumberOfGamesPlayed++;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean canTakeTurn (Pair<Integer, Integer> pairRowCol)
    {
        int row = pairRowCol.first;
        int col = pairRowCol.second;

        if (row==-1 || col==-1)
            throw new IllegalArgumentException("Unknown Space Clicked");

        if(board[row][col] != 'X' && board[row][col] != 'O') {
            board[row][col] = getCurrentPlayerSymbol();

            if (!isGameOver()) {
                player = !player;
            }
            return true;
        }
        else{
            System.out.println("don't set");
        }
        return false;
    }

    public char getCurrentPlayerSymbol() {
        return player ? 'X' : 'O';
    }

    public boolean isGameOver() {

        if (playerWon(getCurrentPlayerSymbol())) {
            updateGameWinStatisticsIfGameHasJustEnded();
            return true;
        }
        else if (boardFull()) {
            return true;
        }
        return false;
    }

    public boolean boardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

   public boolean playerWon(char symbol) {
        for(int i = 0; i < 3; i++) {
            if(board[i][0] == symbol && board[i][1] == symbol  && board[i][2] == symbol ) {
                return true;
            }
        }
        for(int j = 0; j < 3; j++) {
            if(board[0][j] == symbol  && board[1][j] == symbol  && board[2][j] == symbol ) {
                return true;
            }
        }
        if(board[0][0] == symbol  && board[1][1] == symbol  && board[2][2] == symbol ) {
            return true;
        }
        if(board[2][0] == symbol  && board[1][1] == symbol  && board[0][2] == symbol ) {
            return true;
        }

        return false;
    }


    private void updateGameWinStatisticsIfGameHasJustEnded() {
//        if (isGameOver())
        if(getWinningPlayerSymbol() == 'X') {
            mArrayPlayerWinCount[0]++;
        } else {
            mArrayPlayerWinCount[1]++;
        }

    }


    public int getNumberOfWinsForPlayer (char playerSymbol)
    {
        if (playerSymbol != 'X' || playerSymbol != 'O')
            throw new IllegalArgumentException("Player number must be X or O.");
        if(playerSymbol == 'X') {
            return mArrayPlayerWinCount[0];
        } else {
            return mArrayPlayerWinCount[1];
        }
    }

   //    public int getWinningPlayerNumberIfGameOver()
//    {
//        if (!isGameOver())
//            throw new IllegalStateException("No winner yet; the game is still ongoing.");
//        return getWinningPlayerNumber();
//    }

    private char getWinningPlayerSymbol() {
        return (player) ? 'X' : 'O';
    }

//    public int getCurrentPlayerNumber () { return player ? 1 : 2; }

    public int getNumberOfGamesPlayed ()
    {
        return mNumberOfGamesPlayed;
    }

    /**
     * Reverses the game object's serialization as a String
     * back to a TicTacToe game object
     *
     * @param json The serialized String of the game object
     * @return The game object
     */
    public static TicTacToe getGameFromJSON (String json)
    {
        Gson gson = new Gson ();
        return gson.fromJson (json, TicTacToe.class);
    }

    /**
     * Serializes the game object to a JSON-formatted String
     *
     * @param obj Game Object to serialize
     * @return JSON-formatted String
     */
    public static String getJSONFromGame (TicTacToe obj)
    {
        Gson gson = new Gson ();
        return gson.toJson (obj);
    }

    public String getJSONFromCurrentGame()
    {
        return getJSONFromGame(this);
    }

    public String getRules() {
        return "Players take turns as X and Y. Try to get three Xs or Ys in a row to win.";
    }
}