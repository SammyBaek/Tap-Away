package com.baek.sam.tapaway;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private boolean isPlaying;
    private boolean isReady;
    private static final int GAME_TIME = 10000;
    private int score;
    private RelativeLayout mRelativeLayoutMap;
    private ImageButton mButton;
    private TextView scoreView;
    private ProgressBar timeProgressBar;
    private Button readyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayoutMap = (RelativeLayout) findViewById(R.id.RelativeMap);
        mButton = (ImageButton) findViewById(R.id.moleButton);
        setStartingPlace();
        scoreView = (TextView) findViewById(R.id.scoreView);
        timeProgressBar = (ProgressBar) findViewById(R.id.timeProgressBar);
        readyButton = (Button) findViewById(R.id.readyButton);
        timeProgressBar.setMax(GAME_TIME);
        timeProgressBar.setProgress(GAME_TIME);
        setStartingPlace();
    }

    public void clickReady(View v) {
        if (!isReady) {
            isReady = true;
            readyButton.setVisibility(View.INVISIBLE);
            readyButton.setClickable(false);
        }
    }

    public void clickedButton(View v) {
        if (isReady) {
            if (!isPlaying) {
                startGame();
            } else {
                scoreView.setText("Score: " + ++score);
                mButton.setX((float) (Math.random() * (mRelativeLayoutMap.getWidth() - mButton.getWidth())));
                mButton.setY((float) (Math.random() * (mRelativeLayoutMap.getHeight() - mButton.getHeight())));
            }
        } else {
            Toast.makeText(getApplicationContext(), "Click Ready!", Toast.LENGTH_SHORT).show();
        }
    }


    public void startGame() {
        score = 0;
        isPlaying = true;
        scoreView.setText("Score: " + score);
        new CountDownTimer(GAME_TIME, 10) {

            @Override
            public void onTick(long millisUntilFinished) {
//                timeView.setText(String.format("Time left: %.2f", (1.0 * millisUntilFinished / 1000)));
                timeProgressBar.setProgress((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Finished Game!", Toast.LENGTH_LONG).show();
                isPlaying = false;
                setStartingPlace();
                timeProgressBar.setProgress(GAME_TIME);
                mButton.setVisibility(View.INVISIBLE);
                mButton.setVisibility(View.VISIBLE);

                isReady = false;
                readyButton.setClickable(true);
                readyButton.setVisibility(View.VISIBLE);
            }
        }.start();

    }

    private void setStartingPlace() {
        mButton.setX((mRelativeLayoutMap.getWidth() - mButton.getWidth()) / 2);
        mButton.setY((mRelativeLayoutMap.getHeight() - mButton.getHeight()) / 2);
        Log.d(TAG, "setStartingPlace: centered");
    }

}