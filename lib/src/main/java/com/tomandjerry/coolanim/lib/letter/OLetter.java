package com.tomandjerry.coolanim.lib.letter;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Weiwu on 16/2/19.
 */
public class OLetter extends Letter {

    private Paint mPaint;
    private RectF mRectF;
    //启动角度
    private int mStartAngle;
    //偏移角度
    private int mSweepAngle;

    private ValueAnimator mCirAnimator;

    public OLetter(int x, int y) {
        super(x, y);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mRectF = new RectF(mCurX - 60, mCurY - 60, mCurX + 60, mCurY + 60);
    }

    @Override
    public void startAnim() {
        mCirAnimator = ValueAnimator.ofFloat(0, 1).setDuration(1500);
        mCirAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                mStartAngle = (int) (180 * factor);
                mSweepAngle = (int) (360*factor);
            }
        });

        mCirAnimator.start();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mPaint);
    }
}
