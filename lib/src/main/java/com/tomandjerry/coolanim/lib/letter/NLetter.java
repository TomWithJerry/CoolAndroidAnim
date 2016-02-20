package com.tomandjerry.coolanim.lib.letter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.tomandjerry.coolanim.lib.Config;

/**
 * Created by yanxing on 16/2/19.
 */
public class NLetter extends Letter {
    private int mDuration = 1500;
    private Paint mPaint;
    private Path mPath;
    private int mMoveX;
    private int mMoveY;
    private int mCurValue;
    private boolean isStart = false;

    public NLetter(int x, int y) {
        super(x, y);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Config.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPath = new Path();
        // 移动到起始位置
        mMoveX = mCurX - 20;
        mMoveY = mCurY;
        mPath.moveTo(mMoveX, mMoveY);
        mPath.lineTo(mMoveX, mMoveY - 50);
        RectF mRectF = new RectF();
        mRectF.set(mCurX - 20, mCurY - 20 - 50, mCurX + 20, mCurY + 20 - 50);
        mPath.addArc(mRectF, 180, 180);
    }

    @Override
    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mDuration);
        animator.setDuration(mDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isStart) {
                    return;
                }
                mCurValue = (int) animation.getAnimatedValue();
                mMoveY = mCurY - mCurValue / 10;
//                mPath.lineTo(mMoveX, mMoveY);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isStart = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        animator.start();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isStart) {
            canvas.drawPath(mPath, mPaint);
        }
    }
}
