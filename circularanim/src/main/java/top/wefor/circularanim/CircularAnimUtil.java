package top.wefor.circularanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
public class CircularAnimUtil {

    public static final long PERFECT_MILLS = 618;
    public static final int MINI_RADIUS = 0;
    private static final int FINISH_NONE = 0, FINISH_SINGLE = 1, FINISH_ALL = 3;

    /**
     * 为 myView 自身添加显示隐藏的动画
     */
    @SuppressLint("NewApi")
    private static void actionVisible(boolean isShow, final View myView, float miniRadius, long durationMills) {
        // 版本判断
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (isShow)
                myView.setVisibility(View.VISIBLE);
            else
                myView.setVisibility(View.INVISIBLE);
            return;
        }

        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

        int w = myView.getWidth();
        int h = myView.getHeight();

        // 勾股定理 & 进一法
        int maxRadius = (int) Math.sqrt(w * w + h * h) + 1;

        float startRadius, endRadius;
        if (isShow) {
            // -< 从小到大
            startRadius = miniRadius;
            endRadius = maxRadius;
        } else {
            // >- 从大到校
            startRadius = maxRadius;
            endRadius = miniRadius;
        }

        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, startRadius, endRadius);
        myView.setVisibility(View.VISIBLE);
        anim.setDuration(durationMills);

        // 若收缩，则需要在动画结束时隐藏View
        if (!isShow)
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
     * 以 @triggerView 为中心为 @animView 添加显示隐藏的动画
     */
    @SuppressLint("NewApi")
    private static void actionOtherVisible(boolean isShow, final View triggerView, final View animView,
                                           float miniRadius, long durationMills) {
        // 版本判断
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (isShow)
                animView.setVisibility(View.VISIBLE);
            else
                animView.setVisibility(View.INVISIBLE);
            return;
        }

        int[] tvLocation = new int[2];
        triggerView.getLocationInWindow(tvLocation);
        final int tvCX = tvLocation[0] + triggerView.getWidth() / 2;
        final int tvCY = tvLocation[1] + triggerView.getHeight() / 2;

        int[] avLocation = new int[2];
        animView.getLocationInWindow(avLocation);
        final int avLX = avLocation[0];
        final int avTY = avLocation[1];

        int triggerX = Math.max(avLX, tvCX);
        triggerX = Math.min(triggerX, avLX + animView.getWidth());

        int triggerY = Math.max(avTY, tvCY);
        triggerY = Math.min(triggerY, avTY + animView.getHeight());

        // 以上全为绝对坐标

        int avW = animView.getWidth();
        int avH = animView.getHeight();

        int rippleCX = triggerX - avLX;
        int rippleCY = triggerY - avTY;

        // 计算水波中心点至 @animView 边界的最大距离
        int maxW = Math.max(rippleCX, avW - rippleCX);
        int maxH = Math.max(rippleCY, avH - rippleCY);
        final int maxRadius = (int) Math.sqrt(maxW * maxW + maxH * maxH) + 1;

        float startRadius, endRadius;
        if (isShow) {
            // -< 从小到大
            startRadius = miniRadius;
            endRadius = maxRadius;
        } else {
            // >- 从大到校
            startRadius = maxRadius;
            endRadius = miniRadius;
        }

        Animator anim = ViewAnimationUtils.createCircularReveal(
                animView, rippleCX, rippleCY, startRadius, endRadius);
        animView.setVisibility(View.VISIBLE);
        anim.setDuration(durationMills);

        // 若收缩，则需要在动画结束时隐藏View
        if (!isShow)
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animView.setVisibility(View.INVISIBLE);
                }
            });

        anim.start();
    }


    private static void startActivityOrFinish(final int finishType, final Activity thisActivity,
                                              final Intent intent, final Integer requestCode,
                                              final Bundle bundle) {
        if (requestCode == null)
            thisActivity.startActivity(intent);
        else if (bundle == null)
            thisActivity.startActivityForResult(intent, requestCode);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // TODO 注意：低于api16 时
            thisActivity.startActivityForResult(intent, requestCode, bundle);
        } else
            thisActivity.startActivityForResult(intent, requestCode);

        switch (finishType) {
            case FINISH_SINGLE:
                // finish当前activity
                thisActivity.finish();
                break;
            case FINISH_ALL:
                // finish目标activity外的所有activity
                // TODO 注意：低于api16 使用 finish() 代替 finishAffinity()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    thisActivity.finishAffinity();
                } else
                    thisActivity.finish();
                break;
        }
    }

    @SuppressLint("NewApi")
    private static void actionStarActivity(
            final int finishType, final Activity thisActivity, final Intent intent,
            final Integer requestCode, final Bundle bundle, final View triggerView,
            int colorOrImageRes, long durationMills) {

        // 版本判断,小于5.0则无动画.
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            startActivityOrFinish(finishType, thisActivity, intent, requestCode, bundle);
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

        // 计算中心点至view边界的最大距离
        int maxW = Math.max(cx, w - cx);
        int maxH = Math.max(cy, h - cy);
        final int finalRadius = (int) Math.sqrt(maxW * maxW + maxH * maxH) + 1;

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        int maxRadius = (int) Math.sqrt(w * w + h * h) + 1;
        // 若使用默认时长，则需要根据水波扩散的距离来计算实际时间
        if (durationMills == PERFECT_MILLS) {
            // 算出实际边距与最大边距的比率
            double rate = 1d * finalRadius / maxRadius;
            // 为了让用户便于感触到水波，速度应随最大边距的变小而越慢，扩散时间应随最大边距的变小而变小，因此比率应在 @rate 与 1 之间。
            durationMills = (long) (PERFECT_MILLS * Math.sqrt(rate));
        }
        final long finalDuration = durationMills;
        anim.setDuration(finalDuration);
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

                switch (finishType) {
                    case FINISH_NONE:
                        // 默认显示返回至当前Activity的动画.
                        triggerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Animator anim =
                                        ViewAnimationUtils.createCircularReveal(view, cx, cy, finalRadius, 0);
                                anim.setDuration(finalDuration);
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
                        break;
                    case FINISH_SINGLE:
                        // finish当前activity
                        thisActivity.finish();
                        break;
                    case FINISH_ALL:
                        // finish目标activity外的所有activity
                        thisActivity.finishAffinity();
                        break;
                }

            }
        });
        anim.start();
    }


    /**
     * 向四周伸张，直到完成显示。
     */
    public static void show(View myView, float startRadius, long durationMills) {
        actionVisible(true, myView, startRadius, durationMills);
    }

    /**
     * 由满向中间收缩，直到隐藏。
     */
    public static void hide(final View myView, float endRadius, long durationMills) {
        actionVisible(false, myView, endRadius, durationMills);
    }

    /**
     * 从指定View开始向四周伸张(伸张颜色或图片为colorOrImageRes), 然后进入另一个Activity,
     * 返回至 @thisActivity 后显示收缩动画。
     */
    public static void startActivityForResult(
            final Activity thisActivity, final Intent intent, final Integer requestCode, final Bundle bundle,
            final View triggerView, int colorOrImageRes, long durationMills) {

        actionStarActivity(FINISH_NONE, thisActivity, intent, requestCode, bundle, triggerView, colorOrImageRes, durationMills);
    }

    /**
     * 从指定View开始向四周伸张(伸张颜色或图片为colorOrImageRes), 然后启动@intent 并finish @thisActivity/之前所有的Activity.
     */
    public static void startActivityThenFinish(
            final Activity thisActivity, final Intent intent, final boolean isFinishAffinity, final View triggerView,
            int colorOrImageRes, long durationMills) {
        int finishType = isFinishAffinity ? FINISH_ALL : FINISH_SINGLE;
        actionStarActivity(finishType, thisActivity, intent, null, null, triggerView, colorOrImageRes, durationMills);
    }


    /*下面的方法全是重载，用简化上面方法的构建*/


    public static void startActivityForResult(
            Activity thisActivity, Intent intent, Integer requestCode, View triggerView, int colorOrImageRes) {
        startActivityForResult(thisActivity, intent, requestCode, null, triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void startActivity(
            Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes, long durationMills) {
        startActivityForResult(thisActivity, intent, null, null, triggerView, colorOrImageRes, durationMills);
    }

    public static void startActivity(
            Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes) {
        startActivity(thisActivity, intent, triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void startActivity(Activity thisActivity, Class<?> targetClass, View triggerView, int colorOrImageRes) {
        startActivity(thisActivity, new Intent(thisActivity, targetClass), triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void startActivityThenFinish(Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes) {
        // 默认finish当前activity
        startActivityThenFinish(thisActivity, intent, false, triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void show(View myView) {
        show(myView, MINI_RADIUS, PERFECT_MILLS);
    }

    public static void hide(View myView) {
        hide(myView, MINI_RADIUS, PERFECT_MILLS);
    }

    public static void showOther(View triggerView, View otherView) {
        actionOtherVisible(true, triggerView, otherView, MINI_RADIUS, PERFECT_MILLS);
    }

    public static void hideOther(View triggerView, View otherView) {
        actionOtherVisible(false, triggerView, otherView, MINI_RADIUS, PERFECT_MILLS);
    }

}
