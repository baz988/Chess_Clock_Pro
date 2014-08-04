package com.oneq.baz.chessclockpro;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView textFieldA;
    GameTimer gameTimerA;
    long gameStartTime = 10000;
    long realTime = 0;
    boolean paused = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textFieldA = (TextView) findViewById(R.id.textViewA);
        textFieldA.setText(Long.toString(gameStartTime/1000));

        textFieldA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(paused){
                    gameTimerA = new GameTimer();
                    gameTimerA.start();
                    paused=false;
                }

                else{ //currently not paused
                    gameStartTime= gameTimerA.pause();
                    textFieldA.setText("Clock paused at: " + gameStartTime/1000);
                }
            }
        });
    }

    class GameTimer extends CountDownTimer{

        public GameTimer(){
            super(gameStartTime, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished){
            realTime = millisUntilFinished;
            textFieldA.setText(Long.toString(realTime/1000));
        }

        @Override
        public void onFinish(){
            textFieldA.setText("YOU LOSE");
        }

        public long pause(){
            cancel();
            paused=true;
            return realTime;
        }
    }
}