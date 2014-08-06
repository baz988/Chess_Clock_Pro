package com.oneq.baz.chessclockpro;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    Player playerA;
    Player playerB;
    int moveCounter = 0;
    int INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerA = new Player();
        playerB = new Player();

        playerA.textField = (TextView) findViewById(R.id.textViewA);
        playerA.textField.setText(Long.toString(playerA.gameStartTime / INTERVAL));

        playerB.textField = (TextView) findViewById(R.id.textViewB);
        playerB.textField.setText(Long.toString(playerB.gameStartTime / INTERVAL));

        playerA.textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playerA.isPaused) {
                    timerLogic(playerB);
                    if (moveCounter >= 2) {
                        timerPause(playerA);
                    }
                    playerA.isPaused=true;
                }
            }
        });

        playerB.textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playerB.isPaused) {
                    timerLogic(playerA);
                    if (moveCounter >= 2) {
                        timerPause(playerB);
                    }
                    playerB.isPaused=true;
                }
            }
        });
    }

    public void timerLogic(Player currentPlayer) {
        currentPlayer.gameTimer = new GameTimer(currentPlayer);
        currentPlayer.gameTimer.start();
        currentPlayer.isPaused = false;

    }

    public void timerPause(Player currentPlayer){

        currentPlayer.gameStartTime = currentPlayer.gameTimer.pause();
        currentPlayer.textField.setText("Clock paused at: " + currentPlayer.gameStartTime / INTERVAL);
    }

    class GameTimer extends CountDownTimer{

        Player currentPlayer;

        public GameTimer(Player player){
            super(player.gameStartTime, INTERVAL);
            currentPlayer = player;
            moveCounter++;
        }

        @Override

        public void onTick(long millisUntilFinished){
            currentPlayer.realTime = millisUntilFinished;
            currentPlayer.textField.setText(Long.toString(currentPlayer.realTime / INTERVAL));
        }

        @Override
        public void onFinish(){
            currentPlayer.textField.setText("YOU LOSE");
        }

        public long pause(){
            cancel();
            currentPlayer.isPaused=true;
            return currentPlayer.realTime;
        }
    }

    class Player{
        public TextView textField;
        public GameTimer gameTimer;
        long gameStartTime= 10000;
        long realTime = 0;
        boolean isPaused = false;

    }
}


