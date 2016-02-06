package com.tomandjerry.coolanim.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.view.View;

import com.tomandjerry.coolanim.lib.pellet.ForthPellet;
import com.tomandjerry.coolanim.lib.pellet.Pellet;
import com.tomandjerry.coolanim.lib.pellet.ThirdPellet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanxing on 16/1/19.
 */
public class PelletManager {
    public List<Pellet> pellets;
    private View mView;

    public PelletManager(View view) {
        mView = view;
        pellets = new ArrayList<>();
        pellets.add(new ThirdPellet(view.getWidth() / 2, view.getHeight() / 2));
        pellets.add(new ForthPellet(view.getWidth() / 2 + 100, view.getHeight() / 2));
        startTimer();
    }

    public void startTimer() {
        ValueAnimator timer = ValueAnimator.ofInt(0, 1);
        timer.setDuration(16);
        timer.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                mView.invalidate();
            }
        });
        timer.setRepeatMode(ValueAnimator.RESTART);
        timer.setRepeatCount(ValueAnimator.INFINITE);
        timer.start();
    }

    public void drawTheWorld(Canvas canvas) {
        for (Pellet pellet : pellets) {
            pellet.drawSelf(canvas);
        }
    }
}
