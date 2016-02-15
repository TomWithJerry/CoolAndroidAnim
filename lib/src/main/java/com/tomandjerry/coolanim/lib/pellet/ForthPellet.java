package com.tomandjerry.coolanim.lib.pellet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;

/**
 * Created by yanxing on 16/1/29.
 */
public class ForthPellet extends Pellet {
    private int GREEN = Color.parseColor("#5eb752");
    private int YELLOW = Color.parseColor("#fde443");
    private int BLUE = Color.parseColor("#21bbfc");
    private int RED = Color.RED;
    private int TRANSPARENT = Color.parseColor("#0021bbfc");
    // 第一个圆或圆环或圆弧的半径和画笔大小
    private float mFiCurR;
    private float mFiStrokeWidth;
    // 第二个圆或圆环或圆弧的半径和画笔大小
    private float mSeCurR;
    private float mSeStrokeWidth;
    // 正常圆(能停留的)最大的直径
    private int STANDARD_MAX_R;
    // 正常圆(能停留的)最小的直径
    private int STANDARD_MIN_R;
    // 前一个值
    private float mPreValue;
    // 当前值
    private float mCurValue;
    // 差值
    private float mDifValue;
    private Paint mPaint;
    private int mState = 1;
    private RectF mRectF;
    private float mAngle;
    private float mSweepAngle = 360;
    // 蓝色弧线的颜色渲染
    private SweepGradient mSweepGradient;
    private AnimatorSet mAnimatorSet;
    private boolean isStart = false;
    //
    private RadialGradient mRadialGradient;
    // 时间值
    private int mDuration1 = 2000;
    private int mDuration2 = 6000;
    private int mDuration3 = 1000;
    private int mDuration4 = 1000;
    private int mDuration5 = 6000;


    public ForthPellet(int x, int y) {
        super(x, y);
    }

