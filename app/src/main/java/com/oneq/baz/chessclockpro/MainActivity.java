package com.oneq.baz.chessclockpro;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    Player playerA;
    Player classPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerA = new Player();

        playerA.textField = (TextView) findViewById(R.id.textViewA);
        playerA.textField.setText(Long.toString(playerA.gameStartTime/1000));

        playerA.textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerA.isPaused) {
                    playerA.gameTimer = new GameTimer(playerA);
                    playerA.gameTimer.start();
                    playerA.isPaused = false;
                } else { //currently not paused
                    playerA.gameStartTime = playerA.gameTimer.pause();
                    playerA.textField.setText("Clock paused at: " + playerA.gameStartTime / 1000);
                }
            }
        });
    }

    class GameTimer extends CountDownTimer{

        public GameTimer(Player player){
            super(player.gameStartTime, 1000);
            classPlayer = player;
        }

        @Override
        public void onTick(long millisUntilFinished){
            classPlayer.realTime = millisUntilFinished;
            classPlayer.textField.setText(Long.toString(classPlayer.realTime / 1000));
        }

        @Override
        public void onFinish(){
            classPlayer.textField.setText("YOU LOSE");
        }

        public long pause(){
            cancel();
            classPlayer.isPaused=true;
            return classPlayer.realTime;
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