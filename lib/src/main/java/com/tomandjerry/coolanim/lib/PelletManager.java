package com.tomandjerry.coolanim.lib;

import android.graphics.Canvas;

import com.tomandjerry.coolanim.lib.letter.ALetter;
import com.tomandjerry.coolanim.lib.letter.DLetter;
import com.tomandjerry.coolanim.lib.letter.GLetter;
import com.tomandjerry.coolanim.lib.letter.ILetter;
import com.tomandjerry.coolanim.lib.letter.LLetter;
import com.tomandjerry.coolanim.lib.letter.Letter;
import com.tomandjerry.coolanim.lib.letter.NLetter;
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
public class PelletManager implements Pellet.AnimatorStateListen {

    private List<Pellet> mPellets;
    private List<Letter> mLetters;
    private SmallYellowBall mBall;
    private int mEndNum = 0;
    private boolean isEnding = false;

    public PelletManager() {
        mPellets = new ArrayList<>();
        mLetters = new ArrayList<>();
    }

    /**
     * 初始化各个组件,包括球和字
     */
    public void initComponents(){
        // 加入小球
        this.addPellet(new FirstPellet(200, 300));
        this.addPellet(new ThirdPellet(400, 300));
        this.addPellet(new SecondPellet(300, 300));
        this.addPellet(new ForthPellet(500, 300));
        // 为小球添加结束动画监听
        for (Pellet p : mPellets) {
            p.setAnimatorStateListen(this);
        }
        // 加入字母
        this.addLetter(new LLetter(100, 500));
        this.addLetter(new OLetter(220, 500));
        this.addLetter(new ALetter(340, 500));
        this.addLetter(new DLetter(460, 500));
        this.addLetter(new ILetter(580, 500));
        this.addLetter(new NLetter(700, 500));
        this.addLetter(new GLetter(820, 500));
        // 设置小球
        mBall = SmallYellowBall.getInstance();

        startPelletsAnim();
//        showText();
    }

    /**
     * 开始小球的动画
     */
    public void startPelletsAnim() {
        isEnding = false;
        mEndNum = 0;
        for (Pellet p : mPellets){
            p.startAnim();
        }
    }

    /**
     * 先进行小球的移动,同时小球进行结尾动画展示,当完成以为,小球结尾动画继续,同时开始文字的展示
     */
    public void showText() {
        isEnding = true;
        for (Pellet p : mPellets) {
            p.endAnim();
        }
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
        if (!isEnding) {
            // 绘制小球
            mBall.drawSelf(canvas);
        }
    }

    // 循环次数
    private int times = 1;
    // 纪录动画结束的小球个数,当动画结束可以执行循环任务
    @Override
    public void onAnimatorEnd() {
        mEndNum++;
        if (mEndNum == mPellets.size()) {
            times--;
            if (times == 0) {
                showText();
            } else {
                startPelletsAnim();
            }
        }
    }
}
