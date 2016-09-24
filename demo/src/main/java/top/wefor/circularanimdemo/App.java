package top.wefor.circularanimdemo;

import android.app.Application;

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
        // optional. change default duration and fullActivity resource.
        // the original value is (618, 618,android.R.color.white).
        CircularAnim.init(700, 500, R.color.colorPrimary);
    }
}
