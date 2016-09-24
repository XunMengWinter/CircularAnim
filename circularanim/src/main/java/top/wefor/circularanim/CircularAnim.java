package top.wefor.circularanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created on 16/8/5.
 * <p/>
 * 对 ViewAnimationUtils.createCircularReveal() 方法的封装.
 * <p/>
 * GitHub: https://github.com/XunMengWinter
 * <p/>
 * latest edited date: 2016-08-29 01:17
 *
 * @author ice
 */
public class CircularAnim {

    public static final long PERFECT_MILLS = 618;
    public static final int MINI_RADIUS = 0;

    private static Long sPerfectMills;
    private static Long sFullActivityPerfectMills;
    private static Integer sColorOrImageRes;

    private static long getPerfectMills() {
        if (sPerfectMills != null)
            return sPerfectMills;
        else
            return PERFECT_MILLS;
    }

    private static long getFullActivityMills() {
        if (sFullActivityPerfectMills != null)
            return sFullActivityPerfectMills;
        else
            return PERFECT_MILLS;
    }

    private static int getColorOrImageRes() {
        if (sColorOrImageRes != null)
            return sColorOrImageRes;
        else
            return android.R.color.white;
    }


    public interface OnAnimationEndListener {
        void onAnimationEnd();
    }

    @SuppressLint("NewApi")
    public static class VisibleBuilder {

        private View mAnimView, mTriggerView;

        private Float mStartRadius, mEndRadius;

        private long mDurationMills = getPerfectMills();

        private boolean isShow;

        private OnAnimationEndListener mOnAnimationEndListener;

        public VisibleBuilder(View animView, boolean isShow) {
            mAnimView = animView;
            this.isShow = isShow;

            if (isShow) {
                mStartRadius = MINI_RADIUS + 0F;
            } else {
                mEndRadius = MINI_RADIUS + 0F;
            }
        }

        public VisibleBuilder triggerView(View triggerView) {
            mTriggerView = triggerView;
            return this;
        }

        public VisibleBuilder startRadius(float startRadius) {
            mStartRadius = startRadius;
            return this;
        }

        public VisibleBuilder endRadius(float endRadius) {
            mEndRadius = endRadius;
            return this;
        }

        public VisibleBuilder duration(long durationMills) {
            mDurationMills = durationMills;
            return this;
        }

        @Deprecated //You can use method - go(OnAnimationEndListener onAnimationEndListener).
        public VisibleBuilder onAnimationEndListener(OnAnimationEndListener onAnimationEndListener) {
            mOnAnimationEndListener = onAnimationEndListener;
            return this;
        }

        public void go() {
            go(null);
        }

        public void go(OnAnimationEndListener onAnimationEndListener) {
            mOnAnimationEndListener = onAnimationEndListener;

            // 版本判断
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                doOnEnd();
                return;
            }

            int rippleCX, rippleCY, maxRadius;
            if (mTriggerView != null) {
                int[] tvLocation = new int[2];
                mTriggerView.getLocationInWindow(tvLocation);
                final int tvCX = tvLocation[0] + mTriggerView.getWidth() / 2;
                final int tvCY = tvLocation[1] + mTriggerView.getHeight() / 2;

                int[] avLocation = new int[2];
                mAnimView.getLocationInWindow(avLocation);
                final int avLX = avLocation[0];
                final int avTY = avLocation[1];

                int triggerX = Math.max(avLX, tvCX);
                triggerX = Math.min(triggerX, avLX + mAnimView.getWidth());

                int triggerY = Math.max(avTY, tvCY);
                triggerY = Math.min(triggerY, avTY + mAnimView.getHeight());

                // 以上全为绝对坐标

                int avW = mAnimView.getWidth();
                int avH = mAnimView.getHeight();

                rippleCX = triggerX - avLX;
                rippleCY = triggerY - avTY;

                // 计算水波中心点至 @mAnimView 边界的最大距离
                int maxW = Math.max(rippleCX, avW - rippleCX);
                int maxH = Math.max(rippleCY, avH - rippleCY);
                maxRadius = (int) Math.sqrt(maxW * maxW + maxH * maxH) + 1;
            } else {
                rippleCX = (mAnimView.getLeft() + mAnimView.getRight()) / 2;
                rippleCY = (mAnimView.getTop() + mAnimView.getBottom()) / 2;

                int w = mAnimView.getWidth();
                int h = mAnimView.getHeight();

                // 勾股定理 & 进一法
                maxRadius = (int) Math.sqrt(w * w + h * h) + 1;
            }

            if (isShow && mEndRadius == null)
                mEndRadius = maxRadius + 0F;
            else if (!isShow && mStartRadius == null)
                mStartRadius = maxRadius + 0F;

