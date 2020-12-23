package com.example.tic_tac_toe.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.tic_tac_toe.R;
import com.example.tic_tac_toe.models.TicTacToe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import static com.example.tic_tac_toe.lib.Utils.showInfoDialog;

public class MainActivity extends AppCompatActivity {
    private Snackbar mSnackBar;
    private TicTacToe mGame;
    private TextView mTvStatusBarCurrentPlayer;
    private Button[][] mBtnBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBtnBoard = new Button[][]
                {
                        {findViewById(R.id.button_00), findViewById(R.id.button_01), findViewById(R.id.button_02)},
                        {findViewById(R.id.button_10), findViewById(R.id.button_11), findViewById(R.id.button_12)},
                        {findViewById(R.id.button_20), findViewById(R.id.button_21), findViewById(R.id.button_22)}
                };
        mTvStatusBarCurrentPlayer = findViewById(R.id.tv_status_current_player);
        setupFAB();

        mBtnBoard = new Button[][]
                {
                        {findViewById(R.id.button_00), findViewById(R.id.button_01), findViewById(R.id.button_02)},
                        {findViewById(R.id.button_10), findViewById(R.id.button_11), findViewById(R.id.button_12)},
                        {findViewById(R.id.button_20), findViewById(R.id.button_21), findViewById(R.id.button_22)}
                };
        mSnackBar =
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.welcome),
                        Snackbar.LENGTH_LONG);
        startFirstGame();

    }

    private void startFirstGame() {
        mGame = new TicTacToe(mBtnBoard);
        updateUI();
    }
    public void takeTurn(View view){
        Button button = (Button) view;
        // get from the model:
        // see if the game is active (not over) - if yes, tell user game already ended; else...
        // assuming the game is active, take the turn
        // 1. get current player --> for text to set on button and if game will end now
        // 2. query the model if the space x,y is available
        // 3a. if it is then tell model to take turn with space x,y
        //     --> model will update char array and current player
        //     if the turn was successful then
        //      check if the game is over
        // 3b. if it's not available then tell the user that was an invalid choice
        //mGame.takeTurnGame(button);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_new_game) {
            startNextNewGame();
            return true;
        } else if (itemId == R.id.action_statistics) {
            showStatistics();
            return true;
        } else if (itemId == R.id.action_settings) {
            showSettings();
            return true;
        } else if (itemId == R.id.action_about) {
            showAbout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoDialog(MainActivity.this,
                        getString(R.string.info_title), mGame.getRules());
            }
        });
    }

    private void showSettings() {
        dismissSnackBarIfShown();
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(intent, 1);
    }



    private void showStatistics() {
        dismissSnackBarIfShown();
        Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
        intent.putExtra("GAME", mGame.getJSONFromCurrentGame());
        startActivity(intent);
    }


    private void startNextNewGame() {
        mGame.startGame();
        updateUI();
    }

    private void updateUI() {
        dismissSnackBarIfShown();
        mTvStatusBarCurrentPlayer.setText(
                String.format(Locale.getDefault(), "%s: %s",
                        getString(R.string.current_player),
                        mGame.getCurrentPlayer()));
    }

    private void showAbout() {
        dismissSnackBarIfShown();
        showInfoDialog(MainActivity.this, "About Tic Tac Toe",
                "Game created by:\n" +
                        "Ester Agishtein\n" +
                        "Mindy Gottlieb\n" +
                        "Shoshana Weinfeld\n" +
                        "for Android programming term project.");
    }

    private void dismissSnackBarIfShown() {
        if (mSnackBar.isShown()) {
            mSnackBar.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}