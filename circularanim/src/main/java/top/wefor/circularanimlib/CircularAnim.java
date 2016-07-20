package top.wefor.circularanimlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 对 ViewAnimationUtils.createCircularReveal() 方法的封装.
 * <p/>
 * Created on 16/7/20.
 * GitHub: https://github.com/XunMengWinter
 *
 * @author ice
 */
public class CircularAnim {

    public static final long PERFECT_MILLS = 618;
    public static final int MINI_RADIUS = 0;

    /**
     * 向四周伸张，直到完成显示。
     */
    @SuppressLint("NewApi")
    public static void showAsCircular(View myView, float startRadius, long durationMills) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            myView.setVisibility(View.VISIBLE);
            return;
        }

        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

        int w = myView.getWidth();
        int h = myView.getHeight();

        // 勾股定理 & 进一法
        int finalRadius = (int) Math.sqrt(w * w + h * h) + 1;

        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, startRadius, finalRadius);
        myView.setVisibility(View.VISIBLE);
        anim.setDuration(durationMills);
        anim.start();
    }

    /**
     * 由满向中间收缩，直到隐藏。
     */
    @SuppressLint("NewApi")
    public static void hideAsCircular(final View myView, float endRadius, long durationMills) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            myView.setVisibility(View.INVISIBLE);
            return;
        }

        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;
        int w = myView.getWidth();
        int h = myView.getHeight();

        // 勾股定理 & 进一法
        int initialRadius = (int) Math.sqrt(w * w + h * h) + 1;

        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, endRadius);
        anim.setDuration(durationMills);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

    /**
     * 从指定View开始向四周伸张(伸张颜色或图片为colorOrImageRes), 然后进入另一个Activity,
     * 返回至 @thisActivity 后显示收缩动画。
     */
    @SuppressLint("NewApi")
    public static void startActivityForResultAsCircular(
            final Activity thisActivity, final Intent intent, final Integer requestCode, @Nullable final Bundle bundle,
            final View triggerView, int colorOrImageRes, final long durationMills) {

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            thisActivity.startActivity(intent);
            return;
        }

        int[] location = new int[2];
        triggerView.getLocationInWindow(location);
        final int cx = location[0] + triggerView.getWidth() / 2;
        final int cy = location[1] + triggerView.getHeight() / 2;
        final ImageView view = new ImageView(thisActivity);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setImageResource(colorOrImageRes);
        final ViewGroup decorView = (ViewGroup) thisActivity.getWindow().getDecorView();
        int w = decorView.getWidth();
        int h = decorView.getHeight();
        decorView.addView(view, w, h);
        final int finalRadius = (int) Math.sqrt(w * w + h * h) + 1;
        Animator
                anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(durationMills);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (requestCode == null)
                    thisActivity.startActivity(intent);
                else if (bundle == null)
                    thisActivity.startActivityForResult(intent, requestCode);
                else
                    thisActivity.startActivityForResult(intent, requestCode, bundle);

                // 默认渐隐过渡动画.
                thisActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                // 默认显示返回至当前Activity的动画.
                triggerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(view, cx, cy, finalRadius, 0);
                        anim.setDuration(durationMills);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                try {
                                    decorView.removeView(view);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        anim.start();
                    }
                }, 1000);

            }
        });
        anim.start();
    }


    /*下面的方法全是重载，用简化上面方法的构建*/


    public static void startActivityForResultAsCircular(
            Activity thisActivity, Intent intent, Integer requestCode, View triggerView, int colorOrImageRes) {
        startActivityForResultAsCircular(thisActivity, intent, requestCode, null, triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void startActivityAsCircular(
            Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes, long durationMills) {
        startActivityForResultAsCircular(thisActivity, intent, null, null, triggerView, colorOrImageRes, durationMills);
    }

    public static void startActivityAsCircular(
            Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes) {
        startActivityAsCircular(thisActivity, intent, triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void startActivityAsCircular(Activity thisActivity, Class<?> targetClass, View triggerView, int colorOrImageRes) {
        startActivityAsCircular(thisActivity, new Intent(thisActivity, targetClass), triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void showAsCircular(View myView) {
        showAsCircular(myView, MINI_RADIUS, PERFECT_MILLS);
    }

    public static void hideAsCircular(View myView) {
        hideAsCircular(myView, MINI_RADIUS, PERFECT_MILLS);
    }

}
