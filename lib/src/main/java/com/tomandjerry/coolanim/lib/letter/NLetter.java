package com.tomandjerry.coolanim.lib.letter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.tomandjerry.coolanim.lib.Config;

/**
 * Created by yanxing on 16/2/19.
 */
public class NLetter extends Letter {
    private int mFv;
    private int mSv;
    private Paint mPaint;
    private Path mPath;
    private int mMoveX;
    private int mMoveY;
    private int mCurValue;
    private boolean isStart = false;
    private RectF mRectF;
    // 描绘时候的落笔点,也就是圆的半径
    public final static int SHIFT = 40;
    public final static int STROKE_WIDTH = 20;
    public final static int WIDTH = STROKE_WIDTH / 2 + SHIFT;
    public final static int LENGTH = 120;
    // n两个脚的高度
    public final static int LEG_LENGTH = LENGTH - SHIFT - STROKE_WIDTH / 2;

    public NLetter(int x, int y) {
        super(x, y);
        // 将坐标点调整为中心点
        mCurY += LENGTH / 2;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Config.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPath = new Path();
        mFv = mDuration / 3;
        mSv = mDuration * 2 / 3;
        // 移动到起始位置
        mMoveX = mCurX - SHIFT;
        mMoveY = mCurY;
        mPath.moveTo(mMoveX, mMoveY);
        mRectF = new RectF();
        mRectF.set(mCurX - SHIFT, mCurY - SHIFT - LEG_LENGTH, mCurX + SHIFT, mCurY + SHIFT - LEG_LENGTH);
    }

    @Override
    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(1, mDuration);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(mDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isStart) {
                    return;
                }
                mCurValue = (int) animation.getAnimatedValue();
                if (mCurValue <= mFv) {
                    if (mCurValue <= mFv - 15) {
                        mMoveY = mCurY - LEG_LENGTH * mCurValue / mFv;
                        mPath.lineTo(mMoveX, mMoveY);
                    } else {
                        mPath.lineTo(mMoveX, mCurY - LEG_LENGTH);
                    }
                } else if (mCurValue <= mSv) {
                    mCurValue -= mFv;
                    mPath.addArc(mRectF, 180, mCurValue * 180 / (mSv - mFv));
                } else {
                    mCurValue -= mSv;
                    mMoveX = mCurX + SHIFT;
                    mMoveY = mCurY - LEG_LENGTH + LEG_LENGTH * mCurValue / (mDuration - mSv);
                    mPath.lineTo(mMoveX, mMoveY);
                }
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
