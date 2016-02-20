package com.tomandjerry.coolanim.lib.pellet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.tomandjerry.coolanim.lib.Config;

/**
 * Created by weicheng on 16/1/31.
 */
public class FirstPellet extends Pellet {

    //第一个出现初始球的大小
    private final int FIRST_CIRCLE_START_RADIUS = 40;
    //环绕行星最大半径
    private final int SECOND_CIRCLE_MAX_RADIUS = 10;
    //黄色圆的线粗最小值
    private final int THIRD_CIRCLE_MIN_STROKEWIDTH = 15;
    //环绕行星角度间隔
    private final int DIVIDE_DEGREES = 36;

    private Paint mPaint;
    private RectF mRectF;

    //蓝圆半径
    private int mFirCirRadius;
    //环绕行星半径
    private int mSecCirRadius;
    //黄环圆半径
    private int mThiCirRadius;
    //黄环线粗
    private int mThiCirStrokeWidth;
    //环绕圆旋转角度
    private int mAroundCirDegrees;
    //环绕弧线旋转角度
    private int mAroundArcDegrees;
    //环绕弧线长度
    private int mAroundArcLength;
    //最后的蓝圆，对应最开始的蓝圆
    private int mPreFirCirRadius;

    private ValueAnimator mFirAnimator;
    private ValueAnimator mSecAnimator;
    private ValueAnimator mThiAnimator;
    private ValueAnimator mFouAnimator;
    private ValueAnimator mFifAnimator;

    public FirstPellet(int x, int y) {
        super(x, y);
    }

    @Override
    protected void initConfig() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mRectF = new RectF(getCurX() - 45, getCurY() - 45, getCurX() + 45, getCurY() + 45);

    }

    @Override
    protected void initAnim() {
        //初始的蓝色球大小变化
        mFirAnimator = ValueAnimator.ofInt(FIRST_CIRCLE_START_RADIUS, FIRST_CIRCLE_START_RADIUS + 10, FIRST_CIRCLE_START_RADIUS).setDuration(500);
        mFirAnimator.setRepeatCount(0);
        mFirAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFirCirRadius = (int) animation.getAnimatedValue();
                    }
                }
        );
        mFirAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationRepeat(animation);
                mPreFirCirRadius = 0;//将顶层的蓝色圆半径置0;
                mThiCirRadius = 0;
                mThiCirStrokeWidth = 0;
                mSecAnimator.start();
            }
        });
        mFirAnimator.start();

        //环绕行星动画
        mSecAnimator = ValueAnimator.ofFloat(0, 1).setDuration(2000);
        mSecAnimator.setRepeatCount(0);
        mSecAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float zoroToOne = (float) animation.getAnimatedValue();
                        //环绕行星的旋转角度
                        mAroundCirDegrees = (int) (90 + 180 * zoroToOne);
                        if (zoroToOne < 0.5f) {
                            zoroToOne = zoroToOne * 2;
                            //环绕行星的大小变化
                            mSecCirRadius = (int) (zoroToOne * SECOND_CIRCLE_MAX_RADIUS);
                        } else {
                            zoroToOne = (1 - zoroToOne) * 2;
                            mSecCirRadius = (int) (zoroToOne * SECOND_CIRCLE_MAX_RADIUS);
                        }
                    }
                }
        );
        mSecAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFouAnimator.start();
                mThiAnimator.start();
            }
        });

        //环绕结束后蓝色圆有一个变大的动作
        mThiAnimator = ValueAnimator.ofInt(FIRST_CIRCLE_START_RADIUS, FIRST_CIRCLE_START_RADIUS + 10, 20, 20).setDuration(500);
        mThiAnimator.setRepeatCount(0);
        mThiAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFirCirRadius = (int) animation.getAnimatedValue();
                    }
                }
        );

        //环绕结束黄色的圆出现覆盖并缩小
        mFouAnimator = ValueAnimator.ofFloat(0, 1).setDuration(1000);
        mFouAnimator.setRepeatCount(0);
        mFouAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mThiCirRadius = (int) (20 + 80 * (1 - (float) animation.getAnimatedValue()));//变化范围60～20.
                        mThiCirStrokeWidth = 15 + (int) ((float) animation.getAnimatedValue() * THIRD_CIRCLE_MIN_STROKEWIDTH);
                    }
                }
        );
        mFouAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFifAnimator.start();
            }
        });

        //
        //弧形动画旋转启动,弧形角度长度变化＋蓝色球放大动画
        mFifAnimator = ValueAnimator.ofFloat(0, 0.5f, 1, 2).setDuration(2000);
        mFifAnimator.setRepeatCount(0);
        mFifAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float zoroToOne = (float) animation.getAnimatedValue();
                        if (zoroToOne < 0.5f) {
                            mAroundArcDegrees = (int) (-120 + 880 * zoroToOne);
                            zoroToOne = zoroToOne * 2;
                            mAroundArcLength = (int) (180 * zoroToOne);
                        } else if (zoroToOne <= 1.000f) {
                            mAroundArcDegrees = (int) (-120 + 880 * zoroToOne);
                            zoroToOne = (1 - zoroToOne) * 2;
                            mAroundArcLength = (int) (180 * zoroToOne);
                        } else {
                            mAroundArcLength = 0;
                            zoroToOne = zoroToOne - 1;
                            mPreFirCirRadius = (int) (40 * zoroToOne);
                        }
                    }
                }
        );
        mFifAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mFifAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFirAnimator.start();

            }
        });
    }

    @Override
    public void startAnimation() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawSelf(Canvas canvas) {
        super.drawSelf(canvas);
        //蓝色球
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(mFirCirRadius);
        canvas.drawCircle(200, getCurY(), mFirCirRadius / 2, mPaint);

        //环绕行星
        //TODO 关系
        mPaint.setColor(Config.GREEN);
        for (int i = 0; i < 10; i++) {
            canvas.save();
            mPaint.setStrokeWidth(mSecCirRadius);
            canvas.rotate(90 + i * DIVIDE_DEGREES + mAroundCirDegrees, getCurX(), getCurY());
            canvas.drawCircle(getCurX(), getCurY() - 60, mSecCirRadius / 2, mPaint);
            canvas.restore();
        }

        //环绕结束,黄色球包围
        mPaint.setColor(Config.YELLOW);
        mPaint.setStrokeWidth(mThiCirStrokeWidth);
        canvas.drawCircle(getCurX(), getCurY(), mThiCirRadius / 2, mPaint);


        //扇形弧线
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(20);
        canvas.drawArc(mRectF, mAroundArcDegrees, mAroundArcLength, false, mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(mPreFirCirRadius);
        canvas.drawCircle(getCurX(), getCurY(), mPreFirCirRadius / 2, mPaint);
    }
}
