package com.tomandjerry.coolanim.lib.letter;

import android.graphics.Canvas;

/**
 * Created by yanxing on 16/2/18.
 */
public abstract class Letter {

    protected int mCurX;
    protected int mCurY;
    protected int mDuration = 2000;

    public Letter(int x, int y) {
        mCurX = x;
        mCurY = y;
    }

    public void startAnim() {

    }

    public void drawSelf(Canvas canvas) {

    }
}
