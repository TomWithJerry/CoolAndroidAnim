package com.tomandjerry.coolanim.lib.letter;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tomandjerry.coolanim.lib.Config;

/**
 * Created by Weiwu on 16/2/19.
 */
public class ALetter extends Letter{

    private Paint mPaint;

    private int mDegrees;
    private ValueAnimator mFirAnimator;

    public ALetter(int x, int y) {
        super(x, y);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Config.WHITE);

    }

    @Override
    public void startAnim() {

    }

    @Override
    public void drawSelf(Canvas canvas) {
        super.drawSelf(canvas);
    }
}
