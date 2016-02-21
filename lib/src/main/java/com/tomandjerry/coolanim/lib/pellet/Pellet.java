package com.tomandjerry.coolanim.lib.pellet;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by yanxing on 16/1/19.
 * Change by weicheng on 16/1/31.
 */
public abstract class Pellet {

    protected static final int MAX_RADIUS_CIRCLE = 60;//暂定所有球的最大半径

    private int mCurX;
    private int mCurY;
    protected boolean mIsEnd = false;
    protected boolean mIsEndAnimStart = true;
    protected AnimatorStateListen mAnimatorStateListen;

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
        initEndAnim();
        return this;
    }

    public void startAnim() {

    }

    protected abstract void initEndAnim();

    public void endAnim(){
        mIsEnd = true;
        mIsEndAnimStart = false;
    };

    public void drawSelf(Canvas canvas){

    };

    public void moveTo(int x, int y) {

    }

    public int getCurX() {
        return mCurX;
    }

    public int getCurY() {
        return mCurY;
    }

    public void setAnimatorStateListen(AnimatorStateListen animatorStateListen) {
        this.mAnimatorStateListen = animatorStateListen;
    }

    public interface AnimatorStateListen {
        void onAnimatorEnd();
    }
}
