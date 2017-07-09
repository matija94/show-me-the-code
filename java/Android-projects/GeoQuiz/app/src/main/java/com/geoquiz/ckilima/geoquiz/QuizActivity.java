package com.geoquiz.ckilima.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {


    private Button mcheatButton;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private boolean mIsCheater;
    private HashMap<Integer, Boolean> shownAnswers = new HashMap<>();

    private static final String ALREADY_CHEATED = "com.geoquiz.ckilima.geoquiz.already_cheated";
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX="index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String EXTRA_ANSWER_SHOWN = "com.geoquiz.ckilima.geoquiz.extra_answer_shown";
    private static final String ANSWERS_SHOWN = "com.geoquiz.ckilima.geoquiz.answers_shown";
    private static final String SCORE = "com.geoquiz.ckilima.geoquiz.score";

    private Question[] questions = {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private Score score = new Score();

    private int mCurrentIndex = 0;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState() called!");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(EXTRA_ANSWER_SHOWN, mIsCheater);
        savedInstanceState.putSerializable(ANSWERS_SHOWN, shownAnswers);
        savedInstanceState.putSerializable(SCORE, this.score);
    }



    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called!");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called!");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called!");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            shownAnswers.put(mCurrentIndex, mIsCheater);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called!");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN);
            shownAnswers = (HashMap<Integer, Boolean>) savedInstanceState.getSerializable(ANSWERS_SHOWN);
            this.score = (Score) savedInstanceState.getSerializable(SCORE);
        }

        mScoreTextView = (TextView) findViewById(R.id.score_text_view);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mcheatButton = (Button) findViewById(R.id.cheat_button);
        mcheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = questions[mCurrentIndex].getAnswer();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                if (shownAnswers.get(mCurrentIndex) != null) {
                    i.putExtra(ALREADY_CHEATED, true);
                }
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateScore();
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateScore();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % questions.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = questions.length-1;
                }
                else{
                    mCurrentIndex--;
                }
                updateQuestion();
            }
        });

        updateQuestion();
        updateScore();
    }

    private  void updateQuestion() {
        int question = questions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void updateScore() {
        int score = this.score.getValue();
        mScoreTextView.setText(R.string.score);
        mScoreTextView.append(Integer.toString(score));
    }


    private void checkAnswer(boolean userPressed) {
        boolean answer = questions[mCurrentIndex].getAnswer();

        int message;
        // if user view the answer in cheatActivity for given question report that back to him
        if (shownAnswers.get(mCurrentIndex) != null && shownAnswers.get(mCurrentIndex)) {
            message = R.string.judgment_toast;
        }
        else {
            if (userPressed == answer) {
                message = R.string.correct_toast;
                this.score.incrementScore();
            }
            else {
                message = R.string.incorrect_toast;
                this.score.decrementScore();
            }
        }

        Toast.makeText(QuizActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
