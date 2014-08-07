package com.oneq.baz.chessclockpro;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

    Player playerA;
    Player playerB;
    int moveCounter = 0;
    long TIME_CONTROL = 300000;
    int INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerA = new Player();
        playerB = new Player();

        //Toast.makeText(this, time, Toast.LENGTH_LONG).show();

        playerA.textField = (TextView) findViewById(R.id.textViewA);
        playerA.textField.setText(convertTime(TIME_CONTROL));

        playerB.textField = (TextView) findViewById(R.id.textViewB);
        playerB.textField.setText(convertTime(TIME_CONTROL));

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

    public String convertTime(long time){
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);

        String formatted=null;

        if(hours>0 && hours<=9){
            formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
        }
        else if (hours==0 && minutes>0) {
            if (minutes>9) {
                formatted = String.format("%02d:%02d", minutes, seconds);
            }
            else if (minutes>0 && minutes<=9) {
                formatted = String.format("%d:%02d", minutes, seconds);
            }
        }
        else if (hours==0 && minutes==0 && seconds>0){
            if(seconds>9){
                formatted = String.format("%02d", seconds);
            }
            else if(seconds>0 && seconds<=9) {
                formatted = String.format("%d", seconds);
            }
        }
        else{
            formatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        return formatted;
    }

    public void timerLogic(Player currentPlayer) {
        currentPlayer.gameTimer = new GameTimer(currentPlayer);
        currentPlayer.gameTimer.start();
        currentPlayer.isPaused = false;
    }

    public void timerPause(Player currentPlayer){
        currentPlayer.gameStartTime = currentPlayer.gameTimer.pause();
        currentPlayer.textField.setText("" + convertTime(currentPlayer.gameStartTime));
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
            currentPlayer.textField.setText(convertTime(currentPlayer.realTime));
        }

        @Override
        public void onFinish(){
            currentPlayer.textField.setText("OUT OF TIME");
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
        long gameStartTime = TIME_CONTROL;
        long realTime = 0;
        boolean isPaused = false;
    }
}


