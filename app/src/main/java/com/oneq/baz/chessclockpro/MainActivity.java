package com.oneq.baz.chessclockpro;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    Player playerA;
    Player playerB;
    //Player currentPlayer;
    int INTERVAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerA = new Player();
        playerB = new Player();

        playerA.textField = (TextView) findViewById(R.id.textViewA);
        playerA.textField.setText(Long.toString(playerA.gameStartTime/INTERVAL));
        //playerA.gameTimer = new GameTimer(playerA);

        playerB.textField = (TextView) findViewById(R.id.textViewB);
        playerB.textField.setText(Long.toString(playerB.gameStartTime/INTERVAL));
        //playerB.gameTimer = new GameTimer(playerB);

        playerA.textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //timerLogic(playerA);
                if (playerA.isPaused) {
                    playerA.gameTimer = new GameTimer(playerA);
                    playerA.gameTimer.start();
                    playerA.isPaused = false;
                } else { //currently not paused
                    playerA.gameStartTime = playerA.gameTimer.pause();
                    playerA.textField.setText("Clock paused at: " + playerA.gameStartTime / INTERVAL);
                }
            }
        });

        playerB.textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //timerLogic(playerB);
                if (playerB.isPaused) {
                    playerB.gameTimer = new GameTimer(playerB);
                    playerB.gameTimer.start();
                    playerB.isPaused = false;
                } else { //currently not paused
                    playerB.gameStartTime = playerB.gameTimer.pause();

                    playerB.textField.setText("Clock paused at: " + playerB.gameStartTime / INTERVAL);
                }
            }
        });
    }

    public void timerLogic(Player currentPlayer) {
        if (currentPlayer.isPaused) {
            currentPlayer.gameTimer = new GameTimer(currentPlayer);
            currentPlayer.gameTimer.start();
            currentPlayer.isPaused = false;
        } else { //currently not paused
            currentPlayer.gameStartTime = currentPlayer.gameTimer.pause();
            currentPlayer.textField.setText("Clock paused at: " + currentPlayer.gameStartTime / INTERVAL);
        }
    }

    class GameTimer extends CountDownTimer{

        Player currentPlayer;

        public GameTimer(Player player){
            super(player.gameStartTime, INTERVAL);
            currentPlayer = player;
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
        boolean isPaused = true;
    }
}


