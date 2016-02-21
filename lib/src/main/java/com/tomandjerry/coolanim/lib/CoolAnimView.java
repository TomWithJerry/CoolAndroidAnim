package com.tomandjerry.coolanim.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yanxing on 16/1/19.
 */
public class CoolAnimView extends View {
    public final static int WIDTH_DEFAULT = 300;
    public final static int HEIGHT_DEFAULT = 300;

    private PelletManager mPelletMng;
    private ValueAnimator mAnimator;

    private boolean isInit = false;

    public CoolAnimView(Context context) {
        this(context, null);
    }

    public CoolAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoolAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureDimension(WIDTH_DEFAULT, widthMeasureSpec);
        int height = measureDimension(HEIGHT_DEFAULT, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public int measureDimension(int defaultSize, int measureSpec) {
        int result;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize;   //UNSPECIFIED
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
    public void init() {
        mPelletMng = new PelletManager();
        mPelletMng.initComponents();

        mAnimator = ValueAnimator.ofInt(0, 1).setDuration(16);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                invalidate();
            }
        });
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInit){
            init();
            isInit = true;
        }
        mPelletMng.drawTheWorld(canvas);
    }
}
