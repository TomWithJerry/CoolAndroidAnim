package com.tomandjerry.coolanim.lib.pellet;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by weicheng on 16/1/31.
 */
public class SecondPellet extends Pellet {

    private final int FIRST_RED_RADIUS = 50;

    private int mRedStrokeWidthFactor;
    private int mRedCircleRadius;
    private int mYellowCircleRadius1;
    private int mYellowCircleRadius2;
    private int mCurNum;
    private Paint mPaint;
    private ValueAnimator valueAnimator;


    public SecondPellet(int x, int y) {
        super(x, y);
    }

    @Override
    protected void initConfig() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mRedCircleRadius = 80;
        mYellowCircleRadius1 = 100;
        mYellowCircleRadius2 = 100;

        valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(800);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mYellowCircleRadius1 = (int) ((float) animation.getAnimatedValue() * MAX_RADIUS_CIRCLE);
                mRedStrokeWidthFactor = (int) (20 + (float) animation.getAnimatedValue() * 10);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        mPaint.setStrokeWidth(mRedStrokeWidthFactor);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(300, 300, FIRST_RED_RADIUS - mRedStrokeWidthFactor / 2, mPaint);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(mYellowCircleRadius1);
        canvas.drawCircle(300, 300, mYellowCircleRadius1 / 2, mPaint);
    }

    @Override
    public void startAnimation() {

    }

    private void setPaintColor() {

    }
}
