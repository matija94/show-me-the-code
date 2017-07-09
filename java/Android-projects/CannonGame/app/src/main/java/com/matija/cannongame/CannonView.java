package com.matija.cannongame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by matija on 17.6.17..
 */

public class CannonView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "CannonView"; // for logging errors
    // constants for game play
    public static final int MISS_PENALTY = 2; // seconds deducted on a miss
    public static final int HIT_REWARD = 3; // seconds added on a hit
    // constants for the Cannon
    public static final double CANNON_BASE_RADIUS_PERCENT = 3.0 / 40;
    public static final double CANNON_BARREL_WIDTH_PERCENT = 3.0 / 40;
    public static final double CANNON_BARREL_LENGTH_PERCENT = 1.0 / 10;
    // constants for the Cannonball
    public static final double CANNONBALL_RADIUS_PERCENT = 3.0 / 80;
    public static final double CANNONBALL_SPEED_PERCENT = 3.0 / 2;
    // constants for the Targets
    public static final double TARGET_WIDTH_PERCENT = 1.0 / 40;
    public static final double TARGET_LENGTH_PERCENT = 3.0 / 20;
    public static final double TARGET_FIRST_X_PERCENT = 3.0 / 5;
    public static final double TARGET_SPACING_PERCENT = 1.0 / 60;
    public static final double TARGET_PIECES = 9;
    public static final double TARGET_MIN_SPEED_PERCENT = 3.0 / 4;
    public static final double TARGET_MAX_SPEED_PERCENT = 6.0 / 4;
    // constants for the Blocker
    public static final double BLOCKER_WIDTH_PERCENT = 1.0 / 40;
    public static final double BLOCKER_LENGTH_PERCENT = 1.0 / 4;
    public static final double BLOCKER_X_PERCENT = 1.0 / 2;
    public static final double BLOCKER_SPEED_PERCENT = 1.0;
    // text size 1/18 of screen width
    public static final double TEXT_SIZE_PERCENT = 1.0 / 18;
    private CannonThread cannonThread; // controls the game loop
    private Activity activity; // to display Game Over dialog in GUI thread
    private boolean dialogIsDisplayed = false;

    // game objects
    private Cannon cannon;

    private Blocker blocker;
    private ArrayList<Target> targets;

    // dimension vars
    private int height;
    private int width;

    private boolean gameOver;
    private double timeLeft;
    private int shotsFired;
    private double totalElapsedTime;

    public static final int TARGET_SOUND_ID = 0;
    public static final int CANNON_SOUND_ID = 1;
    public static final int BLOCKER_SOUND_ID = 2;
    private SoundPool soundPool;// plays sound effects
    private SparseIntArray soundMap; // maps IDs to SoundPool

    private Paint textPaint; // paint used to draw time remaining text
    private Paint backgroundPaint; // paint used to clear drawing area


    private static DialogFragment gameResults;

    public CannonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (MainActivity) context;
        getHolder().addCallback(this);

        // init sound pool to play sounds of 3 objects we are using in game
        AudioAttributes.Builder attrsBuilder = new AudioAttributes.Builder();
        attrsBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setMaxStreams(1);
        soundPoolBuilder.setAudioAttributes(attrsBuilder.build());
        soundPool = soundPoolBuilder.build();

        // init soundMap
        soundMap = new SparseIntArray(3);
        soundMap.put(TARGET_SOUND_ID, soundPool.load(context, R.raw.target_hit, 1));
        soundMap.put(CANNON_SOUND_ID, soundPool.load(context, R.raw.cannon_fire, 1));
        soundMap.put(BLOCKER_SOUND_ID, soundPool.load(context, R.raw.blocker_hit, 1));

        textPaint = new Paint();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        height = h;
        width = w;

        // configure text properties
        textPaint.setTextSize((int) (TEXT_SIZE_PERCENT * height));
        textPaint.setAntiAlias(true); // smoothes the text
    }

    public int getScreenWidth() {
        return width;
    }

    public int getScreenHeight() {
        return height;
    }

    public void playSound(int soundId) {
        soundPool.play(soundMap.get(soundId), 1, 1, 1, 0, 1f);
    }

    public void newGame() {

        // construct new cannon
        cannon = new Cannon(this, (int) (CANNON_BASE_RADIUS_PERCENT * height), (int) (CANNON_BARREL_LENGTH_PERCENT * width), (int) (CANNON_BARREL_WIDTH_PERCENT * height));

        Random random = new Random(); // random velocities
        targets = new ArrayList<>();

        // initialize first target X cord (from the left to the rigth)
        int targetX = (int) (TARGET_FIRST_X_PERCENT * width);
        int targetY = (int) ((0.5 - TARGET_LENGTH_PERCENT / 2) * height);

        for (int n = 0; n < TARGET_PIECES; n++) {
            // determine random velocity between min and max values
            double velocity = height * (random.nextDouble() * (TARGET_MAX_SPEED_PERCENT - TARGET_MIN_SPEED_PERCENT) + TARGET_MIN_SPEED_PERCENT);

            // alternate target colors between dark and light
            int color = (n % 2 == 0) ?
                    getResources().getColor(R.color.dark, getContext().getTheme()) :
                    getResources().getColor(R.color.light, getContext().getTheme());

            velocity *= -1; // reverse init velocity for next target

            // create and add new target to target list
            targets.add(new Target(this, color, HIT_REWARD, targetX, targetY, (int) (TARGET_WIDTH_PERCENT * width), (int) (TARGET_LENGTH_PERCENT * height), (int) velocity));

            // increase target x cord to position next target more to the right
            targetX += (TARGET_WIDTH_PERCENT + TARGET_SPACING_PERCENT) * width;
        }

        // create new blocker
        blocker = new Blocker(this, Color.BLACK, MISS_PENALTY, (int) (BLOCKER_X_PERCENT * width), (int) ((0.5 - BLOCKER_LENGTH_PERCENT / 2) * height),
                (int) (BLOCKER_WIDTH_PERCENT * width), (int) (BLOCKER_LENGTH_PERCENT * height), (float) (BLOCKER_SPEED_PERCENT * height));

        /*blocker = new Blocker(this,Color.BLACK, MISS_PENALTY, width/4, height/4, width/4, height/4, 0);*/

        timeLeft = 10; // start countDown at 10 seconds

        shotsFired = 0;
        totalElapsedTime = 0.0;

        if (gameOver) { // start new game after last game ended
            gameOver = false; // the game is not over
            cannonThread = new CannonThread(getHolder()); // make cannon thread that will actually manage the surface holder object
            cannonThread.setRunning(true);
            cannonThread.start(); // start game loop
        }

        hideSystemBars();
    }

    private void updatePositions(double totalElapsedTime) {
        Log.d(TAG, "updating positions");
        double interval = totalElapsedTime / 1000.0; // convert to seconds

        if (cannon.getCannonball() != null) {
            cannon.getCannonball().update(interval); // update cannonball pos
        }

        blocker.update(interval);

        for (Target target : targets) {
            target.update(interval);
        }

        timeLeft -= interval;

        if (timeLeft <= 0) {
            // set it to zero just to be sure it is 0.0 secs remaining displayed in dialog user, since most of the time it will be negative
            timeLeft = 0.0;
            gameOver = true; // the game is over
            cannonThread.setRunning(false);// terminate thread
            showGameOverDialog(R.string.lose);// show the losing dialog
        }

        // if all pieces have been hit
        if (targets.isEmpty()) {
            cannonThread.setRunning(false);
            showGameOverDialog(R.string.win); // winning dialog
            gameOver = true;
        }
    }

    // aligns the cannon barrel and fires a cannonball if it is on the screen
    public void alignAndFireCannonbal(MotionEvent event) {

        Point touchPoint = new Point((int) (event.getX()), (int) (event.getY()));

        // compute touch distance from the center of the screen on y-axis
        int distanceY = (height / 2) - touchPoint.y;

        double angle = 0;

        angle = Math.atan2(touchPoint.x, distanceY);

        cannon.align(angle);

        if (cannon.getCannonball() == null || !cannon.getCannonball().isOnScreen()) {
            cannon.fireCannonball();
            shotsFired++;
        }
    }


    private void showGameOverDialog(final int messageId) {
        final GameOverDialog gameOverDialog = new GameOverDialog();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showSystemBars(); // exit immersive mode
                dialogIsDisplayed = true;
                gameOverDialog.setCancelable(false); // modal dialog
                gameOverDialog.show(activity.getFragmentManager(), "results", CannonView.this, messageId);
            }
        });
    }

    public void drawGameElements(Canvas canvas) {
        Log.i(TAG, "Drawing game elements");
        //  clear the background
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);

        // display remaining time
        canvas.drawText(getResources().getString(R.string.time_remaining_format, timeLeft), 50, 100, textPaint);

        cannon.draw(canvas); // draw the cannon

        // draw the game elements
        if (cannon.getCannonball() != null && cannon.getCannonball().isOnScreen()) {
            cannon.getCannonball().draw(canvas);
        }

        blocker.draw(canvas); // draw blocker


        // draw all shootable targets
        for (Target target : targets) {
            target.draw(canvas);
        }
    }

    // test if the cannonball collides with any of the targets or the blocker and handles the event
    public void testForCollision() {
        if (cannon.getCannonball() != null && cannon.getCannonball().isOnScreen()) {
            for (int i = 0; i < targets.size(); i++) {
                Target target = targets.get(i);
                if (cannon.getCannonball().collidesWith(target)) {
                    target.playSound();
                    timeLeft += target.getHitReward(); // reward player with increased time

                    targets.remove(target); // remove destroyed target
                    --i; // decrease index to not skip next target
                    break; // one target is shot, so we break the target collision test loop
                }
            }
        } else {
            cannon.removeCannonball();
        }

        // check if cannonball collides with blocker
        if (cannon.getCannonball() != null && cannon.getCannonball().isOnScreen()) {
            if (cannon.getCannonball().collidesWith(blocker)) {

                blocker.playSound();

                timeLeft -= blocker.getMissPenalty(); // punish the user for hitting blocker ( decrease his time left )
                cannon.getCannonball().reverseVelocityX(); // reverse the direction of the cannonball
            }
        }

    }

    // stop the game , called by CannonGameFragment's onPause method
    public void stopGame() {
        if (cannonThread != null) {
            cannonThread.setRunning(false);
        }
    }

    // release sound resources that were loaded when the view was created
    // this method is called by CannonGameFragment's onDestroy method
    public void releaseResources() {
        soundPool.release();
        soundPool = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!dialogIsDisplayed) {
            newGame();
            cannonThread = new CannonThread(holder);
            cannonThread.setRunning(true);
            cannonThread.start();
        }
    }

    // called when surface changes size, we are always in landscape so this method doesn't need implementation
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        cannonThread.setRunning(false);
        while (retry) {
            try {
                cannonThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread interrupted", e);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            alignAndFireCannonbal(e);
        }
        return true;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public double getTotalTimeElapsed() {
        return totalElapsedTime;
    }

    public void setDialogDisplayed(boolean dialogDisplayed) {
        this.dialogIsDisplayed = dialogDisplayed;
    }

    private class CannonThread extends Thread {
        private SurfaceHolder holder;
        private boolean threadIsRunning;

        public CannonThread(SurfaceHolder holder) {
            this.holder = holder;
            setName("CannonThread");
        }

        @Override
        public void run() {

            Canvas canvas = null;
            long previousFrameTime = System.currentTimeMillis();

            while (threadIsRunning) {
                try {
                    canvas = holder.lockCanvas(null);

                    synchronized (holder) {
                        long currentTimeMilis = System.currentTimeMillis();
                        double elapsedTimeMS = currentTimeMilis - previousFrameTime;
                        totalElapsedTime += elapsedTimeMS / 1000.0;
                        updatePositions(elapsedTimeMS); // update game state
                        testForCollision(); // test for collision
                        drawGameElements(canvas); // draw game objects
                        previousFrameTime = currentTimeMilis; // update previous time
                    }
                } finally {
                    // display canvas's contents on the CannonView
                    // and enable other threads to use canvas
                    /*backgroundPaint.setColor(Color.BLACK);
                    canvas.drawCircle(width/2, height/2,100f, backgroundPaint);*/
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        public void setRunning(boolean running) {
            this.threadIsRunning=running;
        }
    }

    // hide system bars and app bar
    private void hideSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // show system bars and app bar
    private void showSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


}
