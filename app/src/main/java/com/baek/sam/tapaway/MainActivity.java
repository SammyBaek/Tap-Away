package com.baek.sam.tapaway;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private boolean isPlaying;
    private static final int GAME_TIME = 10000;
    private int score;
    private RelativeLayout mRelativeLayoutMap;
    private ImageButton mButton;
    private TextView scoreView;
    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayoutMap = (RelativeLayout) findViewById(R.id.RelativeMap);
        mButton = (ImageButton) findViewById(R.id.moleButton);
        scoreView = (TextView) findViewById(R.id.scoreView);
        timeView = (TextView) findViewById(R.id.timerView);

        setStartingPlace();
    }

    public void clickedButton(View v) {
        if (!isPlaying) {
            startGame();
        } else {
            scoreView.setText("Score: " + ++score);
            mButton.setX((float) (Math.random() * (mRelativeLayoutMap.getWidth() - mButton.getWidth())));
            mButton.setY((float) (Math.random() * (mRelativeLayoutMap.getHeight() - mButton.getHeight())));
        }
    }


    public void startGame() {
        score = 0;
        isPlaying = true;
        scoreView.setText("Score: " + score);
        new CountDownTimer(GAME_TIME, 10) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeView.setText(String.format("Time left: %.2f", (1.0 * millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                timeView.setText("Finished!");
                Toast.makeText(getApplicationContext(), "Finished Game!", Toast.LENGTH_LONG).show();
                isPlaying = false;
                mButton.setVisibility(View.VISIBLE);
                setStartingPlace();
            }
        }.start();

    }

    private void setStartingPlace() {
        mButton.setX((mRelativeLayoutMap.getWidth() - mButton.getWidth()) / 2);
        mButton.setY((mRelativeLayoutMap.getHeight() - mButton.getHeight()) / 2);
        Log.d(TAG, "setStartingPlace: centered");
    }

}