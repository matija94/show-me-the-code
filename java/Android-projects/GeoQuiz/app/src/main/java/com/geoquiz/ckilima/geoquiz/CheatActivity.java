package com.geoquiz.ckilima.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.geoquiz.ckilima.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.geoquiz.ckilima.geoquiz.extra_answer_shown";
    private static final String ALREADY_CHEATED = "com.geoquiz.ckilima.geoquiz.already_cheated";
    private static final String TAG = "CheatActivity";
    private boolean mAnswerIsTrue;
    private boolean mAnswerShown;
    private boolean mAlreadyCheated;
    private Button mShowAnswer;
    private TextView mAnswerView;
    private TextView mShowAPILevelView;



    public static Intent newIntent(Context paContext, boolean answerIsTrue) {
        Intent i = new Intent(paContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState() called!");
        savedInstanceState.putBoolean(EXTRA_ANSWER_SHOWN, mAnswerShown);
        savedInstanceState.putBoolean(EXTRA_ANSWER_IS_TRUE, mAnswerIsTrue);
        savedInstanceState.putBoolean(ALREADY_CHEATED, mAlreadyCheated);
    }


    public boolean alreadyCheated(boolean alreadyCheated, boolean answerIsTrue, TextView answerView, Button showAnswer) {
        if (alreadyCheated) {
            if (answerIsTrue) {
                answerView.setText(R.string.true_button);
            }
            else {
                answerView.setText(R.string.false_button);
            }
            answerView.setVisibility(View.VISIBLE);
            showAnswer.setVisibility(View.INVISIBLE);
        }
        if (mAnswerShown) {
            showAnswer.setVisibility(View.INVISIBLE);
        }
        return alreadyCheated;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cheat);
        mShowAPILevelView = (TextView) findViewById(R.id.show_api_level_view);
        mShowAPILevelView.setText(Integer.toString(Build.VERSION.SDK_INT));


        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAlreadyCheated = getIntent().getBooleanExtra(ALREADY_CHEATED, false);

        mAnswerView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);

        if (savedInstanceState != null) {
            mAnswerShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN);
            mAnswerIsTrue = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_TRUE);
            if (mAnswerIsTrue) {
                mAnswerView.setText(R.string.true_button);
            }
            else {
                mAnswerView.setText(R.string.false_button);
            }
            setAnswerShown(mAnswerShown);
            if (alreadyCheated(mAlreadyCheated, mAnswerIsTrue, mAnswerView, mShowAnswer)) {
                return;
            }
        }

        if (alreadyCheated(mAlreadyCheated, mAnswerIsTrue, mAnswerView, mShowAnswer)) {
            return;
        }

        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerView.setText(R.string.true_button);
                }
                else {
                    mAnswerView.setText(R.string.false_button);
                }
                mAnswerShown = true;
                setAnswerShown(mAnswerShown);
                // if users api(sdk) version isnt equal or higer to 21(lolipop) then skip animation
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswer.getWidth()/2;
                    int cy = mShowAnswer.getHeight()/2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerView.setVisibility(View.VISIBLE);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }
                else {
                    mAnswerView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }


            }
        });

    }

    private void setAnswerShown(boolean answerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, answerShown);
        setResult(RESULT_OK, data);
    }

}
