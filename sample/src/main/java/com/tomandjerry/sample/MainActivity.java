package com.tomandjerry.sample;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tomandjerry.coolanim.lib.CoolAnimView;

public class MainActivity extends AppCompatActivity {

    private Button mBtnDlgShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDlgShow = (Button) findViewById(R.id.btn_dialog);

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