    @Override
    protected void initConfig() {
        STANDARD_MAX_R = 50;
        STANDARD_MIN_R = 15;
        mFiCurR = STANDARD_MAX_R;
        mFiStrokeWidth = 33;
        mSeCurR = 0;
        mSeStrokeWidth = mSeCurR;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mAnimatorSet = new AnimatorSet();

        ValueAnimator anim1 = createInsideCircleAnim();
        ValueAnimator anim2 = createRotateAnim();
        ValueAnimator anim3 = createWaitAndFillAnim();
        ValueAnimator anim4 = createSmallBiggerAnim();
        ValueAnimator anim5 = createColorfulAnim();

        mAnimatorSet.playSequentially(anim1, anim2, anim3, anim4, anim5);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorSet.start();
                STANDARD_MAX_R = 50;
                STANDARD_MIN_R = 15;
                mFiCurR = STANDARD_MAX_R;
                mFiStrokeWidth = 33;
                mSeCurR = 0;
                mSeStrokeWidth = mSeCurR;
            }
        });
        mAnimatorSet.start();
    }

    @Override
    public void startAnimation() {

    }

    /**
     * 第一步:黄色圆环,内部绿色小球由无变大到圆环,仍然在黄色圆弧内
     */
    public ValueAnimator createInsideCircleAnim() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, STANDARD_MIN_R, STANDARD_MIN_R + 10);
        anim.setDuration(mDuration1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mState = 1;
                mCurValue = (float) animation.getAnimatedValue();
                mDifValue = mCurValue - mPreValue;

                if (mCurValue <= STANDARD_MIN_R) {
                    // 绿色小球变大
                    mSeCurR = (float) animation.getAnimatedValue();
                    mSeStrokeWidth = mSeCurR;
                } else {
                    // 绿色小球内部变大,成为空心
                    mSeCurR += mDifValue;
                    mSeStrokeWidth = mSeCurR;
                    // 黄色圆弧变大
                    mFiCurR -= mDifValue / 2;
                    mFiStrokeWidth -= mDifValue;
                }
                mPreValue = mCurValue;
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        return anim;
    }


    /**
     * 第二步:黄色圆环旋转为圆弧,带有一点蓝色的尾巴,逐渐缩短,绿色圆膨胀一些,变为圆环
     */
    protected ValueAnimator createRotateAnim() {
        final int gap = 10;
        final float rate = gap / 180f;
        final int[] colors = new int[]{TRANSPARENT, TRANSPARENT, BLUE};
        final float[] positions = new float[]{0, 0.8f, 1};

        mAngle = 0;
        mSweepAngle = 360;
        ValueAnimator animator = ValueAnimator.ofInt(0, 180 + 20);
        animator.setDuration(mDuration2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mState = 2;
                mCurValue = (int) animation.getAnimatedValue();
                mDifValue = mCurValue - mPreValue;
                if (mCurValue <= 180) {
                    mSeCurR += mDifValue * rate / 2;
                    mSeStrokeWidth -= mDifValue * rate;

                    mAngle = mCurValue * 3;
                    mSweepAngle -= mDifValue * 2;
                } else {
                    positions[1] += (mCurValue - 180) * 0.01f;
                    mSweepGradient = new SweepGradient(getCurX(), getCurY(), colors, positions);
                }

                mPreValue = mCurValue;
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                // 初始化变量
                mCurValue = 0;
                mPreValue = 0;
                mSweepAngle = 360;
                mRectF = new RectF(getCurX() - (STANDARD_MAX_R - 5 - mFiStrokeWidth / 2),
                        getCurY() - (STANDARD_MAX_R - 5 - mFiStrokeWidth / 2),
                        getCurX() + (STANDARD_MAX_R - 5 - mFiStrokeWidth / 2),
                        getCurY() + (STANDARD_MAX_R - 5 - mFiStrokeWidth / 2));
                colors[2] = BLUE;
                positions[1] = 0.8f;
                mSweepGradient = new SweepGradient(getCurX(), getCurY(), colors, positions);
            }
        });
        return animator;
    }

    // 第三步:黄色圆弧彻底消失,绿色圆弧变为实心圆
    protected ValueAnimator createWaitAndFillAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 10);
        animator.setDuration(mDuration3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isStart) {
                    return;
                }
                mState = 2;
                mCurValue = (int) animation.getAnimatedValue();
                mDifValue = mCurValue - mPreValue;

                mSeCurR -= mDifValue / 2f;
                mSeStrokeWidth += mDifValue;
                mPreValue = mCurValue;
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isStart = true;
                mCurValue = 0;
                mPreValue = 0;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isStart = false;
            }
        });
        return animator;
    }

    // 第四步:填充,等待一会,缩小然后放大
    protected ValueAnimator createSmallBiggerAnim() {

        ValueAnimator animator = ValueAnimator.ofInt(0, STANDARD_MIN_R + STANDARD_MAX_R);
        animator.setDuration(mDuration4);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isStart) {
                    return;
                }
                mState = 2;
                mCurValue = (int) animation.getAnimatedValue();
                mDifValue = mCurValue - mPreValue;
                if (mCurValue <= STANDARD_MIN_R) {
                    mSeCurR -= mDifValue / 2f;
                    mSeStrokeWidth -= mDifValue / 2f;
                } else {
                    mSeCurR += mDifValue / 2f;
//                    mSeStrokeWidth -= mDifValue;
                }
                mPreValue = mCurValue;
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isStart = true;
                mCurValue = 0;
                mPreValue = 0;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isStart = false;
            }
        });
        return animator;
    }

    /**
     * 第五步:圆环颜色由内向外拓展为蓝色.颜色由浅逐渐变深,一半的时候,内拓为红色,再到黄色,最后全黄,回到第一步
     */
    protected ValueAnimator createColorfulAnim() {
        final float[] positions1 = new float[]{0f, 0f};
        final float[] positions2 = new float[]{0f, 0f, 0f, 0f, 0f, 0f};
        final int[] colors1 = new int[]{YELLOW, GREEN};
        final int[] colors2 = new int[]{YELLOW, YELLOW, RED, RED, BLUE, BLUE};
        ValueAnimator animator = ValueAnimator.ofInt(0, 100, 200, 300, 400);
        animator.setDuration(mDuration5);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isStart) {
                    return;
                }
                mState = 3;
                mCurValue = (int) animation.getAnimatedValue();
                if (mCurValue <= 100) {
                    positions1[0] = mCurValue / 100f;
                    positions1[1] = positions1[0];
                    mRadialGradient = new RadialGradient(getCurX(), getCurY(), mSeCurR + mSeStrokeWidth / 2, colors1, positions1, Shader.TileMode.CLAMP);
                } else if (mCurValue <= 200) {
                    colors1[0] = BLUE;
                    colors1[1] = YELLOW;
                    positions1[0] = (mCurValue - 100) / 100f;
                    positions1[1] = positions1[0];
                    mRadialGradient = new RadialGradient(getCurX(), getCurY(), mSeCurR + mSeStrokeWidth / 2, colors1, positions1, Shader.TileMode.CLAMP);
                } else {
                    positions2[5] = (mCurValue - 200) / 100f;
                    positions2[4] = positions2[5] - 0.2f;
                    positions2[3] = positions2[4];
                    positions2[2] = positions2[3] - 0.4f;
                    positions2[1] = positions2[2];
                    positions2[0] = positions2[1] - 0.6f;
                    mRadialGradient = new RadialGradient(getCurX(), getCurY(), mSeCurR + mSeStrokeWidth / 2, colors2, positions2, Shader.TileMode.CLAMP);
                }
            }
        });
        animator.addListener(new
                         AnimatorListenerAdapter() {
                             @Override
                             public void onAnimationStart(Animator animation) {
                                 mState = 3;
                                 isStart = true;
                                 mCurValue = 0;
                                 mPreValue = 0;
                                 positions1[1] = 0f;
                                 positions1[0] = 0f;
                                 positions2[5] = 0f;
                                 positions2[4] = 0f;
                                 positions2[3] = 0f;
                                 positions2[2] = 0f;
                                 positions2[1] = 0f;
                                 positions2[0] = 0f;
                                 colors1[0] = YELLOW;
                                 colors1[1] = GREEN;
                                 mRadialGradient = new RadialGradient(getCurX(), getCurY(), mSeCurR + mSeStrokeWidth, colors1, positions1, Shader.TileMode.CLAMP);
                             }

                             @Override
                             public void onAnimationEnd(Animator animation) {
                                 isStart = false;
                             }
                         });
        return animator;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        switch (mState) {
            case 1:
                // 绘制黄色圆环或圆
                mPaint.setStrokeWidth(mFiStrokeWidth);
                mPaint.setColor(YELLOW);
                canvas.drawCircle(getCurX(), getCurY(), mFiCurR - mFiStrokeWidth / 2, mPaint);
                // 绘制绿色圆环
                mPaint.setStrokeWidth(mSeStrokeWidth);
                mPaint.setColor(GREEN);
                canvas.drawCircle(getCurX(), getCurY(), mSeCurR - mSeStrokeWidth / 2, mPaint);
                break;
            case 2:

                // 绘制蓝色弧线
                canvas.save();
                canvas.rotate(mAngle - 90, getCurX(), getCurY());
                mPaint.setShader(mSweepGradient);
                canvas.drawCircle(getCurX(), getCurY(), mFiCurR - mFiStrokeWidth / 2, mPaint);
                canvas.restore();

                // 绘制黄色弧线
                mPaint.setShader(null);
                mPaint.setStrokeWidth(mFiStrokeWidth);
                mPaint.setColor(YELLOW);
                canvas.drawArc(mRectF, mAngle - 90, mSweepAngle, false, mPaint);

                // 绘制绿色圆环
                mPaint.setStrokeWidth(mSeStrokeWidth);
                mPaint.setColor(GREEN);
                canvas.drawCircle(getCurX(), getCurY(), mSeCurR - mSeStrokeWidth / 2, mPaint);
                break;
            case 3:
                // 绘制绿色圆环
                mPaint.setStrokeWidth(mSeStrokeWidth);
                mPaint.setShader(mRadialGradient);
                canvas.drawCircle(getCurX(), getCurY(), mSeCurR - mSeStrokeWidth / 2, mPaint);
                mPaint.setShader(null);
                break;
            default:
                break;
        }

    }
}
