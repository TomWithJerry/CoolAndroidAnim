package com.tomandjerry.coolanim.lib.pellet;

import android.graphics.Canvas;

/**
 * 用animationset完成
 * 两种颜色交替,黄色和绿色,红色
 * 1.从黄色小圆点->大圆点,有一个膨胀回弹的感觉
 * 2.从中心向外射出8条黄线,然后消失,圆点空心.
 * 3.颜色从内往外渐变,内空心圆放大到一定程度,往回填充变为实心圆
 * 4.
 * Created by yanxing on 16/1/29.
 */
public class ThirdPellet extends Pellet {

    public ThirdPellet(int x, int y) {
        super(x, y);
    }

    @Override
    protected void initConfig() {

    }

    @Override
    public void drawSelf(Canvas canvas) {
        super.drawSelf(canvas);
    }
}
