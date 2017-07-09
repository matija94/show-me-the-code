package com.matija.cannongame;

/**
 * Created by matija on 17.6.17..
 */


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * represents rectangle-bounded game element
 */
public class GameElement {


    protected CannonView cannonView;
    protected Paint paint = new Paint();
    protected Rect shape; // bounded rectangle that represents actual object on the screen
    private float velocityY; // the vertical velocity(speed) of this game element
    private int soundId;

    public GameElement(CannonView view, int color, int soundId, int x, int y, int width, int height, float velocityY) {
        this.cannonView = view;
        paint.setColor(color);
        shape = new Rect(x, y, x+ width, y+height); // set bounds on rect
        this.soundId = soundId;
        this.velocityY = velocityY;
    }

    // update game element position and check for wall collision
    public void update(double interval) {
        // update vertical pos
        shape.offset(0, (int) (velocityY * interval));

        // if game elements collides with the wall reverse direction
        if (shape.top<0 && velocityY < 0 ||
                shape.bottom>cannonView.getScreenHeight() && velocityY > 0) {
                velocityY*=-1;
        }

    }

    // draws this game element on the cannonView
    public void draw(Canvas canvas) {
        canvas.drawRect(shape, paint);
    }

    // plays the sound that corresponds to this game element
    public void playSound(){
        cannonView.playSound(soundId);
    }

}
