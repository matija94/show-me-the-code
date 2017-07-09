package com.matija.doodlz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.provider.MediaStore;
import android.support.v4.print.PrintHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matija on 5.6.17..
 */

public class DoodleView extends View {

    private static final float TOUCH_TOLERANCE = 10;// used to determine whether user moved a finger enough to draw again

    private Bitmap bitmap; // drawing area for displaying or saving
    private Canvas bitmapCanvas; // used to draw on the bitmap
    private Paint paintScreen; //used to draw bitmap on the screen
    private Paint paintLine; // used to draw lines onto bitmap


    // Maps of current Paths being drawn and Points in those Paths
    private Map<Integer, Path> pathMap = new HashMap<>();
    private Map<Integer, Point> previousPointMap = new HashMap<>();

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);// passes the context and the layout attributes to the view constructor

        paintScreen = new Paint(); // used to display bitmap on the screen

        // set the initial display settings for the painted line
        paintLine = new Paint();
        paintLine.setAntiAlias(true); // smooths edges of drawn line
        paintLine.setColor(Color.BLACK); // default black color
        paintLine.setStyle(Paint.Style.STROKE); // solid line
        paintLine.setStrokeWidth(5); // set the default line width
        paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends
    }

    // create Bitmap and Canvas base on View's size
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE); // erase the bitmap with white
    }

    // performs custom drawing when the DoodleView is refreshed on the screen
    @Override
    public void onDraw(Canvas canvas) {
        // draw the background screen
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);

        // for each path currently being drawn
        for (int key : pathMap.keySet()) {
            canvas.drawPath(pathMap.get(key), paintLine);
        }
    }

    //handle touch event
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked(); // event type
        int actionIndex = event.getActionIndex();// pointer (i.e. , finger)

        // determine whether touch started, ended or is moving
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            touchStarted(event.getX(actionIndex), event.getY(actionIndex), event.getPointerId(actionIndex));
        }

        else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            touchEnded(event.getPointerId(actionIndex));
        }

        else {
            touchMoved(event);
        }

        // invalidates current view and calls its onDraw method
        invalidate();
        return true;
    }

    private void touchStarted(float x, float y, int lineID) {
        Path path; // used to store the path for the given touch id
        Point point; // used to store the last point in path

        if (pathMap.containsKey(lineID)) {
            Log.i("DOODLE_VIEW", "Finger with id " + lineID + "touched screen");
            path = pathMap.get(lineID); // get the path
            path.reset();
            point = previousPointMap.get(lineID); // get Path's last point
        }
        else {
            path = new Path();
            pathMap.put(lineID, path);
            point = new Point();
            previousPointMap.put(lineID, point);
        }

        path.moveTo(x,y);// move path to the coords of the touch
        point.x = (int) x; // save point x coord
        point.y = (int) y; // save point y coord
     }

    // called when the user drags along the screen
    private void touchMoved(MotionEvent event) {

        // for each of the pointers in the given motion event
        for (int i = 0; i < event.getPointerCount(); i++) {
            // get the pointer id and the pointer index
            int pointerId = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pointerId);

            if (pathMap.containsKey(pointerId)) {
                float x = event.getX(pointerIndex);
                float y = event.getY(pointerIndex);

                Path path = pathMap.get(pointerId);
                Point pointer = previousPointMap.get(pointerId);

                float deltaX = Math.abs((x - pointer.x));
                float deltaY = Math.abs((y- pointer.y));

                if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE) {
                    path.quadTo(pointer.x, pointer.y, (x + pointer.x)/2, (y+pointer.y)/2);

                    pointer.x = (int) x;
                    pointer.y = (int) y;
                }
            }
        }

    }

    private void touchEnded(int lineID) {

        Path path = pathMap.get(lineID);
        bitmapCanvas.drawPath(path, paintLine);
        path.reset();
    }


    public void saveImage() {

        final String name = "Doodlz" + System.currentTimeMillis()+ ".jpg";

        // insert the image on the device
        String location = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, name, "Doodlz Drawing");
        if (location != null) {
            Toast message = Toast.makeText(getContext(), R.string.message_saved, Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset()/2, message.getYOffset()/2);
            message.show();
        }

        else {
            Toast message = Toast.makeText(getContext(), R.string.message_error_saving, Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset()/2, message.getYOffset()/2);
            message.show();
        }

    }

    public void printImage() {
        // check to see if system supports printing
        if (PrintHelper.systemSupportsPrint()) {
            PrintHelper printHelper = new PrintHelper(getContext());
            // fit img in page bounds and print image
            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            printHelper.printBitmap("Doodlz image", bitmap);
        }
        else {
            // display msg on mid of the screen that system doesn't support printing
            Toast message = Toast.makeText(getContext(), R.string.message_error_printing, Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset()/2, message.getXOffset()/2);
            message.show();
        }
    }

    public void clear() {
        pathMap.clear();
        previousPointMap.clear();
        bitmap.eraseColor(Color.WHITE);
        invalidate();// refresh the screen
    }

    public void setDrawingColor(int color) {
        paintLine.setColor(color);
    }

    public int getDrawingColor() {
        return paintLine.getColor();
    }

    public void setLineWidth(int width) {
        paintLine.setStrokeWidth(width);
    }

    public int getLineWidth() {
        return (int) paintLine.getStrokeWidth();
    }
}
