package com.android.quizeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int PROGRESS_BAR_INCREMENT = 8;
    Button trueButton;
    Button falseButton;
    TextView question;
    TextView scoreText;
    int index;
    int score;
    private Quiz[] questionArray = new Quiz[] {
            new Quiz(R.string.question_1, true),
            new Quiz(R.string.question_2, true),
            new Quiz(R.string.question_3, true),
            new Quiz(R.string.question_4, true),
            new Quiz(R.string.question_5, true),
            new Quiz(R.string.question_6, false),
            new Quiz(R.string.question_7, true),
            new Quiz(R.string.question_8, false),
            new Quiz(R.string.question_9, true),
            new Quiz(R.string.question_10, true),
            new Quiz(R.string.question_11, false),
            new Quiz(R.string.question_12, false),
            new Quiz(R.string.question_13, true)
    };
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);

        question = (TextView) findViewById(R.id.question_text_view);
        scoreText = (TextView) findViewById(R.id.score);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateQuestion();
                    }
                }, 1000);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateQuestion();
                    }
                }, 1000);
            }
        });
    }

    private void updateQuestion(){
        index = (index + 1) % questionArray.length;

        int questionId = questionArray[index].getQuestionId();
        question.setText(questionId);
        progressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        scoreText.setText("Score " + score + "/" + questionArray.length);

        if(index == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            if(score >= 9){
                alert.setTitle("You Win!");
            } else {
                alert.setTitle("Game Over!");
            }
            alert.setCancelable(false);
            alert.setMessage("Your Score " + score + " Points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
    }

    private void checkAnswer(boolean userAnswer){
        boolean correctAnswer = questionArray[index].isAnswer();
        if(correctAnswer == userAnswer){
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            score += 1;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

}