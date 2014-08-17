package com.oneq.baz.chessclockpro;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    Player white;
    Player black;
    ImageButton pauseButton;
    ImageButton resetButton;
    MediaPlayer mediaPlayer;
    long TIME_CONTROL = 300000;
    int INTERVAL = 1000;
    boolean outOfTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup white player
        white = new Player();
        white.gameTimer = new GameTimer(white);
        white.textField = (TextView) findViewById(R.id.textViewA);
        white.textField.setText(convertTime(TIME_CONTROL));

        //setup black player
        black = new Player();
        black.gameTimer = new GameTimer(black);
        black.textField = (TextView) findViewById(R.id.textViewB);
        black.textField.setText(convertTime(TIME_CONTROL));


        //pause and reset buttons
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        resetButton = (ImageButton) findViewById(R.id.resetButton);

        //handle a white click
        //the .isPaused makes sure the clicked players time is running, and then starts the opponents timer
        //the move counter is to make sure the other players class has been created and set to the Time_Control
            //for proper object reference and display
        white.textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!white.isPaused) {
                    timerLogic(black);
                    if (white.moveCounter >1) {
                        timerPause(white);
                    }
                    white.isPaused=true;
                }
            }
        });

        //handle a black click
        //the .isPaused makes sure the clicked players time is running, and then starts the opponents timer
        //the move counter is to make sure the other players class has been created and set to the Time_Control
            //for proper object reference and display
        black.textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!black.isPaused) {
                    timerLogic(white);
                    if (black.moveCounter >1) {
                        timerPause(black);
                    }
                    black.isPaused=true;
                }
            }
        });

        //pause button will reset both clocks to whatever time is currently displayed
        //the move counter is to make sure the clock has been created with the Time_Control for proper object reference and display
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!outOfTime) {
                    if (white.moveCounter > 1) {
                        timerPause(white);
                        white.isPaused = false;
                    }
                    if (black.moveCounter > 1) {
                        timerPause(black);
                        black.isPaused = false;
                    }
                }
            }
        });

        //reset button will set both the clocks to Time_Control
        /**
         * Need to add a moveCounter reset
         */
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                white.realTime = TIME_CONTROL;
                timerPause(white);

                black.realTime = TIME_CONTROL;
                timerPause(black);

                if(outOfTime){
                    mediaPlayer.release();
                    mediaPlayer = null;
                    outOfTime=false;
                }

                white.isPaused = false;
                black.isPaused = false;

            }
        });

    }

    //METHODS

    public void timerLogic(Player currentPlayer) {
        currentPlayer.gameTimer = new GameTimer(currentPlayer);
        currentPlayer.gameTimer.start();
        currentPlayer.isPaused = false;
    }

    public void timerPause(Player currentPlayer){
        currentPlayer.gameStartTime = currentPlayer.gameTimer.pause();
        currentPlayer.textField.setText("" + convertTime(currentPlayer.gameStartTime));
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

    //CLASSES

    class GameTimer extends CountDownTimer{

        Player currentPlayer;

        public GameTimer(Player player){
            super(player.gameStartTime, INTERVAL);
            currentPlayer = player;
            player.moveCounter++;
        }

        @Override
        public void onTick(long millisUntilFinished){
            currentPlayer.realTime = millisUntilFinished;
            currentPlayer.textField.setText(convertTime(currentPlayer.realTime));
        }

        @Override
        public void onFinish(){
            currentPlayer.textField.setText(R.string.outOfTime);
            white.isPaused=true;
            black.isPaused=true;
            outOfTime=true;


            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.beep3);
            mediaPlayer.start();
        }

        public long pause(){
            cancel();
            //currentPlayer.isPaused=true;
            return currentPlayer.realTime;
        }
    }

    class Player{
        public TextView textField;
        public GameTimer gameTimer;
        long gameStartTime = TIME_CONTROL;
        long realTime = 0;
        boolean isPaused = false;
        int moveCounter=0;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();

        if (white.moveCounter > 1) {
            timerPause(white);
            white.isPaused = false;
        }
        if (black.moveCounter > 1) {
            timerPause(black);
            black.isPaused = false;
        }
    }
}


