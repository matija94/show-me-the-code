package com.matija.cannongame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by matija on 17.6.17..
 */

public class Cannon {

    private int baseRadius;
    private int barrelLength;
    private Point barrelEnd = new Point();
    private double barrelAngle;
    private Cannonball cannonball;
    private Paint paint = new Paint();
    private CannonView cannonView;


    public Cannon(CannonView view, int baseRadius, int barrelLength, int barrelWidth) {
        this.cannonView = view;
        this.baseRadius = baseRadius;
        this.barrelLength = barrelLength;

        paint.setStrokeWidth(barrelWidth);
        paint.setColor(Color.BLACK);
        align(Math.PI/2);
    }


    // aligns the cannon barrel to the given angle
    public void align(double barrelAngle) {
        this.barrelAngle = barrelAngle;

        // sine is X here because we are in landscape mode and android (0,0) is always top-right independently of screen orientation
        barrelEnd.x = (int) (barrelLength * Math.sin(barrelAngle));

        // cosine is Y because we are in landscape mode and android (0,0) is always top-right independently of screen orientation
        barrelEnd.y = (int) (-barrelLength * Math.cos(barrelAngle)) + cannonView.getScreenHeight() / 2;
    }

    // creates and fires Cannonball in the direction Cannon points
    public void fireCannonball() {
        // calculate the Cannonball velocity's x component

        int velocityX = (int) (CannonView.CANNONBALL_SPEED_PERCENT * cannonView.getScreenWidth() * Math.sin(barrelAngle));
        // and y
        int velocityY = (int) (CannonView.CANNONBALL_SPEED_PERCENT * cannonView.getScreenWidth() * -Math.cos(barrelAngle));

        int radius = (int) (cannonView.getScreenHeight() * CannonView.CANNONBALL_RADIUS_PERCENT);

        cannonball = new Cannonball(cannonView, Color.BLACK, CannonView.CANNON_SOUND_ID, -radius, cannonView.getScreenHeight()/2 - radius, radius, velocityX, velocityY);

        cannonball.playSound();
    }


    public void draw(Canvas canvas) {
        // draw cannon barrel in mid of the Y-axis
        canvas.drawLine(0,  cannonView.getScreenHeight()/2, barrelEnd.x, barrelEnd.y, paint);

        // draw cannon base
        canvas.drawCircle(0, cannonView.getScreenHeight()/2, baseRadius, paint);

    }

    public Cannonball getCannonball() {
        return cannonball;
    }

    public void removeCannonball() {
        cannonball=null;
    }

}
