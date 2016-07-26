package top.wefor.circularanimdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import top.wefor.circularanimlib.CircularAnimUtil;

public class MainActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    Button mChangeBtn, mActivityImageBtn, mActivityColorBtn, mActivityFinishBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mChangeBtn = (Button) findViewById(R.id.change_btn);
        mActivityImageBtn = (Button) findViewById(R.id.activity_image_btn);
        mActivityColorBtn = (Button) findViewById(R.id.activity_color_btn);
        mActivityFinishBtn = (Button) findViewById(R.id.activity_finish_btn);

        mChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChangeBtn.setEnabled(false);
                mProgressBar.setVisibility(View.VISIBLE);
                // 收缩按钮
                CircularAnimUtil.hide(mChangeBtn);
            }
        });

        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.GONE);
                mChangeBtn.setEnabled(true);
                // 伸展按钮
                CircularAnimUtil.show(mChangeBtn);
            }
        });

        mActivityImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 先将图片展出铺满，然后启动新的Activity
                CircularAnimUtil.startActivity(MainActivity.this, EmptyActivity.class, view, R.mipmap.img_huoer_black);
            }
        });

        mActivityColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 先将颜色展出铺满，然后启动新的Activity
                CircularAnimUtil.startActivity(MainActivity.this, EmptyActivity.class, view, R.color.colorPrimary);
            }
        });

        mActivityFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 先将颜色展出铺满，然后启动新的Activity并finish当前Activity.
                Intent intent = new Intent(MainActivity.this, EmptyActivity.class);
                CircularAnimUtil.startActivityThenFinish(MainActivity.this, intent, view, R.color.colorPrimary);
            }
        });
    }
}
