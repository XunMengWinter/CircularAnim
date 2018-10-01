package top.wefor.circularanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
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
 * latest edited date: 2018-05-13 13:25
 *
 * @author ice
 */
public class CircularAnim {

    public static final long PERFECT_MILLS = 618;
    public static final int MINI_RADIUS = 0;

    private static Long sPerfectMills;
    private static Long sFullActivityPerfectMills;
    private static Integer sColorOrImageRes;

    /**
     * View visible OnAnimatorDeployListener.
     */
    private static OnAnimatorDeployListener sShowAnimatorDeployListener, sHideAnimatorDeployListener;

    /**
     * Activity OnAnimatorDeployListener.
     */
    private static OnAnimatorDeployListener sStartAnimatorDeployListener, sReturnAnimatorDeployListener;


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

    public interface OnAnimatorDeployListener {
        void deployAnimator(Animator animator);
    }

    public static class VisibleBuilder {

        private View mAnimView, mTriggerView;

        private Float mStartRadius, mEndRadius;

        private Point mTriggerPoint;

        private long mDurationMills = getPerfectMills();

        private boolean isShow;

        private OnAnimatorDeployListener mOnAnimatorDeployListener;

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

        /**
         * set the trigger view.
         * if {@link VisibleBuilder#mTriggerPoint} is null,
         * then will set the mTriggerView's center as trigger point.
         */
        public VisibleBuilder triggerView(View triggerView) {
            mTriggerView = triggerView;
            return this;
        }

        /**
         * set the trigger point.
         */
        public VisibleBuilder triggerPoint(Point triggerPoint) {
            mTriggerPoint = triggerPoint;
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

        public VisibleBuilder deployAnimator(OnAnimatorDeployListener onAnimatorDeployListener) {
            mOnAnimatorDeployListener = onAnimatorDeployListener;
            return this;
        }

        public void go() {
            go(null);
        }

        @SuppressLint("NewApi")
        public void go(OnAnimationEndListener onAnimationEndListener) {
            mOnAnimationEndListener = onAnimationEndListener;

            // 版本判断
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                doOnEnd();
                return;
            }

            if (mTriggerPoint == null) {
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
                    mTriggerPoint = new Point(triggerX - avLX, triggerY - avTY);
                } else {
                    int centerX = (mAnimView.getLeft() + mAnimView.getRight()) / 2;
                    int centerY = (mAnimView.getTop() + mAnimView.getBottom()) / 2;
                    mTriggerPoint = new Point(centerX, centerY);
                }
            }

            // 计算水波中心点至 @mAnimView 边界的最大距离
            int maxW = Math.max(mTriggerPoint.x, mAnimView.getWidth() - mTriggerPoint.x);
            int maxH = Math.max(mTriggerPoint.y, mAnimView.getHeight() - mTriggerPoint.y);
            // 勾股定理 & 进一法
            int maxRadius = (int) Math.sqrt(maxW * maxW + maxH * maxH) + 1;

            if (isShow && mEndRadius == null)
                mEndRadius = maxRadius + 0F;
            else if (!isShow && mStartRadius == null)
                mStartRadius = maxRadius + 0F;

            Animator anim = ViewAnimationUtils.createCircularReveal(
                    mAnimView, mTriggerPoint.x, mTriggerPoint.y, mStartRadius, mEndRadius);

