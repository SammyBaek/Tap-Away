package com.baek.sam.tapaway;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private TextView highScoreView;
    private ProgressBar timeProgressBar;
    private Button readyButton;
    private TextView currentScoreView;

    private String[] promptName;

    private int highScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayoutMap = (RelativeLayout) findViewById(R.id.RelativeMap);
        mButton = (ImageButton) findViewById(R.id.moleButton);
        setStartingPlace();
        highScoreView = (TextView) findViewById(R.id.scoreView);
        currentScoreView = (TextView) findViewById(R.id.currentScoreView);
        timeProgressBar = (ProgressBar) findViewById(R.id.timeProgressBar);
        readyButton = (Button) findViewById(R.id.readyButton);
        timeProgressBar.setMax(GAME_TIME);
        timeProgressBar.setProgress(GAME_TIME);
        promptName = new String[1];
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
                currentScoreView.setText(++score + "");
                mButton.setX((float) (Math.random() * (mRelativeLayoutMap.getWidth() - mButton.getWidth())));
                mButton.setY((float) (Math.random() * (mRelativeLayoutMap.getHeight() - mButton.getHeight())));
            }
        } else {
            Toast.makeText(getApplicationContext(), "Click Ready!", Toast.LENGTH_SHORT).show();
        }
    }


    public void startGame() {
        score = 1;
        isPlaying = true;
        currentScoreView.setText(score + "");

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

                if (score > highScore) {
                    highScore = score;
                    highScoreView.setText("High Score: " + highScore);
                }
                score = 0;
                currentScoreView.setText(score + "");
                readyButton.setVisibility(View.VISIBLE);
                readyButton.setClickable(true);
            }
        }.start();

    }

    private void getName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You got a new high score!");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                promptName[0] = input.getText().toString();
            }
        });
        builder.show();
    }

    private void setStartingPlace() {
        mButton.setX((mRelativeLayoutMap.getWidth() - mButton.getWidth()) / 2);
        mButton.setY((mRelativeLayoutMap.getHeight() - mButton.getHeight()) / 2);
        Log.d(TAG, "setStartingPlace: centered");
    }

}