package top.wefor.circularanimdemo;

import android.animation.Animator;
import android.app.Application;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import top.wefor.circularanim.CircularAnim;

/**
 * Created on 16/9/24.
 *
 * @author ice
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // optional. change the default duration and fullActivity resource.
        // the original value is (618, 618,android.R.color.white).
        CircularAnim.init(700, 618, R.color.colorPrimary);
        // optional. set the default duration OnAnimatorDeployListener.
        CircularAnim.initDefaultDeployAnimators(
                new CircularAnim.OnAnimatorDeployListener() {
                    @Override
                    public void deployAnimator(Animator animator) {
                        animator.setInterpolator(new AccelerateInterpolator());
                    }
                },
                new CircularAnim.OnAnimatorDeployListener() {
                    @Override
                    public void deployAnimator(Animator animator) {
                        animator.setInterpolator(new DecelerateInterpolator());
                    }
                },
                null,
                null);
    }
}
