package com.tomandjerry.coolanim.lib.pellet;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.tomandjerry.coolanim.lib.pellet.Pellet;

/**
 * 小球运动过程中是椭圆的
 * Created by yanxing on 16/1/31.
 */
public class SmallYellowBall {
    private int YELLOW = Color.parseColor("#FFCC00");
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
    private int bounceTime;
    // 地面距离中心的距离
    public static int HEIGHT = 50;
    public static float GRAVITY = 9.8f;
    private float mSpeed;
    private float mDuration;
    // 当前运动的距离
    private float curDistance;
    // 当前时间
    private float curTime;
    private float mFRate;
    private float mSRate;
    private SmallYellowBall() {
        initConfig();
    }

    protected void initConfig() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(YELLOW);
        mRadius = 15;
        mDistance = 100;
        mDuration = 2000;
        mSpeed = mDistance / mDuration;
        bounceTime = 2;
        mFRate = HEIGHT / (0.5f * GRAVITY * (mDuration / 4000f) * (mDuration / 4000f));
        mSRate = HEIGHT / (0.5f * GRAVITY * (mDuration / 4000f) * (mDuration / 4000f));
    }

    // 获取单实例的球
    public synchronized static SmallYellowBall getInstance() {
        if (mBall == null) {
            mBall = new SmallYellowBall();
        }
        return mBall;
    }

    public void throwOut() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mDuration);
        valueAnimator.setDuration((long) mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curTime = (float) animation.getAnimatedValue();
                curDistance = curTime * mSpeed;
                if (curDistance <= 25) {
                    mCurY = mOriginY + 0.5f * GRAVITY * (curTime / 1000f) * (curTime / 1000f) * mFRate;
                } else if (curDistance <= 50) {
                    curTime -= mDuration / 4;
                    mCurY = mOriginY + HEIGHT - 0.5f * GRAVITY * (curTime / 1000f) * (curTime / 1000f) * mSRate;
                } else if (curDistance <= 75) {
                    curTime -= mDuration / 2;
                    mCurY = mOriginY + 0.5f * GRAVITY * (curTime / 1000f) * (curTime / 1000f) * mSRate;
                } else {
                    curTime -= mDuration * 3 / 4;
                    mCurY = mOriginY + HEIGHT - 0.5f * GRAVITY * (curTime / 1000f) * (curTime / 1000f) * mFRate;
                }
                mCurX = mOriginX + curDistance;

            }
        });
        valueAnimator.start();
    }

    public void drawSelf(Canvas canvas) {
        if (isShow) {
            canvas.drawCircle(mCurX, mCurY, mRadius, mPaint);
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
