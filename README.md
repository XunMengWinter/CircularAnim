## CircularAnim
[![](https://jitpack.io/v/XunMengWinter/CircularAnim.svg)](https://jitpack.io/#XunMengWinter/CircularAnim)

[iOS version](https://github.com/entotsu/TKSubmitTransition)

[中文版文档](https://github.com/XunMengWinter/CircularAnim/blob/master/README-ZH.md)

First, let's see a quick demo of the UI:
![Demo from Dribbble](https://d13yacurqjgara.cloudfront.net/users/62319/screenshots/1945593/shot.gif)

The design on which rendering is based can be found [here](https://dribbble.com/shots/1945593-Login-Home-Screen) on Dribble.

The results I was able to achieve:
[Watch on YouTube](https://youtu.be/3u0xFX62mgU)

![CircularAnim](https://raw.githubusercontent.com/XunMengWinter/source/master/gif/CircularAnimDemo.gif)

### Configuration
Most recent version: [![](https://jitpack.io/v/XunMengWinter/CircularAnim.svg)](https://jitpack.io/#XunMengWinter/CircularAnim)


Instructions for compiling the project follow. As an alternative, you can just take the [CircularAnimUtil](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnimUtil.java) class and use it directly in your project.


- Add this to the the project level build.gradle file

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

- Add the dependency to the the app level build.gradle file

```
// replace {x.y.z} with the last version.
compile 'com.github.XunMengWinter:CircularAnim:{x.y.z}'
```

### Usage
For ease of use, below are provided examples on how to interact with CircularAnimUtil.

Hiding a button only requires the following code:
> CircularAnimUtil.hide(mChangeBtn);

Likewise, for showing:
> CircularAnimUtil.show(mChangeBtn);

With a view's ripple animation - trigger hiding another View:
> hideOther(View triggerView, View otherView)

With a view's ripple animation - trigger showing another View:
> showOther(View triggerView, View otherView)

To set the color and start an Activity for a ripple animation:
> CircularAnimUtil.startActivity(MainActivity.this, EmptyActivity.class, view, R.color.colorPrimary);

You can also supply an image:
> CircularAnimUtil.startActivity(MainActivity.this, EmptyActivity.class, view, R.mipmap.img_huoer_black);

When showing or hiding an image, you may want to set the radius or time, which can be done by calling with these signatures:
> show(View myView, float startRadius, long durationMills)

> hide(final View myView, float endRadius, long durationMills) 

Upon calling startActivity, you can also provide an Intent:
> startActivity(Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes)

You may also use startActivityForResult:
> startActivityForResult(Activity thisActivity, Intent intent, Integer requestCode, View triggerView, int colorOrImageRes)

To first call startActivity, then finish:
> startActivityThenFinish(Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes)

Likewise, setting the length of time is also possible with startActivity.


### Source
The source is available below. You can directly construct a new [CircularAnimUtil](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnimUtil.java), or just use one or more parts from the source.

[View the source code here](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnimUtil.java)

Additionally, the project is [available on GitHub](https://github.com/XunMengWinter/CircularAnim). Interest, stars and thanking is very much welcome!


### Notes
- The project supports versions older than API 19, so there's no need to opt for a specific version to use. However, the ripple animation is not available on these lower API levels - fortunately, this does not affect interaction logic at all.

I would like to thank 
[im_brucezz](http://www.jianshu.com/users/693105fbc9cb/timeline) and [AkiossDev](http://www.jianshu.com/users/aedb3232c9e0/timeline) for recommending licecap, the GIF recording tool that was used to capture the images for this page.

Feedback is welcome on the [Issues](https://github.com/XunMengWinter/CircularAnim/issues) page.
I used [JitPack.io](https://jitpack.io/docs/ANDROID/) as recommended by [YangHui](https://github.com/kyze8439690) and [twiceYuan](https://github.com/twiceyuan), which  was much easier to use than JCenter, which I had no success with.
 

Translation: [Szabolcs Pasztor](https://github.com/spqpad)
