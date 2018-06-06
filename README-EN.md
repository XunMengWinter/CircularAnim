## CircularAnim [![](https://jitpack.io/v/XunMengWinter/CircularAnim.svg)](https://jitpack.io/#XunMengWinter/CircularAnim)

English | [中文](https://github.com/XunMengWinter/CircularAnim/blob/master/README.md)

First, let's see a quick demo of the UI:
![Demo from Dribbble](https://d13yacurqjgara.cloudfront.net/users/62319/screenshots/1945593/shot.gif)

The design on which rendering is based can be found [here](https://dribbble.com/shots/1945593-Login-Home-Screen) on Dribble.

The results I was able to achieve:
[Watch on YouTube](https://youtu.be/3u0xFX62mgU)

![CircularAnim](https://raw.githubusercontent.com/XunMengWinter/source/master/gif/CircularAnimDemo.gif)

### Configuration
Latest version: [![](https://jitpack.io/v/XunMengWinter/CircularAnim.svg)](https://jitpack.io/#XunMengWinter/CircularAnim)


Instructions for compiling the project follow. As an alternative, you can just take the [CircularAnim](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnim.java) class and use it directly in your project.


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
// replace {x.y.z} with the latest version.
implementation 'com.github.XunMengWinter:CircularAnim:{x.y.z}'
```

### Usage
For ease of use, below are provided examples on how to interact with CircularAnimUtil.

Hiding a button only requires the following code:
> CircularAnim.hide(mChangeBtn).go();

Likewise, for showing:
> CircularAnim.show(mChangeBtn).go();

With a view's ripple animation - trigger hiding another View:
> CircularAnim.hide(mContentLayout).triggerView(mLogoBtnIv).go();

With a view's ripple animation - trigger showing another View:
> CircularAnim.show(mContentLayout).triggerView(mLogoBtnIv).go();

To set the color and start an Activity for a ripple animation:
```
CircularAnim.fullActivity(MainActivity.this, view)
                        .colorOrImageRes(R.color.colorPrimary)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(MainActivity.this, EmptyActivity.class));
                            }
                        });
```

You can also supply an image:
> .colorOrImageRes(R.mipmap.img_huoer_black)

Likewise, you can also set duration, radius, trantionAnim and animation end listener.


### Changes
* 0.4.0
ADD：.triggerPoint() , You can set the ripple triggerPoint by this method, like .triggerView()。
ADD：CircularAnim.initDefaultDeployAnimators()  , you can set the default animator deployer。

* 0.3.5
ADD：You can deploy Animator in CircularAnim's Builder.
```
CircularAnim.hide(mChangeBtn2)
                          .endRadius(mProgressBar2.getHeight() / 2)
                          .deployAnimator(new CircularAnim.OnAnimatorDeployListener() {
                              @Override
                              public void deployAnimator(Animator animator) {
                                  animator.setDuration(1200L);
                                  animator.setInterpolator(new AccelerateInterpolator());
                              }
                          })
                          .go();
```


* 0.3.4
ADD：You can change the default duration and colorOrImageRes when enter app.
> CircularAnim.init(700, 500, R.color.colorPrimary);


### Source
The source is available below. You can directly construct a new [CircularAnim](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnim.java), or just use one or more parts from the source.

[View the source code here](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnim.java)

Additionally, the project is [available on GitHub](https://github.com/XunMengWinter/CircularAnim). Interest, stars and thanking is very much welcome!


### Notes
- The project supports versions older than API 19, so there's no need to opt for a specific version to use. However, the ripple animation is not available on these lower API levels - fortunately, this does not affect interaction logic at all.

I would like to thank 
[im_brucezz](http://www.jianshu.com/users/693105fbc9cb/timeline) and [AkiossDev](http://www.jianshu.com/users/aedb3232c9e0/timeline) for recommending licecap, the GIF recording tool that was used to capture the images for this page.

Feedback is welcome on the [Issues](https://github.com/XunMengWinter/CircularAnim/issues) page.
I used [JitPack.io](https://jitpack.io/docs/ANDROID/) as recommended by [YangHui](https://github.com/kyze8439690) and [twiceYuan](https://github.com/twiceyuan), which  was much easier to use than JCenter, which I had no success with.
 
 -------------------------------------------
 
[iOS version](https://github.com/entotsu/TKSubmitTransition)

Translation: [Szabolcs Pasztor](https://github.com/spqpad)
