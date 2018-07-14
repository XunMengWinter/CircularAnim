package top.wefor.circularanimdemo.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import top.wefor.circularanim.CircularAnim;
import top.wefor.circularanimdemo.R;

/**
 * Created on 2018/7/15.
 *
 * @author ice
 * <p>
 * GitHub: https://github.com/XunMengWinter
 */
public class FragmentTestActivity extends AppCompatActivity {

    View mFullView;

    Test1Fragment mTest1Fragment;
    Test2Fragment mTest2Fragment;

    private boolean mIsTest2Fragment = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        mFullView = findViewById(R.id.full_view);

        mTest1Fragment = new Test1Fragment();
        mTest2Fragment = new Test2Fragment();

        showFragment(mTest1Fragment);
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void replaceFragment(final View view) {
        view.setEnabled(false);

        /* show Fragment with CircularAnim */
        CircularAnim.show(mFullView)
                .triggerView(view)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mFullView.animate().alpha(0).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    mFullView.setVisibility(View.GONE);
                                    mFullView.setAlpha(1);
                                }
                            }).start();
                        } else {
                            mFullView.setVisibility(View.GONE);
                        }

                        showFragment(mIsTest2Fragment ? mTest1Fragment : mTest2Fragment);
                        mIsTest2Fragment = !mIsTest2Fragment;
                        view.setEnabled(true);
                    }
                });
    }
}
