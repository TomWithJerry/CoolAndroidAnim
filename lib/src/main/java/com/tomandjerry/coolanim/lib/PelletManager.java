package com.tomandjerry.coolanim.lib;

import android.graphics.Canvas;

import com.tomandjerry.coolanim.lib.letter.ALetter;
import com.tomandjerry.coolanim.lib.letter.DLetter;
import com.tomandjerry.coolanim.lib.letter.ILetter;
import com.tomandjerry.coolanim.lib.letter.LLetter;
import com.tomandjerry.coolanim.lib.letter.Letter;
import com.tomandjerry.coolanim.lib.letter.OLetter;
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
        this.addPellet(new FirstPellet(200, 300));
        this.addPellet(new ThirdPellet(400, 300));
        this.addPellet(new SecondPellet(300, 300));
        this.addPellet(new ForthPellet(500, 300));
        this.addLetter(new LLetter(100, 500));
        this.addLetter(new OLetter(220, 500));
        this.addLetter(new ALetter(340, 500));
        this.addLetter(new DLetter(460, 500));
        this.addLetter(new ILetter(580, 500));
        mBall = SmallYellowBall.getInstance();

        showText();
    }

    public void showText() {
        for (Letter l : mLetters) {
            l.startAnim();
        }
    }

    public void addPellet(Pellet pellet){
        if(pellet != null){
            mPellets.add(pellet);
            pellet.prepareAnim();
        }
    }

    public void addLetter(Letter letter) {
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
