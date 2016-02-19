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
    private final int THIRD_CIRCLE_MIN_STROKEWIDTH = 15;//黄色圆的线粗最小值
    private final int DIVIDE_DEGREES = 35;

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

    private ValueAnimator firAnimator;
    private ValueAnimator firAnimator2;
    private ValueAnimator secAnimator;
    private ValueAnimator thirAnimator;
    private ValueAnimator fourthAnimator;
    private ValueAnimator fifthAnimator;
    private ValueAnimator sixthAnimator;
    private ValueAnimator sevenAnimator;

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
        firAnimator = ValueAnimator.ofInt(FIRST_CIRCLE_START_RADIUS, FIRST_CIRCLE_START_RADIUS + 10, FIRST_CIRCLE_START_RADIUS).setDuration(800);
        firAnimator.setRepeatCount(0);
        firAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFirCirRadius = (int) animation.getAnimatedValue();
                    }
                }
        );
        firAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationRepeat(animation);
                mPreFirCirRadius = 0;//将顶层的蓝色圆半径置0;
                mThiCirRadius = 0;
                mThiCirStrokeWidth = 0;
                secAnimator.start();
                thirAnimator.start();
            }
        });
        firAnimator.start();

        //环绕行星的大小变化
        secAnimator = ValueAnimator.ofFloat(0, 1, 1, 0).setDuration(1600);
        secAnimator.setRepeatCount(0);
        secAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mSecCirRadius = (int) ((float) animation.getAnimatedValue() * SECOND_CIRCLE_MAX_RADIUS);
                    }
                }
        );

        //环绕行星的旋转角度
        thirAnimator = ValueAnimator.ofInt(90, 180).setDuration(1600);
        thirAnimator.setRepeatCount(0);
        thirAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mAroundCirDegrees = (int) animation.getAnimatedValue();
                    }
                }
        );
        thirAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fourthAnimator.start();
                firAnimator2.start();
            }
        });

        //环绕结束蓝色圆有一个变大的动作
        firAnimator2 = ValueAnimator.ofInt(FIRST_CIRCLE_START_RADIUS, FIRST_CIRCLE_START_RADIUS + 10, 20, 20).setDuration(1600);
        firAnimator2.setRepeatCount(0);
        firAnimator2.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFirCirRadius = (int) animation.getAnimatedValue();
                    }
                }
        );

        //环绕结束黄色的圆出现覆盖并缩小
        fourthAnimator = ValueAnimator.ofFloat(0, 1).setDuration(1600);
        fourthAnimator.setRepeatCount(0);
        fourthAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {

                        mThiCirRadius = (int) (20 + 80 * (1 - (float) animation.getAnimatedValue()));//变化范围60～20.
                        mThiCirStrokeWidth = 15 + (int) ((float) animation.getAnimatedValue() * THIRD_CIRCLE_MIN_STROKEWIDTH);
                    }
                }
        );
        fourthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fifthAnimator.start();
                sixthAnimator.start();
            }
        });

        //弧形动画旋转启动
        fifthAnimator = ValueAnimator.ofInt(-120, 750).setDuration(1600);
        fifthAnimator.setRepeatCount(0);
        fifthAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mAroundArcDegrees = (int) animation.getAnimatedValue();
                    }
                }
        );
        fifthAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        //弧形长度变化
        sixthAnimator = ValueAnimator.ofInt(0, 180, 0).setDuration(1600);
        sixthAnimator.setRepeatCount(0);
        sixthAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mAroundArcLength = (int) animation.getAnimatedValue();
                    }
                }
        );
        sixthAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        sixthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                sevenAnimator.start();
            }
        });

        sevenAnimator = ValueAnimator.ofInt(0, FIRST_CIRCLE_START_RADIUS).setDuration(800);
        sevenAnimator.setRepeatCount(0);
        sevenAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mPreFirCirRadius = (int) animation.getAnimatedValue();
                    }
                }
        );
        sevenAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                firAnimator.start();
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
        for (int i = 0; i < 9; i++) {
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
        //TODO 需要修改实现
        mPaint.setColor(Color.BLUE);
        canvas.save();
        canvas.rotate(mAroundArcDegrees, getCurX(), getCurY());
        mPaint.setStrokeWidth(20);
        canvas.drawArc(mRectF, 0, mAroundArcLength, false, mPaint);
        canvas.restore();

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(mPreFirCirRadius);
        canvas.drawCircle(getCurX(), getCurY(), mPreFirCirRadius / 2, mPaint);
    }
}
