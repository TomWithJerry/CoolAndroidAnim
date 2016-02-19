package com.tomandjerry.coolanim.lib;

import android.graphics.Canvas;

import com.tomandjerry.coolanim.lib.letter.ILetter;
import com.tomandjerry.coolanim.lib.letter.Letter;
import com.tomandjerry.coolanim.lib.pellet.ForthPellet;
import com.tomandjerry.coolanim.lib.pellet.Pellet;
import com.tomandjerry.coolanim.lib.pellet.SmallYellowBall;
import com.tomandjerry.coolanim.lib.pellet.ThirdPellet;
import com.tomandjerry.coolanim.lib.pellet.FirstPellet;
import com.tomandjerry.coolanim.lib.pellet.SecondPellet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiCheng on 16/1/19.
 */
public class PelletManager {

    private List<Pellet> mPellets;
    private List<Letter> mLetters;
    private SmallYellowBall mBall;

    public PelletManager() {
        mPellets = new ArrayList<>();
        mLetters = new ArrayList<>();
    }

    public void initPellets(){
        this.setPellet(new FirstPellet(200, 300));
        this.setPellet(new ThirdPellet(400, 300));
        this.setPellet(new SecondPellet(300, 300));
        this.setPellet(new ForthPellet(500, 300));
        this.setLetter(new ILetter(50, 500));
        mBall = SmallYellowBall.getInstance();

        showText();
    }

    public void showText() {
        for (Letter l : mLetters) {
            l.startAnim();
        }
    }

    public void setPellet(Pellet pellet){
        if(pellet != null){
            mPellets.add(pellet);
            pellet.prepareAnim();
        }
    }

    public void setLetter(Letter letter) {
        if (letter != null) {
            mLetters.add(letter);
        }
    }

    public void drawTheWorld(Canvas canvas) {
        for (Pellet p : mPellets){
            p.drawSelf(canvas);
        }

        for (Letter l : mLetters) {
            l.drawSelf(canvas);
        }
        // 绘制小球
        mBall.drawSelf(canvas);
    }

}
