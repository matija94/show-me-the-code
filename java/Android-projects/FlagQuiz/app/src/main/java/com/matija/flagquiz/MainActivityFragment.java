package com.matija.flagquiz;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "FlagQuiz Activity";

    private static final int FLAGS_IN_QUIZ = 10;

    private List<String> fileNameList; // flag file names
    private List<String> quizCountriesList; //countries in current quiz
    private Set<String> regionsSet; // world regions in current quizcorrectAnswer = nextImage;
    private String correctAnswer; // correct country for current flagcorrectAnswer = nextImage;
    private int totalGuesses; // num of guesses user made
    private int correctAnswers;// num of correct guesses
    private int guessRows; // num of rows displaying guess Buttons
    private SecureRandom random; //used to randomize the quiz
    private Handler handler; // used to delay loading next flag
    private Animation shakeAnimation; // animation for incorrect guess

    private LinearLayout quizLinearLayout; // layout that contains the quiz
    private TextView questionNumberTextView; // shows current question #
    private ImageView flagImageView; // displays a flag
    private LinearLayout[] guessLinearLayouts; // rows of answer Buttons
    private TextView answerTextView; // displays correct answer


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        fileNameList = new ArrayList<>();
        quizCountriesList = new ArrayList<>();
        random = new SecureRandom();
        handler = new Handler();

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3);

        quizLinearLayout = (LinearLayout) view.findViewById(R.id.quizLinearLayout);
        questionNumberTextView = (TextView) view.findViewById(R.id.questionNumberTextView);
        flagImageView = (ImageView) view.findViewById(R.id.flagImageView);
        answerTextView = (TextView) view.findViewById(R.id.answerTextView);

        guessLinearLayouts = new LinearLayout[4];
        for (int i=1; i<=4; i++) {
            int rowLayout = R.id.row1LinearLayout;
            switch (i) {
                case 1:
                    break;
                case 2:
                    rowLayout = R.id.row2LinearLayout;
                    break;
                case 3:
                    rowLayout = R.id.row3LinearLayout;
                    break;
                case 4:
                    rowLayout = R.id.row4LinearLayout;
                    break;
            }
            guessLinearLayouts[i-1] = (LinearLayout) view.findViewById(rowLayout);
        }


        // configure listeners for guess Buttons
        for (LinearLayout guessLinearLayout : guessLinearLayouts) {
            for (int column =0; column<guessLinearLayout.getChildCount(); column++) {
                Button guessButton = (Button) guessLinearLayout.getChildAt(column);
                guessButton.setOnClickListener(guessButtonListener);
            }
        }

        questionNumberTextView.setText(getString(R.string.question, 1, FLAGS_IN_QUIZ));
        return view;
    }

    public void updateGuessRows(SharedPreferences preferences) {
        String guessButtons = preferences.getString(MainActivity.CHOICES, null);
        // each row contains 2 guess buttons
        guessRows = Integer.parseInt(guessButtons)/2;

        for (LinearLayout layout : guessLinearLayouts) {
            layout.setVisibility(View.GONE);
        }

        for (int i =0; i<guessRows; i++) {
            guessLinearLayouts[i].setVisibility(View.VISIBLE);
        }
    }

    public void updateRegions(SharedPreferences preferences) {
        regionsSet = preferences.getStringSet(MainActivity.REGIONS, null);
    }

    public void resetQuiz() {
        AssetManager assets = getActivity().getAssets();
        fileNameList.clear();

        try {
            for (String region : regionsSet) {
                // get a list of all flag image files in this region
                String [] paths = assets.list(region);
                for (String path : paths) {
                    fileNameList.add(path.replace(".png", ""));
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error loading image file names", e);
        }

        correctAnswers = 0;
        totalGuesses = 0;
        quizCountriesList.clear();

        int flagCounter = 1;
        int numberOfFlags = fileNameList.size();

        while (flagCounter <= FLAGS_IN_QUIZ) {
            int randomIndex = random.nextInt(numberOfFlags);

            // get the random file name
            String fileName = fileNameList.get(randomIndex);
            // if it hasn't been already chosen add to quizCountriesList
            if (!quizCountriesList.contains(fileName)) {
                quizCountriesList.add(fileName);
                ++flagCounter;
            }
        }

        loadNextFlag();
    }

    private void loadNextFlag() {
        String nextImage = quizCountriesList.remove(0);
        correctAnswer = nextImage;
        answerTextView.setText("");

        questionNumberTextView.setText(getString(R.string.question, (correctAnswers+1), FLAGS_IN_QUIZ));

        String region = nextImage.substring(0, nextImage.indexOf('-'));

        AssetManager assets = getActivity().getAssets();

        try(InputStream stream = assets.open(region + "/" + nextImage + ".png")) {
            // load asset as Drawable and display on flagImageView
            Drawable flag = Drawable.createFromStream(stream, nextImage);
            flagImageView.setImageDrawable(flag);

            animate(false);
        }catch (IOException e) {
            Log.e(TAG, "Error loading " + nextImage, e);
        }

        Collections.shuffle(fileNameList);
        // put correct answer at the end of the list so we won't pull that one out for incorrect answers, even if user set max guess buttons
        int correct = fileNameList.indexOf(correctAnswer);
        fileNameList.add(fileNameList.remove(correct));

        for (int row =0; row < guessRows; row++) {
            for (int column = 0; column < guessLinearLayouts[row].getChildCount(); column++) {
                Button newGuessButton = (Button) guessLinearLayouts[row].getChildAt(column);
                newGuessButton.setEnabled(true);
                newGuessButton.setTextColor(getResources().getColor(R.color.button_text_color, getContext().getTheme()));
                String fileName = fileNameList.get((row*2) + column);
                newGuessButton.setText(getCountryName(fileName));
            }
        }

        // randomly replace one button with the correct answer
        int row = random.nextInt(guessRows);//pick random row
        int column = random.nextInt(2);//pick random column
        LinearLayout randomRow = guessLinearLayouts[row];
        String countryName = getCountryName(correctAnswer);
        ((Button)randomRow.getChildAt(column)).setText(countryName);
    }

    private void animate(boolean animateOut) {
        if (correctAnswers == 0) {
            // correct answers = 0 implies that user is on first quest, so we won't animate quiz layout yet
            return;
        }

        int centerX = (quizLinearLayout.getLeft() + quizLinearLayout.getRight())/2;
        int centerY = (quizLinearLayout.getTop() + quizLinearLayout.getBottom())/2;

        int radius = Math.max(quizLinearLayout.getWidth(), quizLinearLayout.getHeight());

        Animator animator;

        if (animateOut) {
            animator = ViewAnimationUtils.createCircularReveal(quizLinearLayout, centerX, centerY, radius, 0);
            animator.addListener(new Animator.AnimatorListener() {
                // called when animation has ended
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    loadNextFlag();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        else {
            animator = ViewAnimationUtils.createCircularReveal(quizLinearLayout, centerX, centerY, 0, radius);
        }
        animator.setDuration(500); // set duration of animation to 500ms(0.5sec)
        animator.start();
    }

    private String getCountryName(String fileName) {
        return fileName.substring(fileName.indexOf('-')+1).replaceAll("_", " ");
    }

    private View.OnClickListener guessButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btn = (Button) v;
                    String guess = btn.getText().toString();
                    String answer = getCountryName(correctAnswer);

                    ++totalGuesses;
                    if (guess.equals(answer)) {
                        correctAnswers++;

                        answerTextView.setText(answer);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            answerTextView.setTextColor(getResources().getColor(R.color.correct_answer, getContext().getTheme()));
                        }

                        disableButtons();

                        if (correctAnswers == FLAGS_IN_QUIZ) {
                            // Dialog fragment to display quiz stats and start new quiz
                            QuizResultsDialogFragment quizResults = new QuizResultsDialogFragment();
                            quizResults.setQuizFragment(MainActivityFragment.this);
                            quizResults.setCancelable(false);
                            quizResults.show(getFragmentManager(), "quiz results");
                        }
                        else {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                   animate(true);
                                }
                            }, 1000);
                        }
                    }

                    else {
                        //flagImageView.setAnimation(shakeAnimation);
                        flagImageView.startAnimation(shakeAnimation);
                        btn.setText(R.string.incorrect_answer);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            btn.setTextColor(getResources().getColor(R.color.incorrect_answer, getContext().getTheme()));
                        }
                        btn.setEnabled(false);
                    }
                }
            };

    private void disableButtons() {
        for (int i=0; i<guessRows; i++) {
            LinearLayout btnsRow = guessLinearLayouts[i];
            for (int j=0; j<btnsRow.getChildCount(); j++) {
                ((Button)btnsRow.getChildAt(j)).setEnabled(false);
            }
        }
    }

    public static class QuizResultsDialogFragment extends DialogFragment {

        private MainActivityFragment quizFragment;

        public void setQuizFragment(MainActivityFragment quizFragment) {
            this.quizFragment = quizFragment;
        }
        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.results, quizFragment.totalGuesses, (1000/(double) quizFragment.totalGuesses)));

            builder.setPositiveButton(R.string.reset_quiz, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quizFragment.resetQuiz();
                }
            });
            return builder.create();
        }
    }
}
