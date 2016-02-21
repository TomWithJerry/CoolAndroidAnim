package com.tomandjerry.coolanim.lib.pellet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.tomandjerry.coolanim.lib.Config;

/**
 * Created by weicheng on 16/1/31.
 */
public class SecondPellet extends Pellet {

    //移动长度
    private final int MOVE_MAX_LINTH = 120;
    //环绕线粗，应为AROUND_POINT_RADIUS*2;
    private final int LINE_STROKE_LENGTH = 8;
    //环绕行星(圆)半径
    private final int AROUND_POINT_RADIUS = 4;

    //红球外半径,比最大半径稍微一点
    private int mRedCirCleRadius = 50;
    //红球内半径
    private int mRedCirStrokeFactor;
    //环绕线内点
    private int mAroundLineInsideP = MAX_RADIUS_CIRCLE;
    //环绕线外点
    private int mAroundLineOutsideP = MAX_RADIUS_CIRCLE;
    //环绕角度
    private int mAroundLineDegrees = 0;
    //环绕偏离中心的坐标
    private int mAroundPointY = 0;


    //这个方式待修改完善。
    private int mFirYellowCirRadius;
    //黄球左偏移
    private int mLineLeftOffset;
    //黄球右偏移
    private int mLineRightOffset;
    private int mLineStrokeWidth = 120;

    private boolean mIsCirLineShow = true;
    //环绕行星可见性
    private boolean mIsAroundPointV = false;

    //第二个黄色球
    private int mSecYellowCirRadius;

    private Paint mPaint;
    private ValueAnimator firAnimator;
    private ValueAnimator secAnimator;
    private ValueAnimator thirdAnimator;

    private int mEndCirIRadius;
    private int mEndCirMRadius;
    private int mEndCirORadius;
    private ValueAnimator mEndAnimator;
    // 正在结束,只绘制结束动画
    private boolean isMoveEnd = false;

    public SecondPellet(int x, int y) {
        super(x, y);
    }

    @Override
    protected void initConfig() {
        mEndMovingLength = -25;

        mPaint = new Paint();
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void initAnim() {
        //黄色圆出现->黄色圆变成圆边矩形
        firAnimator = ValueAnimator.ofFloat(0, 1, 2).setDuration(600);
        firAnimator.setRepeatCount(0);
        firAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                if (factor < 1) {
                    mFirYellowCirRadius = 20 + (int) ((float) animation.getAnimatedValue() * (MAX_RADIUS_CIRCLE - 20));
                    mRedCirStrokeFactor = (int) (14 + (float) animation.getAnimatedValue() * 20);
                } else {
                    mLineLeftOffset = (int) (MOVE_MAX_LINTH * (factor - 1));
                }
            }
        });
        firAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFirYellowCirRadius = 0;
                mRedCirCleRadius = 60;
                mRedCirStrokeFactor = 50;
                mSecYellowCirRadius = 0;
                secAnimator.start();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mIsCirLineShow = true;
            }
        });
//        firAnimator.start();


        secAnimator = ValueAnimator.ofFloat(0, 0.5f, 1, 1.25f, 1.5f, 1.75f, 2).setDuration(2400);
        secAnimator.setRepeatCount(0);
        secAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                float changeValue = 0;
                if (factor <= 1.0f) {
                    mLineRightOffset = (int) (MOVE_MAX_LINTH * factor);
                } else {
                    changeValue = 2 - factor;
                    //线变短变成圆一会消失，交给第三个小球处理
                    mIsCirLineShow = false;
                    mAroundLineDegrees = (int) (90 * changeValue);
                    if (factor < 1.5) {
                        changeValue = (1.5f - factor) * 2;
                        mAroundLineInsideP = MAX_RADIUS_CIRCLE - (int) (MAX_RADIUS_CIRCLE * changeValue);
                        //红球缩小至20时就停止缩小
                        if (mRedCirCleRadius > 20) {
                            mRedCirStrokeFactor = (int) (50 * changeValue);
                            mRedCirCleRadius = MAX_RADIUS_CIRCLE - mAroundLineInsideP;
                        }
                        //中心小黄球出现
                        mFirYellowCirRadius = (int) (10 * (1 - changeValue));
                    }
                    //底层黄球出现（环）
                    if (factor > 1.5) {
                        changeValue = (factor - 1.5f) * 2;
                        mIsAroundPointV = true;
                        mAroundPointY = (int) ((MAX_RADIUS_CIRCLE / 2) * changeValue);
                        mSecYellowCirRadius = (int) (30 * changeValue);
                    }
                }
            }
        });
        secAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                thirdAnimator.start();
            }
        });

        //黄球（环）放大，再缩小，缩小过程伴随着环绕行星点变线向内伸长。
        thirdAnimator = ValueAnimator.ofFloat(0, 5).setDuration(3000);
        thirdAnimator.setRepeatCount(0);
        thirdAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                float changeValue = 0;
                if (factor < 1.0f) {
                    //黄球放大
                    mSecYellowCirRadius = (int) (30 + 15 * (factor));
                } else if (factor < 2.0f) {
                    changeValue = 2 - factor;
                    //黄球缩小
                    mSecYellowCirRadius = (int) (45 * changeValue);
                    //点成线
                    mAroundLineInsideP = MAX_RADIUS_CIRCLE - (int) (MAX_RADIUS_CIRCLE / 3 * (1 - changeValue));
                } else if (factor < 2.25f) {
                    //停顿一下。
                } else if (factor < 3.0f) {
                    changeValue = (3.0f - factor) * 4 / 3;
                    //线内聚
                    mIsAroundPointV = false;
                    mAroundLineOutsideP = (int) (MAX_RADIUS_CIRCLE * changeValue);
                    mAroundLineInsideP = (int) (MAX_RADIUS_CIRCLE / 3 * 2 * changeValue);
                    //黄球缩小,红球放大
                    mFirYellowCirRadius = (int) (10 * changeValue);
                    mRedCirStrokeFactor = 30;//(int) (16 + 10 * (1 - (3.0f - factor) * 4 / 3));
                    mRedCirCleRadius = (int) (20 + 15 * (1 - changeValue));
                } else if (factor < 4.0f) {
                    //停顿一下
                } else if (factor < 5.0f) {
                    changeValue = factor - 4.0f;
                    //红球（环）内边外扩，内部黄球放大
                    mFirYellowCirRadius = (int) (20 * changeValue);
                    mRedCirStrokeFactor = (int) (30 - 16 * changeValue);
                    mRedCirCleRadius = (int) (35 + 8 * changeValue);
                }
            }
        });
        thirdAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAroundLineInsideP = MAX_RADIUS_CIRCLE;
                mAroundLineOutsideP = MAX_RADIUS_CIRCLE;
                mLineLeftOffset = 0;
                mLineRightOffset = 0;
