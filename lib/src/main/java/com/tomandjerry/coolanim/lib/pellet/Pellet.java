package com.tomandjerry.coolanim.lib.pellet;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by yanxing on 16/1/19.
 * Change by weicheng on 16/1/31.
 */
public abstract class Pellet {

    protected static final int MAX_RADIUS_CIRCLE = 60;//暂定所有球的最大半径

    protected int mCurX;
    protected int mCurY;

    //保存原始X位置
    protected int mPerX = mCurX;
    //是否结束
    protected boolean mIsEnd = false;
    //是否已经启动结束动画
    protected boolean mIsEndAnimStart = true;
    //结束时小球移动距离
    protected int mEndMovingLength;
    //结束动画时间长度
    protected int mDuration = 4000;

    protected AnimatorStateListen mAnimatorStateListen;

    public Pellet(int x, int y) {
        this.mCurX = x;
        this.mCurY = y;
        this.mPerX = x;
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
