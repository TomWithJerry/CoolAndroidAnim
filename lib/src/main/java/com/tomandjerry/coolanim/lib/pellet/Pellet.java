package com.tomandjerry.coolanim.lib.pellet;

import android.graphics.Canvas;

/**
 * Created by yanxing on 16/1/19.
 */
public class Pellet {
    private int mCurX;
    private int mCurY;
    private int mDelayTime;

    public Pellet(int x, int y) {
        this.mCurX = x;
        this.mCurY = y;
        initConfig();
    }

    protected void initConfig() {

    }

    public void drawSelf(Canvas canvas) {

    }

    public void startAnimation() {

    }

    public void moveTo(int x, int y) {

    }

    /**
     * 演变成文字
     */
    public void turnToText() {

    }

    public void setDelay(int delay) {
        mDelayTime = delay;
    }

    public int getCurX() {
        return mCurX;
    }

    public int getCurY() {
        return mCurY;
    }
}
