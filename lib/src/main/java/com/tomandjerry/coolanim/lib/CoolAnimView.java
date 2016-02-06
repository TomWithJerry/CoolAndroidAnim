package com.tomandjerry.coolanim.lib;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yanxing on 16/1/19.
 */
public class CoolAnimView extends View {
    public final static int WIDTH_DEFAULT = 300;
    public final static int HEIGHT_DEFAULT = 300;

    private PelletManager mPelletMng;
    private boolean isInit = false;

    public CoolAnimView(Context context) {
        super(context);
    }

    public CoolAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoolAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CoolAnimView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        if (isInit) {
            return;
        }
        mPelletMng = new PelletManager(this);
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        mPelletMng.drawTheWorld(canvas);
    }
}
