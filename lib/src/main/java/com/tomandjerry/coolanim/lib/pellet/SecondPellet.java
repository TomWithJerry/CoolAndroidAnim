package com.tomandjerry.coolanim.lib.pellet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by weicheng on 16/1/31.
 */
public class SecondPellet extends Pellet {

    private final int MOVE_MAX_LINTH = 200;//移动长度
    private final int LINE_STROKE_LENGTH = 8;//应为AROUND_POINT_RADIUS*2;
    private final int AROUND_POINT_RADIUS = 4;//环绕行星半径

    private int mFirCirCleRadius = 50;//红球外半径,比最大半径稍微一点
    private int mFirCirStrokeFactor;//红球内半径
    private int mAroundLineLength = MAX_RADIUS_CIRCLE;
    private int mAroundLineDegrees = 0;
    private int mAroundPointY = 0;
    private int mPointAlpha;
    private int mRedCirRadius;

    //这个方式待修改完善。
    private int mFirYellowCirRadius;
    private int mLineLeftOffset;//黄球左偏移
    private int mLineRightOffset;//黄球右偏移
    private int mLineStrokeWidth = 120;

    private boolean mIsAroundPointV = false;//环绕行星可见性

    private Path mPath;


    private int mSecYellowCirRadius;
    private int mCurNum;
    private Paint mPaint;

    private ValueAnimator firAnimator;
    private ValueAnimator secAnimator;
    private ValueAnimator thirAnimator;
    private ValueAnimator fourthAnimator;
    private ValueAnimator fifthAnimator;
    private ValueAnimator sixthAnimator;
    private ValueAnimator sevenAnimator;


    public SecondPellet(int x, int y) {
        super(x, y);
    }

    @Override
    protected void initConfig() {
        mPaint = new Paint();
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mRedCirRadius = 80;
        mFirYellowCirRadius = 100;
        mSecYellowCirRadius = 0;

        //黄色圆出现->黄色圆变成圆边矩形
        firAnimator = ValueAnimator.ofFloat(0, 1, 2).setDuration(1600);
        firAnimator.setRepeatCount(0);
        firAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                if (factor < 1) {
                    mFirYellowCirRadius = 20 + (int) ((float) animation.getAnimatedValue() * (MAX_RADIUS_CIRCLE - 20));
                    mFirCirStrokeFactor = (int) (20 + (float) animation.getAnimatedValue() * 20);
                } else {
                    mLineLeftOffset = (int) (MOVE_MAX_LINTH * (factor - 1));
                }
            }
        });
        firAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFirYellowCirRadius = 0;
                mFirCirCleRadius = 60;
                mFirCirStrokeFactor = 50;
                secAnimator.start();
            }
        });
        firAnimator.start();


        secAnimator = ValueAnimator.ofFloat(0, 0.5f, 1, 1.25f, 1.5f, 1.75f, 2,2.5f,2).setDuration(2400 + 800);
        secAnimator.setRepeatCount(0);
        secAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                if (factor < 1) {
                    mLineRightOffset = (int) (MOVE_MAX_LINTH * factor);
                } else if (factor < 2) {
                    mAroundLineDegrees = (int) (90 * (2 - factor));
                    if (factor < 1.5) {
                        mAroundLineLength = MAX_RADIUS_CIRCLE - (int) (MAX_RADIUS_CIRCLE * (1.5f - factor) * 2);
                        if (mFirCirCleRadius > 20) {//红球缩小至20时就停止缩小
                            mFirCirStrokeFactor = (int) (50 * (1.5 - factor) * 2);
                            mFirCirCleRadius = MAX_RADIUS_CIRCLE - mAroundLineLength;
                        }
                        mFirYellowCirRadius = (int) (10 * (factor - 1) * 2);
                    }
                    if (factor > 1.5) {
                        mIsAroundPointV = true;
                        mAroundPointY = (int) ((MAX_RADIUS_CIRCLE / 2) * (factor - 1.5f) * 2);
                        mSecYellowCirRadius = (int) (30 * (factor - 1.5f) * 2);
                    }
                }else {
                    mSecYellowCirRadius = (int) (30 + 15 * (factor-2));
                }
            }
        });
        secAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAroundLineLength = MAX_RADIUS_CIRCLE;
                mLineLeftOffset = 0;
                mLineRightOffset = 0;
                mIsAroundPointV = false;
                firAnimator.start();
            }
        });

//        thirAnimator = ValueAnimator.ofFloat(0,1,2).setDuration(2400);
//        thirAnimator.setRepeatCount(0);
//        thirAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float factor = (float) animation.getAnimatedValue();
//                //完成后在拆开后续动画
//            }
//        });



    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (mPaint.getStyle() != Paint.Style.STROKE) {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(mSecYellowCirRadius);
        canvas.drawCircle(300, 300, mSecYellowCirRadius / 2, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(mFirCirStrokeFactor);
        canvas.drawCircle(300, 300, mFirCirCleRadius - mFirCirStrokeFactor / 2, mPaint);

        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(mFirYellowCirRadius);
        canvas.drawCircle(300, 300, mFirYellowCirRadius / 2, mPaint);


        //黄色圆移动，等同圆角的线
        mPaint.setStrokeWidth(mLineStrokeWidth);
        canvas.drawLine(300 + mLineRightOffset, 300, 300 + mLineLeftOffset, 300, mPaint);

        drawAroundLine(canvas);
        if (mIsAroundPointV == true) {
            drawAroundPoint(canvas);
        }

    }

    private void drawAroundLine(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(LINE_STROKE_LENGTH);
        for (int i = 0; i < 8; i++) {
            canvas.save();
            canvas.rotate(45 * i + mAroundLineDegrees, getCurX(), getCurY());
            canvas.drawLine(getCurX(), getCurY() - MAX_RADIUS_CIRCLE, getCurX(), getCurY() - mAroundLineLength, mPaint);
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

    @Override
    public void startAnimation() {

    }

    private void setPaintColor() {

    }
}
