package com.tomandjerry.coolanim.lib.letter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.tomandjerry.coolanim.lib.Config;

/**
 * 字母i的绘制以及动画
 * Created by yanxing on 16/2/18.
 */
public class ILetter extends Letter {
    private int mCurValue;
    private boolean isStart = false;
    private Paint mPaint;
    // 竖线弹出的时间
    private int mDuration1 = 1000;
    // 圆球弹出的时间
    private int mDuration2 = 500;
    // 竖线变化的长度
    private float mLength1;
    // 圆球弹出的变化高度
    private float mLength2;
    // 圆球的半径
    private int mRadius;

    public ILetter(int x, int y) {
        super(x, y);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Config.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mDuration1 + mDuration2);
        animator.setDuration(mDuration1 + mDuration2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isStart) {
                    return;
                }
                mCurValue = (int) animation.getAnimatedValue();
                if (mCurValue <= mDuration1) {
                    mLength1 = mCurValue / 10;
                } else {
                    mCurValue -= mDuration1;
                    mRadius = 12 * mCurValue / 500;
                    mLength2 = 30 * mCurValue / 500;
                }

            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isStart = true;
                mRadius = 0;
            }
        });
        animator.start();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isStart) {
            // 绘制竖线
            mPaint.setStrokeWidth(20);
            canvas.drawLine(mCurX, mCurY, mCurX, mCurY - mLength1, mPaint);
            // 绘制圆点
            canvas.drawCircle(mCurX, mCurY - mLength1 + 30 - mLength2 - 20, mRadius, mPaint);
        }
    }
}