            try {
                Animator anim = ViewAnimationUtils.createCircularReveal(
                        mAnimView, rippleCX, rippleCY, mStartRadius, mEndRadius);


                mAnimView.setVisibility(View.VISIBLE);
                anim.setDuration(mDurationMills);

                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        doOnEnd();
                    }
                });

                anim.start();
            } catch (Exception e) {
                e.printStackTrace();
                doOnEnd();
            }
        }

        private void doOnEnd() {
            if (isShow)
                mAnimView.setVisibility(View.VISIBLE);
            else
                mAnimView.setVisibility(View.INVISIBLE);

            if (mOnAnimationEndListener != null)
                mOnAnimationEndListener.onAnimationEnd();
        }

    }

    @SuppressLint("NewApi")
    public static class FullActivityBuilder {
        private Activity mActivity;
        private View mTriggerView;
        private float mStartRadius = MINI_RADIUS;
        private int mColorOrImageRes = getColorOrImageRes();
        private Long mDurationMills;
        private OnAnimationEndListener mOnAnimationEndListener;
        private int mEnterAnim = android.R.anim.fade_in, mExitAnim = android.R.anim.fade_out;

        public FullActivityBuilder(Activity activity, View triggerView) {
            mActivity = activity;
            mTriggerView = triggerView;
        }

        public FullActivityBuilder startRadius(float startRadius) {
            mStartRadius = startRadius;
            return this;
        }

        public FullActivityBuilder colorOrImageRes(int colorOrImageRes) {
            mColorOrImageRes = colorOrImageRes;
            return this;
        }

        public FullActivityBuilder duration(long durationMills) {
            mDurationMills = durationMills;
            return this;
        }

        public FullActivityBuilder overridePendingTransition(int enterAnim, int exitAnim) {
            mEnterAnim = enterAnim;
            mExitAnim = exitAnim;
            return this;
        }

        public void go(OnAnimationEndListener onAnimationEndListener) {
            mOnAnimationEndListener = onAnimationEndListener;

            // 版本判断,小于5.0则无动画.
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                doOnEnd();
                return;
            }

            int[] location = new int[2];
            mTriggerView.getLocationInWindow(location);
            final int cx = location[0] + mTriggerView.getWidth() / 2;
            final int cy = location[1] + mTriggerView.getHeight() / 2;
            final ImageView view = new ImageView(mActivity);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageResource(mColorOrImageRes);
            final ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
            int w = decorView.getWidth();
            int h = decorView.getHeight();
            decorView.addView(view, w, h);

            // 计算中心点至view边界的最大距离
            int maxW = Math.max(cx, w - cx);
            int maxH = Math.max(cy, h - cy);
            final int finalRadius = (int) Math.sqrt(maxW * maxW + maxH * maxH) + 1;

            try {
                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, mStartRadius, finalRadius);

                int maxRadius = (int) Math.sqrt(w * w + h * h) + 1;
                // 若未设置时长，则以PERFECT_MILLS为基准根据水波扩散的距离来计算实际时间
                if (mDurationMills == null) {
                    // 算出实际边距与最大边距的比率
                    double rate = 1d * finalRadius / maxRadius;
                    // 为了让用户便于感触到水波，速度应随最大边距的变小而越慢，扩散时间应随最大边距的变小而变小，因此比率应在 @rate 与 1 之间。
                    mDurationMills = (long) (getFullActivityMills() * Math.sqrt(rate));
                }
                final long finalDuration = mDurationMills;
                // 由于thisActivity.startActivity()会有所停顿，所以进入的水波动画应比退出的水波动画时间短才能保持视觉上的一致。
                anim.setDuration((long) (finalDuration * 0.9));
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        doOnEnd();

                        mActivity.overridePendingTransition(mEnterAnim, mExitAnim);

                        // 默认显示返回至当前Activity的动画.
                        mTriggerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mActivity.isFinishing()) return;
                                try {
                                    Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                                            finalRadius, mStartRadius);
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
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    try {
                                        decorView.removeView(view);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }, 1000);

                    }
                });
                anim.start();
            } catch (Exception e) {
                e.printStackTrace();
                doOnEnd();
            }
        }

        private void doOnEnd() {
            mOnAnimationEndListener.onAnimationEnd();
        }
    }


    /* 上面为实现逻辑，下面为外部调用方法 */


    /* 伸展并显示@animView */
    public static VisibleBuilder show(View animView) {
        return new VisibleBuilder(animView, true);
    }

    /* 收缩并隐藏@animView */
    public static VisibleBuilder hide(View animView) {
        return new VisibleBuilder(animView, false);
    }

    /* 以@triggerView 为触发点铺满整个@activity */
    public static FullActivityBuilder fullActivity(Activity activity, View triggerView) {
        return new FullActivityBuilder(activity, triggerView);
    }

    /* 设置默认时长，设置充满activity的默认颜色或图片资源 */
    public static void init(long perfectMills, long fullActivityPerfectMills, int colorOrImageRes) {
        sPerfectMills = perfectMills;
        sFullActivityPerfectMills = fullActivityPerfectMills;
        sColorOrImageRes = colorOrImageRes;
    }

}
