package com.tomandjerry.coolanim.lib.pellet;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.tomandjerry.coolanim.lib.Config;

/**
 * 小球运动过程中是椭圆的
 * Created by yanxing on 16/1/31.
 */
public class SmallYellowBall {
    private static SmallYellowBall mBall;
    private boolean isShow = false;
    private Paint mPaint;
    private int mRadius;
    private float mCurX;
    private int mOriginX;
    private float mCurY;
    private int mOriginY;
    // 两球的距离
    private float mDistance;
    // 地面距离中心的距离
    public static int HEIGHT = 50;
    // 重力加速度
    public static float gravity = 9.8f / (1000 * 1000);
    // x方向的速度
    private float mSpeedX;
    private float mDuration;
    // 当前运动的距离
    private float curDistance;
    // 当前时间
    private float curTime;
    private float mFiRate;
    // 椭圆绘制区域
    private RectF mRectF;
    // y方向的速度
    private float mSpeedY;
    // y方向的最大速度
    private float maxSpeedY;
    // 球的偏移角度
    private float mAngle;
    // 球的压缩
    private float mShift;

    private SmallYellowBall() {
        initConfig();
    }

    protected void initConfig() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Config.YELLOW);
        mRadius = 15;
        mDistance = 100;
        mDuration = 2000;
        mSpeedX = mDistance / mDuration;
        mFiRate = HEIGHT * 4 / mDuration;
        // 初始化重力加速度
        gravity = (float) (3 * HEIGHT / Math.pow(mDuration / 4f, 2));
        maxSpeedY = gravity * mDuration / 4;
        mRectF = new RectF(mCurX - mRadius, mCurY - mRadius, mCurX + mRadius, mCurY + mRadius);
        mShift = mRadius / 6;
    }

    // 获取单实例的球
    public synchronized static SmallYellowBall getInstance() {
        if (mBall == null) {
            mBall = new SmallYellowBall();
        }
        return mBall;
    }

    /**
     * 分开四个段区域
     */
    public void throwOut() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mDuration);
        valueAnimator.setDuration((long) mDuration);
        // 3个节点
        final float fiv = mDistance / 4;
        final float sev = mDistance / 2;
        final float thv = mDistance * 3 / 4;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curTime = (float) animation.getAnimatedValue();
                curDistance = curTime * mSpeedX;
                mRectF.set(mCurX - mRadius, mCurY - mRadius, mCurX + mRadius, mCurY + mRadius);
                if (curDistance <= fiv) {
                    mCurY = mOriginY + curTime * mFiRate;
                    mAngle = 0;
                    if (curDistance >= 5 && curDistance < fiv - 2) {
                        mRectF.set(mCurX - mRadius + mShift, mCurY - mRadius, mCurX + mRadius - mShift, mCurY + mRadius);
                        mAngle = -45;
                    }
                    // 掷地一刻压扁效果
                    if (curDistance >= fiv - 2) {
                        mRectF.set(mCurX - mRadius, mCurY - mRadius + 10, mCurX + mRadius, mCurY + mRadius);
                        mAngle = 0;
                    }
                } else if (curDistance <= sev) {
                    curTime -= mDuration / 4;
                    mSpeedY = maxSpeedY - gravity * curTime;
                    mAngle = (float) (Math.atan(mSpeedY / mSpeedX) * 180 / Math.PI);
                    mCurY = mOriginY + HEIGHT - 0.5f * (maxSpeedY + mSpeedY) * curTime;
                    if (mAngle < 15) {
                        mRectF.set(mCurX - mRadius, mCurY - mRadius, mCurX + mRadius, mCurY + mRadius);
                    } else {
                        mRectF.set(mCurX - mRadius + mShift, mCurY - mRadius, mCurX + mRadius - mShift, mCurY + mRadius);
                    }
                } else if (curDistance <= thv) {
                    curTime -= mDuration / 2;
                    mSpeedY = gravity * curTime;
                    mAngle = -(float) (Math.atan(mSpeedX / mSpeedY) * 180 / Math.PI);
                    mCurY = mOriginY - 0.5f * HEIGHT + 0.5f * mSpeedY * curTime;
                    if (mAngle > -15) {
                        mRectF.set(mCurX - mRadius, mCurY - mRadius, mCurX + mRadius, mCurY + mRadius);
                    } else {
                        mRectF.set(mCurX - mRadius + mShift, mCurY - mRadius, mCurX + mRadius - mShift, mCurY + mRadius);
                    }
                    // 掷地一刻压扁效果
                    if (curDistance >= thv - 2) {
                        mRectF.set(mCurX - mRadius, mCurY - mRadius + 10, mCurX + mRadius, mCurY + mRadius);
                        mAngle = 0;
                    }
                } else {
                    curTime -= mDuration * 3 / 4;
                    mCurY = mOriginY + HEIGHT - curTime * mFiRate;
                    mRectF.set(mCurX - mRadius, mCurY - mRadius, mCurX + mRadius, mCurY + mRadius);
                    if (curDistance >= thv + 5 && curDistance < mDistance - 10) {
                        mRectF.set(mCurX - mRadius + mShift, mCurY - mRadius, mCurX + mRadius - mShift, mCurY + mRadius);
                        mAngle = 45;
                    }
                }
                mCurX = mOriginX + curDistance;
            }
        });
        valueAnimator.start();
    }

    public void drawSelf(Canvas canvas) {
        if (isShow) {
            canvas.save();
            canvas.rotate(mAngle, mCurX, mCurY);
            canvas.drawOval(mRectF, mPaint);
            canvas.restore();
        }
    }

    public void setShow(boolean show) {
        this.isShow = show;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setCurY(int y) {
        mCurY = y;
        mOriginY = y;
    }

    public void setCurX(int x) {
        mCurX = x;
        mOriginX = x;
    }
}
