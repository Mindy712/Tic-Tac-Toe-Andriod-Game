package com.example.tic_tac_toe.models;

import android.widget.Button;

import com.google.gson.Gson;

import java.util.Arrays;

public class TicTacToe
{
    private Button[][] board = new Button[3][3];
    private boolean player;
    private boolean gameEnd = false;
    private int mNumberOfGamesPlayed = 0;
    private final int[] mArrayPlayerWinCount;

    //TODO: Saved state
    public TicTacToe(Button[][] state)
    {
        //TODO: pass in old array
        mArrayPlayerWinCount = new int[2];
    }


    public TicTacToe()
    {
        mArrayPlayerWinCount = new int[2];
        startGame ();
    }

    public void startGame ()
    {
        player = true;
        mNumberOfGamesPlayed++;
    }

    public void takeTurn (Button button)
    {
        while(!gameEnd) {
            char symbol;
            if(player) {
                symbol = 'X';
            } else {
                symbol = 'O';
            }

            //TODO:get user's move in terms of row and col
            int row = 0; //place holder
            int col = 0; //place holder
            while (!board[row][col].getText().equals("")) {
                //TODO:get user's move in terms of row and col
                row = 0; //place holder
                col = 0; //place holder
            }
            board[row][col].setText(symbol);

            //check if player won
            if(playerWon(symbol)) {
                //TODO:message of winner
                gameEnd = true;
            } else if (boardFull()){
                //TODO:message of cats game
                gameEnd = true;
            } else {
                player = !player;
            }

        }
    }

    private boolean boardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean playerWon(char symbol) {
        for(int i = 0; i < 3; i++) {
            if(board[i][0].getText().equals(symbol) && board[i][1].getText().equals(symbol) && board[i][2].getText().equals(symbol)) {
                return true;
            }
        }
        for(int j = 0; j < 3; j++) {
            if(board[0][j].getText().equals(symbol) && board[1][j].getText().equals(symbol) && board[2][j].getText().equals(symbol)) {
                return true;
            }
        }
        if(board[0][0].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][2].getText().equals(symbol)) {
            return true;
        }
        if(board[2][0].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[0][2].getText().equals(symbol)) {
            return true;
        }

        return false;
    }


    private void updateGameWinStatisticsIfGameHasJustEnded() {
        if (gameEnd)
            mArrayPlayerWinCount[getWinningPlayerNumber()-1]++;  // player 1 or 2 == element 0 or 1
    }


    public int getNumberOfWinsForPlayer (int playerNumber)
    {
        if (playerNumber < 1 || playerNumber > 2)
            throw new IllegalArgumentException("Player number must be between 1 and "
                    + 2 + ".");
        return mArrayPlayerWinCount[playerNumber-1];
    }

    public void resetStatistics ()
    {
        mNumberOfGamesPlayed= gameEnd ? 0 : 1;
        Arrays.fill(mArrayPlayerWinCount, 0);
    }

    public int getWinningPlayerNumberIfGameOver()
    {
        if (!gameEnd)
            throw new IllegalStateException("No winner yet; the game is still ongoing.");
        return getWinningPlayerNumber();
    }

    private int getWinningPlayerNumber() {
        return (player) ? 1 : 2;
    }

    public int getCurrentPlayerNumber ()
    {
        return player ? 1 : 2;
    }

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
    }}