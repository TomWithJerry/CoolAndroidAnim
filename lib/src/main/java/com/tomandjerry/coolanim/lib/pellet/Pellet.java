package com.tomandjerry.coolanim.lib.pellet;

import android.graphics.Canvas;

/**
 * Created by yanxing on 16/1/19.
 * Change by weicheng on 16/1/31.
 */
public abstract class Pellet {

    protected static final int MAX_RADIUS_CIRCLE = 60;//暂定所有球的最大半径

    private int mCurX;
    private int mCurY;
    private int mDelayTime;

    public Pellet(int x, int y) {
        this.mCurX = x;
        this.mCurY = y;
    }

    protected void initConfig(){

    };

    protected void initAnim(){

    };

    public Pellet prepareAnim(){
        initConfig();
        initAnim();
        return this;
    }

    public abstract void startAnimation();

    public void drawSelf(Canvas canvas){

    };

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
