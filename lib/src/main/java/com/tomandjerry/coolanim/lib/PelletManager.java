package com.tomandjerry.coolanim.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.view.View;

import com.tomandjerry.coolanim.lib.pellet.FirstPellet;
import com.tomandjerry.coolanim.lib.pellet.Pellet;
import com.tomandjerry.coolanim.lib.pellet.SecondPellet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiCheng on 16/1/19.
 */
public class PelletManager {

    public List<Pellet> pelletList;

    public PelletManager() {
        pelletList = new ArrayList<>();
    }

    public void initPellets(){
        this.setPellet(new FirstPellet(200, 300));
        this.setPellet(new SecondPellet(300, 300));
    }

    public void setPellet(Pellet pellet){
        if(pellet != null){
            pelletList.add(pellet);
            pellet.prepareAnim();
        }
    }

    public void drawTheWorld(Canvas canvas) {
        for (Pellet p :pelletList){
            p.drawSelf(canvas);
        }
    }
}