//                firAnimator.start();

                if (mAnimatorStateListen != null) {
                    mAnimatorStateListen.onAnimatorEnd();
                }
            }
        });
    }

    @Override
    public void startAnim() {
        firAnimator.start();
    }

    @Override
    protected void initEndAnim() {
        mEndAnimator = ValueAnimator.ofFloat(0, 1, 2).setDuration(mDuration);
//        mEndAnimator.setRepeatCount(2);
        mEndAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float zoroToOne = (float) animation.getAnimatedValue();
                if (zoroToOne <= 1.0f) {
                    mCurX = (int) (mPerX + zoroToOne * mEndMovingLength);
                    mEndCirIRadius = (int) (MAX_RADIUS_CIRCLE * zoroToOne);
                    if (zoroToOne <= 0.5f) {
                        zoroToOne = 2 * zoroToOne;
                    } else {
                        zoroToOne = 1 - 2 * (zoroToOne - 0.5f);
                    }
                    mEndCirMRadius = (int) (MAX_RADIUS_CIRCLE * zoroToOne);
                } else {
                    if (!isMoveEnd) {
                        isMoveEnd = true;
                        if (mAnimatorStateListen != null) {
                            mAnimatorStateListen.onMoveEnd();
                        }
                    }
                    zoroToOne = 2 - zoroToOne;
                    mEndCirIRadius = (int) (MAX_RADIUS_CIRCLE * zoroToOne);
                    if (zoroToOne >= 0.5f) {
                        zoroToOne = (1.0f - zoroToOne) * 2;
                    } else {
                        zoroToOne = zoroToOne * 2;
                    }
                    mEndCirORadius = (int) (MAX_RADIUS_CIRCLE * zoroToOne);
                }
            }
        });
    }

    @Override
    public void drawSelf(Canvas canvas) {

        if (!isMoveEnd) {
            if (mPaint.getStyle() != Paint.Style.STROKE) {
                mPaint.setStyle(Paint.Style.STROKE);
            }
            mPaint.setColor(Config.YELLOW);
            mPaint.setStrokeWidth(mSecYellowCirRadius);
            canvas.drawCircle(getCurX(), getCurY(), mSecYellowCirRadius / 2, mPaint);

            mPaint.setColor(Config.RED);
            mPaint.setStrokeWidth(mRedCirStrokeFactor);
            canvas.drawCircle(getCurX(), getCurY(), mRedCirCleRadius - mRedCirStrokeFactor / 2, mPaint);

            mPaint.setColor(Config.YELLOW);
            mPaint.setStrokeWidth(mFirYellowCirRadius);
            canvas.drawCircle(getCurX(), getCurY(), mFirYellowCirRadius / 2, mPaint);


            //黄色圆移动，等同圆角的线
            //TODO 结尾问题。
            if (mIsCirLineShow) {
                mPaint.setStrokeWidth(mLineStrokeWidth);
                canvas.drawLine(getCurX() + mLineRightOffset, getCurY(), getCurX() + mLineLeftOffset, getCurY(), mPaint);
            }

            drawAroundLine(canvas);
            if (mIsAroundPointV == true) {
                drawAroundPoint(canvas);
            }
        }

        if (mIsEnd) {
            if (!mIsEndAnimStart) {
                mEndAnimator.start();
                mIsEndAnimStart = true;
            }
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Config.GREEN);
            canvas.drawCircle(getCurX(), getCurY(), mEndCirIRadius, mPaint);
            mPaint.setColor(Config.YELLOW);
            canvas.drawCircle(getCurX(), getCurY(), mEndCirMRadius, mPaint);
            mPaint.setColor(Config.RED);
            canvas.drawCircle(getCurX(), getCurY(), mEndCirORadius, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            return;
        }

    }

    private void drawAroundLine(Canvas canvas) {
        mPaint.setColor(Config.RED);
        mPaint.setStrokeWidth(LINE_STROKE_LENGTH);
        for (int i = 0; i < 8; i++) {
            canvas.save();
            canvas.rotate(45 * i + mAroundLineDegrees, getCurX(), getCurY());
            canvas.drawLine(getCurX(), getCurY() - mAroundLineOutsideP, getCurX(), getCurY() - mAroundLineInsideP, mPaint);
            canvas.restore();
        }
    }

    private void drawAroundPoint(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 8; i++) {
            mPaint.setAlpha(160);
            canvas.save();
            canvas.rotate(45 * i + mAroundLineDegrees, getCurX(), getCurY());
            canvas.drawCircle(getCurX(), getCurY() - MAX_RADIUS_CIRCLE / 2 - mAroundPointY, AROUND_POINT_RADIUS, mPaint);
            canvas.restore();
        }
    }
}
