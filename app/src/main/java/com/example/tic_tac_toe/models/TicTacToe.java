package com.example.tic_tac_toe.models;

import com.google.gson.Gson;
import java.util.Arrays;

public class TicTacToe
{
    public char[][] getBoard() {
        return board;
    }

    public boolean isPlayer() {
        return player;
    }

    private char[][] board = new char[3][3];

    private boolean player;
    private int mNumberOfGamesPlayed = 0;
    private final int[] mArrayPlayerWinCount;

    public TicTacToe()
    {
        mArrayPlayerWinCount = new int[2];
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

    public boolean attemptTakeTurn(int row, int col)
    {
        if (row < 0 || col < 0 || row >= board.length || col >= board[row].length)
            throw new IllegalArgumentException("Unknown Space Clicked");

        if(board[row][col] != 'X' && board[row][col] != 'O') {
            board[row][col] = getCurrentPlayerSymbol();

            if (!isGameOver()) {
                player = !player;
            }
            return true;
        }
        else {
            System.out.println("don't set");
        }
        return false;
    }

    public char getCurrentPlayerSymbol() {
        return player ? 'X' : 'O';
    }

    public boolean isGameOver() {

        if (playerWon(getCurrentPlayerSymbol())) {
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


    public void updateGameWinStatisticsIfGameHasJustEnded() {
        if (playerWon(getCurrentPlayerSymbol())) {
            if (getWinningPlayerSymbol() == 'X') {
                mArrayPlayerWinCount[0]++;
            } else {
                mArrayPlayerWinCount[1]++;
            }
        }
    }


    public int getNumberOfWinsForPlayer (char playerSymbol)
    {
        if (playerSymbol != 'X' && playerSymbol != 'O')
            throw new IllegalArgumentException("Player number must be X or O.");
        if(playerSymbol == 'X') {
            return mArrayPlayerWinCount[0];
        } else {
            return mArrayPlayerWinCount[1];
        }
    }

    public void undoLastMove(int row, int col) {
        if (row < 0 || col < 0 || row >= board.length || col >= board[row].length)
            throw new IllegalArgumentException("Unknown Space Clicked");

        player = !player;
        board[row][col] = ' ';
    }

    private char getWinningPlayerSymbol() {
        return (player) ? 'X' : 'O';
    }

    public int getNumberOfGamesPlayed ()
    {
        return mNumberOfGamesPlayed;
    }

    public void resetNumberOfGamesPlayed() { mNumberOfGamesPlayed = 0; }

    public void resetPlayerWins() { Arrays.fill(mArrayPlayerWinCount, 0); }

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