            mAnimView.setVisibility(View.VISIBLE);
            anim.setDuration(mDurationMills);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    doOnEnd();
                }
            });
            if (mOnAnimatorDeployListener == null)
                mOnAnimatorDeployListener = isShow ? sShowAnimatorDeployListener : sHideAnimatorDeployListener;
            if (mOnAnimatorDeployListener != null)
                mOnAnimatorDeployListener.deployAnimator(anim);
            anim.start();
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


    public static class FullActivityBuilder {
        private Activity mActivity;
        private Point mTriggerPoint;
        private float mStartRadius = MINI_RADIUS;
        private int mColorOrImageRes = getColorOrImageRes();
        private Drawable mDrawable;
        private Long mDurationMills;
        private OnAnimatorDeployListener mStartAnimatorDeployListener;
        private OnAnimatorDeployListener mReturnAnimatorDeployListener;
        private OnAnimationEndListener mOnAnimationEndListener;
        private int mEnterAnim = android.R.anim.fade_in, mExitAnim = android.R.anim.fade_out;

        public FullActivityBuilder(Activity activity, View triggerView) {
            mActivity = activity;
            int[] location = new int[2];
            triggerView.getLocationInWindow(location);
            final int cx = location[0] + triggerView.getWidth() / 2;
            final int cy = location[1] + triggerView.getHeight() / 2;
            mTriggerPoint = new Point(cx, cy);
        }

        public FullActivityBuilder(Activity activity, Point triggerPoint) {
            mActivity = activity;
            mTriggerPoint = triggerPoint;
        }

        public FullActivityBuilder startRadius(float startRadius) {
            mStartRadius = startRadius;
            return this;
        }

        /**
         * set the ripple background drawableRes.
         * this will be override by {@link FullActivityBuilder#drawable(Drawable)}
         */
        public FullActivityBuilder colorOrImageRes(int colorOrImageRes) {
            mColorOrImageRes = colorOrImageRes;
            return this;
        }

        /**
         * set the ripple background drawable.
         */
        public FullActivityBuilder drawable(Drawable drawable) {
            mDrawable = drawable;
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

        /**
         * set the start animation interceptor
         */
        public FullActivityBuilder deployStartAnimator(OnAnimatorDeployListener onAnimatorDeployListener) {
            mStartAnimatorDeployListener = onAnimatorDeployListener;
            return this;
        }

        /**
         * set the return animation interceptor
         */
        public FullActivityBuilder deployReturnAnimator(OnAnimatorDeployListener onAnimatorDeployListener) {
            mReturnAnimatorDeployListener = onAnimatorDeployListener;
            return this;
        }

        @SuppressLint("NewApi")
        public void go(OnAnimationEndListener onAnimationEndListener) {
            mOnAnimationEndListener = onAnimationEndListener;
            // 版本判断,小于5.0则无动画.
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                doOnEnd();
                return;
            }

            final ImageView view = new ImageView(mActivity);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // 优先使用 mDrawable
            if (mDrawable != null)
                view.setImageDrawable(mDrawable);
            else
                view.setImageResource(mColorOrImageRes);
            final ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
            int w = decorView.getWidth();
            int h = decorView.getHeight();
            decorView.addView(view, w, h);

            // 计算中心点至view边界的最大距离
            int maxW = Math.max(mTriggerPoint.x, w - mTriggerPoint.x);
            int maxH = Math.max(mTriggerPoint.y, h - mTriggerPoint.y);
            final int finalRadius = (int) Math.sqrt(maxW * maxW + maxH * maxH) + 1;

            Animator anim = ViewAnimationUtils.createCircularReveal(view, mTriggerPoint.x, mTriggerPoint.y, mStartRadius, finalRadius);

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

                    // 默认显示返回至当前Activity的动画, not support
                    decorView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mActivity.isFinishing())
                                return;
                            Animator returnAnim = ViewAnimationUtils.createCircularReveal(view,
                                    mTriggerPoint.x, mTriggerPoint.y,
                                    finalRadius, mStartRadius);
                            returnAnim.setDuration(finalDuration);
                            returnAnim.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    if (!mActivity.isFinishing() && view.getParent() != null)
                                        ((ViewGroup) view.getParent()).removeView(view);
                                }
                            });
                            if (mReturnAnimatorDeployListener == null)
                                mReturnAnimatorDeployListener = sReturnAnimatorDeployListener;
                            if (mReturnAnimatorDeployListener != null)
                                mReturnAnimatorDeployListener.deployAnimator(returnAnim);
                            returnAnim.start();
                        }
                    }, 1000);

                }
            });
            if (mStartAnimatorDeployListener == null)
                mStartAnimatorDeployListener = sStartAnimatorDeployListener;
            if (mStartAnimatorDeployListener != null)
                mStartAnimatorDeployListener.deployAnimator(anim);
            anim.start();
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

    /**
     * 设置默认时长，设置充满activity的默认颜色或图片资源
     */
    public static void init(long perfectMills, long fullActivityPerfectMills, int colorOrImageRes) {
        sPerfectMills = perfectMills;
        sFullActivityPerfectMills = fullActivityPerfectMills;
        sColorOrImageRes = colorOrImageRes;
    }

    /**
     * 设置默认时长，设置充满activity的默认颜色或图片资源
     */
    public static void initDefaultDeployAnimators(
            OnAnimatorDeployListener showListener
            , OnAnimatorDeployListener hideListener
            , OnAnimatorDeployListener startAtyListener
            , OnAnimatorDeployListener returnAtyListener
    ) {
        sShowAnimatorDeployListener = showListener;
        sHideAnimatorDeployListener = hideListener;
        sStartAnimatorDeployListener = startAtyListener;
        sReturnAnimatorDeployListener = returnAtyListener;
    }

}
