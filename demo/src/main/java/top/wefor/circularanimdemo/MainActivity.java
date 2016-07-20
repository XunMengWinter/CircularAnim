package top.wefor.circularanimdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import top.wefor.circularanimlib.CircularAnim;

public class MainActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    Button mChangeBtn, mActivityImageBtn, mActivityColorBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mChangeBtn = (Button) findViewById(R.id.change_btn);
        mActivityImageBtn = (Button) findViewById(R.id.activity_image_btn);
        mActivityColorBtn = (Button) findViewById(R.id.activity_color_btn);

        mChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                // 收缩按钮
                CircularAnim.hideAsCircular(mChangeBtn);
            }
        });

        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.GONE);
                // 伸展按钮
                CircularAnim.showAsCircular(mChangeBtn);
            }
        });

        mActivityImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 先将图片展出铺满，然后启动新的Activity
                CircularAnim.startActivityAsCircular(MainActivity.this, EmptyActivity.class, view, R.mipmap.img_huoer_black);
            }
        });

        mActivityColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 先将颜色展出铺满，然后启动新的Activity
                CircularAnim.startActivityAsCircular(MainActivity.this, EmptyActivity.class, view, R.color.colorPrimary);
            }
        });
    }
}
