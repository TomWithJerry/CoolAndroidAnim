package com.tomandjerry.coolanim.lib.letter;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.tomandjerry.coolanim.lib.Config;

/**
 * Created by Weiwu on 16/2/19.
 */
public class ALetter extends Letter {

    private Paint mPaint;

    private RectF mRectF;
    //圆角度起点
    private int mStartAngle = 0;
    //变化角度
    private int mSweepAngle = 0;
    //字母边长
    private int mLength = 120;
    //线粗
    private int mStrokeWidth = 20;
    private ValueAnimator mAnimator;

    private Point mFirPoint;
    private Point mSecPoint;

    public ALetter(int x, int y) {
        super(x, y);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Config.WHITE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        //除去线粗带来的偏差
        int offsetSub = mLength / 2 - mStrokeWidth / 2;
        //对其圆右边内边位置
        mFirPoint = new Point(mCurX + offsetSub, mCurY + mLength / 2);
        mSecPoint = new Point(mFirPoint);
        //圆向内偏移
        mRectF = new RectF(mCurX - offsetSub, mCurY - offsetSub, mCurX + offsetSub, mCurY + offsetSub);

    }

    @Override
    public void startAnim() {
        mAnimator = ValueAnimator.ofFloat(0, 1).setDuration(mDuration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                //竖线
                mSecPoint.y = (int) (mFirPoint.y - mLength * factor);
                if (factor > 0.5f) {
                    //中点处开始画圆
                    float zoroToOne = (factor - 0.5f) * 2;
                    mSweepAngle = -(int) (360 * zoroToOne);
                }
            }
        });
        mAnimator.start();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        //竖线
        canvas.drawLine(mFirPoint.x, mFirPoint.y, mSecPoint.x, mSecPoint.y, mPaint);
        //圆
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mPaint);
    }
}
