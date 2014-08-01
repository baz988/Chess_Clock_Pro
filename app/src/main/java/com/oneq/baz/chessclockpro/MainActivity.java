package com.oneq.baz.chessclockpro;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView textFieldA;
    GameTimer gameTimerA;
    long gameStartTime = 10000;
    long realTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.button);
        Button pauseButton = (Button) findViewById(R.id.button2);

        textFieldA = (TextView) findViewById(R.id.textViewA);
        textFieldA.setText(Long.toString(gameStartTime/1000));

        //gameTimerA = new GameTimer(gameStartTime);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameTimerA = new GameTimer(gameStartTime);
                gameTimerA.start();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameStartTime= gameTimerA.pause();
                textFieldA.setText("Clock paused at: " + gameStartTime);
            }
        });
    }

    class GameTimer extends CountDownTimer{

        public GameTimer(long gameStartTime){
            super(gameStartTime, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished){
            realTime = millisUntilFinished/1000;
            textFieldA.setText(Long.toString(realTime));
        }

        @Override
        public void onFinish(){
            textFieldA.setText("YOU LOSE");
        }

        public long pause(){
            cancel();
            return realTime;
        }
    }
}