package com.tomandjerry.coolanim.lib.letter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import com.tomandjerry.coolanim.lib.Config;

/**
 * Created by Weiwu on 16/2/19.
 */
public class LLetter extends Letter {

    private Paint mPaint;

    //L上面的点
    private Point mFirstPoint;
    //转折处的点
    private Point mSecondPoint;
    //结尾点
    private Point mThirdPoint;

    //竖线
    private ValueAnimator mFirstLineAnimator;
    //横线
    private ValueAnimator mSecondLineAnimator;

    private int mStrokeWidth = 20;
    private int mLength = 140;
    private int mWidth = 80;

    //横线画完的标志
    private boolean mIsFirstFinish = false;

    public LLetter(int x, int y) {
        super(x, y);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Config.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokeWidth);


        mFirstPoint = new Point(x - mWidth/2 + mStrokeWidth / 2, y - mLength / 4 * 3);
        mSecondPoint = new Point(mFirstPoint);
        mThirdPoint = new Point(x - mWidth/2 + mStrokeWidth / 2, y + mLength / 4 + mStrokeWidth / 2);
    }

    @Override
    public void startAnim() {
        mFirstLineAnimator = ValueAnimator.ofFloat(0, 1).setDuration(750);
        mFirstLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                mSecondPoint.y = (int) (mFirstPoint.y + (mLength + mStrokeWidth) * factor);
            }
        });
        mFirstLineAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIsFirstFinish = true;
                mSecondLineAnimator.start();
            }
        });

        mSecondLineAnimator = ValueAnimator.ofFloat(0, 1).setDuration(750);
        mSecondLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                mThirdPoint.x = (int) (mSecondPoint.x + mWidth * factor);
            }
        });

        mFirstLineAnimator.start();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        canvas.drawLine(mFirstPoint.x, mFirstPoint.y, mSecondPoint.x, mSecondPoint.y, mPaint);
        if (mIsFirstFinish) {
            //减去了线粗导致的偏移
            canvas.drawLine(mSecondPoint.x - mStrokeWidth / 2, mSecondPoint.y - mStrokeWidth / 2, mThirdPoint.x, mThirdPoint.y, mPaint);
        }
    }
}
