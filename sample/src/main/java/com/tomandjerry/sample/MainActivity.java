package com.tomandjerry.sample;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tomandjerry.coolanim.lib.CoolAnimView;

public class MainActivity extends AppCompatActivity {

    private Button mBtnDlgShow;
    private CoolAnimView mCoolAnimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDlgShow = (Button) findViewById(R.id.btn_dialog);
        mCoolAnimView = (CoolAnimView) findViewById(R.id.cool_view);
        mCoolAnimView.setOnCoolAnimViewListener(new CoolAnimView.OnCoolAnimViewListener() {
            @Override
            public void onAnimEnd() {
                Toast.makeText(MainActivity.this, "end", Toast.LENGTH_SHORT).show();
            }
        });
        // 1s后结束动画
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 调用此方法,使动画进入结束阶段
                mCoolAnimView.stopAnim();
            }
        }).start();

        RelativeLayout layout = new RelativeLayout(MainActivity.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(new CoolAnimView(MainActivity.this), params);
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this,R.style.Translucent_NoTitle)
                .setView(layout)
                .create();

        mBtnDlgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
}
