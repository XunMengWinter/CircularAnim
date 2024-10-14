package top.wefor.circularanimdemo;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.wefor.circularanim.CircularAnim;
import top.wefor.circularanimdemo.fragment.FragmentTestActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar mProgressBar, mProgressBar2;
    Button mChangeBtn, mChangeBtn2, mActivityImageBtn, mActivityColorBtn;
    ImageView mLogoBtnIv;
    LinearLayout mContentLayout;
    boolean isContentVisible = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mChangeBtn = (Button) findViewById(R.id.change_btn);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        mChangeBtn2 = (Button) findViewById(R.id.change_btn2);
        mActivityImageBtn = (Button) findViewById(R.id.activity_image_btn);
        mActivityColorBtn = (Button) findViewById(R.id.activity_color_btn);
        mLogoBtnIv = (ImageView) findViewById(R.id.logoBtn_iv);
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);

        mChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                // 收缩按钮
                CircularAnim.hide(mChangeBtn).go();
            }
        });

        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.GONE);
                // 伸展按钮
                CircularAnim.show(mChangeBtn).go();
            }
        });

        mChangeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircularAnim.hide(mChangeBtn2)
                        .endRadius(mProgressBar2.getHeight() / 2)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                mProgressBar2.setVisibility(View.VISIBLE);
                                mProgressBar2.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CircularAnim.fullActivity(MainActivity.this, mProgressBar2)
                                                .go(new CircularAnim.OnAnimationEndListener() {
                                                    @Override
                                                    public void onAnimationEnd() {
                                                        startActivity(new Intent(MainActivity.this, ListActivity.class));
                                                        finish();
                                                    }
                                                });
                                    }
                                }, 3000);
                            }
                        });
            }
        });

        mActivityImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 先将图片展出铺满，然后启动新的Activity
                CircularAnim.fullActivity(MainActivity.this, view)
                        .colorOrImageRes(R.mipmap.img_huoer_black)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(MainActivity.this, ListActivity.class));
                            }
                        });
            }
        });

        mActivityColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 先将颜色展出铺满，然后启动新的Activity
                CircularAnim.fullActivity(MainActivity.this, view)
//                        .colorOrImageRes(R.color.colorPrimary)  //注释掉，因为该颜色已经在App.class 里配置为默认色
                        .deployReturnAnimator(new CircularAnim.OnAnimatorDeployListener() {
                            @Override
                            public void deployAnimator(Animator animator) {
                                //this .setDuration() with override CircularAnim.setDuration().
                                animator.setDuration(700L);
                                animator.setInterpolator(new AccelerateInterpolator());
                            }
                        })
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(MainActivity.this, ListActivity.class));
                            }
                        });
            }
        });


        mLogoBtnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.animate().rotationBy(90);
                // 以 @mLogoBtnIv 为中心，收缩或伸展 @mContentLayout
                if (isContentVisible)
                    CircularAnim.hide(mContentLayout).duration(5_000).triggerView(mLogoBtnIv).go();
                else
                    CircularAnim.show(mContentLayout)
                            .triggerPoint(new Point(mContentLayout.getWidth(), 0))
                            .duration(5_000)
                            .go();

                isContentVisible = !isContentVisible;
            }
        });
    }

    public void fullWithDrawable(View view) {
        // 先将图片展出铺满，然后启动新的Activity
        CircularAnim.fullActivity(MainActivity.this, view)
                .drawable(new ColorDrawable(0xFF996383)) // drawable will override colorOrImageRes
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        startActivity(new Intent(MainActivity.this, ListActivity.class));
                    }
                });
    }

    public void fragmentDemo(View view) {
        startActivity(new Intent(this, FragmentTestActivity.class));
    }

}
