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

//    public void takeTurnGame (Button button, View view)
//    {
//        String buttonID = button.getResources().getResourceEntryName(button.getId());
//        char buttonIDArray[] = buttonID.toCharArray();
//        while(!gameEnd) {
//            char symbol;
//            if(player) {
//                symbol = 'X';
//            } else {
//                symbol = 'O';
//            }
//
//            //TODO:get user's move in terms of row and col
//            int row = buttonIDArray[buttonIDArray.length - 2]; //place holder
//            int col = buttonIDArray[buttonIDArray.length - 1]; //place holder
////            while (!board[row][col].getText().equals("")) {
////                //TODO:get user's move in terms of row and col
////                row = 0; //place holder
////                col = 0; //place holder
////            }
//            Button player_button = (Button) view.findViewById(R.id.(buttonID));
//            if(button.getText().equals("")) {
//                button.setText(symbol);
//                board[row][col] = symbol;
//            }
//            else{
//                System.out.println("don't set");
//            }
//            //check if player won
//            if(playerWon(symbol)) {
//                //TODO:message of winner
//                gameEnd = true;
//            } else if (boardFull()){
//                //TODO:message of cats game
//                gameEnd = true;
//            } else {
//                player = !player;
//            }
//
//        }
//    }

    public boolean canTakeTurn (Pair<Integer, Integer> pairRowCol)
    {
        int row = pairRowCol.first;
        int col = pairRowCol.second;

        if (row==-1 || col==-1)
            throw new IllegalArgumentException("Unknown Space Clicked");

        if(board[row][col] != 'X' || board[row][col] != 'O') {
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
            return true;
        }
        else if (boardFull()) {
            return true;
        }
        return false;
    }

    private boolean boardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean playerWon(char symbol) {
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
        if (isGameOver())
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
        mNumberOfGamesPlayed= isGameOver() ? 0 : 1;
        Arrays.fill(mArrayPlayerWinCount, 0);
    }

    public int getWinningPlayerNumberIfGameOver()
    {
        if (!isGameOver())
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
    }

    public String getRules() {
        return "Players take turns as X and Y. Try to get three Xs or Ys in a row to win.";
    }
}