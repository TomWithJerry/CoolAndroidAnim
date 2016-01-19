package com.tomandjerry.coolanim.lib;

import android.graphics.Canvas;

/**
 * Created by yanxing on 16/1/19.
 */
public class Pellet {
    private int mCurX;
    private int mCurY;

    public Pellet(int x, int y) {
        this.mCurX = x;
        this.mCurY = y;
    }

    protected void initConfig() {

    }

    public void drawSelf(Canvas canvas) {

    }

    public int getCurX() {
        return mCurX;
    }

    public int getCurY() {
        return mCurY;
    }
}
