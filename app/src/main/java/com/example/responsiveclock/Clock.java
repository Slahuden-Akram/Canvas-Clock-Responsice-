package com.example.responsiveclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class Clock extends View {

    private static final String TAG = "Clock";
    private int mHeight;
    private int mWidth;
    private int mCenterX;
    private int mCenterY;
    private boolean mIsInit;
    private int mMinimum;
    private int mPadding;
    private int mRadius;
    private Paint mPaint;
    private int[] mNumbers;
    private Rect mRect;
    private double mAngle;
    private int mHandSize;
    private int mHourHandSize;
    private int mHour;
    private int mMinute;
    private int mSecond;
    private int mRadiusForNumbers;
    private int mRadiusForCircle;


    public Clock(Context context) {
        super(context);
    }

    protected void init(){
        mHeight = getHeight();
        mWidth = getWidth();
        mIsInit= true;
        mCenterX= mWidth/2;
        mCenterY= mHeight/2;
        mMinimum = Math.min(mHeight,mWidth);
        mPadding =50;
        mRadius = mMinimum/2-mPadding;
        mRadiusForNumbers= mRadius-35;
        mPaint = new Paint();
        mNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        mRect = new Rect();
        mHourHandSize = mRadius - mRadius/2;
        mHandSize = mRadius - mRadius/2;
        mRadiusForCircle =mRadius-30;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!mIsInit){
            init();
        }
        drawCircle2(canvas);
        drawCircle(canvas);
        drawCircle3(canvas);
        drawNumerals(canvas);
        drawHands(canvas);
        postInvalidateDelayed(500);
    }

    protected void setPaintAttribute(int color, Paint.Style stroke, int strokeWidth){
        mPaint.reset();
        mPaint.setColor(color);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(stroke);
        mPaint.setAntiAlias(true);
    }

    protected void drawCircle2(Canvas canvas){
        mPaint.reset();
        setPaintAttribute(Color.CYAN,Paint.Style.FILL_AND_STROKE,8);
        canvas.drawCircle(mCenterX,mCenterY,mRadius+20, mPaint);
    }
    protected void drawCircle(Canvas canvas){
        mPaint.reset();
        setPaintAttribute(Color.BLACK,Paint.Style.FILL_AND_STROKE,8);
        canvas.drawCircle(mCenterX,mCenterY,mRadius, mPaint);
    }
    protected void drawCircle3(Canvas canvas){
        mPaint.reset();
        setPaintAttribute(Color.CYAN,Paint.Style.FILL_AND_STROKE,8);
        canvas.drawCircle(mCenterX,mCenterY,10, mPaint);
    }

    private void drawHands(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        //convert to 12hour format from 24 hour format
        mHour = mHour > 12 ? mHour - 12 : mHour;
        mMinute = calendar.get(Calendar.MINUTE);
        mSecond = calendar.get(Calendar.SECOND);

        drawHourHand(canvas, (mHour + mMinute / 60.0) * 5f);
        drawMinuteHand(canvas,mMinute);
        drawSecondsHand(canvas,mSecond);
    }

    private void drawMinuteHand(Canvas canvas, float location) {
        mPaint.reset();
        setPaintAttribute(Color.WHITE, Paint.Style.STROKE,8);
        mAngle = Math.PI * location / 30 - Math.PI / 2;
        canvas.drawLine(mCenterX, mCenterY,(float)(mCenterX + Math.cos( mAngle)* mHandSize), (float)(mCenterY+Math.sin(mAngle)*mHourHandSize), mPaint);
    }

    private void drawHourHand(Canvas canvas, double location) {
        mPaint.reset();
        setPaintAttribute(Color.WHITE, Paint.Style.STROKE,10);
        mAngle = Math.PI * location / 30 - Math.PI / 2;
        canvas.drawLine(mCenterX,mCenterY,(float) (mCenterX+Math.cos(mAngle)*mHourHandSize)
                , (float) (mCenterY+Math.sin(mAngle)*mHourHandSize),mPaint);
    }
    private void drawSecondsHand(Canvas canvas, float location) {
        mPaint.reset();
        setPaintAttribute(Color.RED, Paint.Style.STROKE,8);
        mAngle = Math.PI * location / 30 - Math.PI / 2;
        canvas.drawLine(mCenterX,mCenterY,(float)   (mCenterX+Math.cos(mAngle)*mHandSize)
                , (float) (mCenterY+Math.sin(mAngle)*mHourHandSize),mPaint);
    }

    private void drawNumerals(Canvas canvas) {
        mPaint.setTextSize(50);
        mPaint.setColor(Color.WHITE);
        for (int number : mNumbers) {
            String num = String.valueOf(number);
            mPaint.getTextBounds(num, 0, num.length(), mRect);
            double angle = Math.PI / 6 * (number - 3);
            int x = (int) (mCenterX + Math.cos(angle) * mRadiusForNumbers - mRect.width() / 2);
            int y = (int) (mCenterY + Math.sin(angle) * mRadiusForNumbers + mRect.height() / 2);
            canvas.drawText(num,x,y,mPaint);

            Log.d(TAG, "PI "+Math.PI);
            Log.d(TAG, "PI/6 "+Math.PI/6);
            Log.d(TAG, "number-3 "+(number-3));
            Log.d(TAG, "drawNumerals: Angle= "+angle);
            Log.d(TAG, "drawNumerals: " +x);
            Log.d(TAG, "drawNumerals: "+y);
            Log.d(TAG, "number= "+num);
            Log.d(TAG, ""+mRect.width());
            Log.d(TAG, "drawNumerals: ----------------------");
        }
    }

}
