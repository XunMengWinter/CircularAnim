## CircularAnim [![](https://jitpack.io/v/XunMengWinter/CircularAnim.svg)](https://jitpack.io/#XunMengWinter/CircularAnim)

[English](https://github.com/XunMengWinter/CircularAnim/blob/master/README-EN.md) | 中文

首先来看一个UI动效图。
![动效来自Dribbble](https://d13yacurqjgara.cloudfront.net/users/62319/screenshots/1945593/shot.gif)

效果图是是Dribbble上看到的，[原作品在此。](https://dribbble.com/shots/1945593-Login-Home-Screen)

我所实现的效果如下：
[Watch on YouTube](https://youtu.be/3u0xFX62mgU)

![CircularAnim](https://raw.githubusercontent.com/XunMengWinter/source/master/gif/CircularAnimDemo.gif)

### Compile
最新可用版本[![](https://jitpack.io/v/XunMengWinter/CircularAnim.svg)](https://jitpack.io/#XunMengWinter/CircularAnim)

So,你可以如下compile该项目，也可以直接把这个类 [CircularAnim](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnim.java) 拷贝到项目里去。

add this to the the project level build.gradle file

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

add the dependency to the the app level build.gradle file

```
// replace {x.y.z} with the latest version.
implementation 'com.github.XunMengWinter:CircularAnim:{x.y.z}'
```

### 使用方法
为了使用起来简单，我将动画封装成CircularAnim.

现在，让按钮收缩只需一行代码，如下：
> CircularAnim.hide(mChangeBtn).go();


同理，让按钮伸展开：
> CircularAnim.show(mChangeBtn).go();


以View为水波触发点收缩其它View:
> CircularAnim.hide(mContentLayout).triggerView(mLogoBtnIv).go();


以View为水波触发点伸展其它View:
> CircularAnim.show(mContentLayout).triggerView(mLogoBtnIv).go();


水波般铺满指定颜色并启动一个Activity: 
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


这里，你还可以放图片：
> .colorOrImageRes(R.mipmap.img_huoer_black)

同时，你还可以设置时长、半径、转场动画、动画结束监听器等参数。

用起来非常的方便，一切逻辑性的东西都由帮助类搞定。

### 版本改动
* 0.4.0
新增：triggerPoint() 方法，可指定水波扩散点，与triggerView()类似。
新增：CircularAnim.initDefaultDeployAnimators() 方法，可设置默认的动画部署器。

* 0.3.5
新增：可以配置CircularAnim的Animator。
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
新增：可在Application中初始化CircularAnim的各项默认参数：动画时长，满铺颜色。

> CircularAnim.init(700, 500, R.color.colorPrimary);


### 源码
下面贡献源码。你可以直接新建一个[CircularAnim](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnim.java)的类，然后把下面的代码复制进去就OK了。

[点此查看源码](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnim.java)

另外，[GitHub Demo 地址在此](https://github.com/XunMengWinter/CircularAnim)，欢迎Star,欢迎喜欢，欢迎关注，哈哈哈 ^ ^ ~


### 后记
需要注意的是，该帮助类适配了api 19以下的版本，因此你不需要判断版本号，但在这些低版本设备上是没有水波动画效果的，不过好的是并不会影响交互逻辑。

另外，有木有手机版或者Mac版好用的Gif转换器推荐，表示好难找。
(感谢[im_brucezz](http://www.jianshu.com/users/693105fbc9cb/timeline)、[AkiossDev](http://www.jianshu.com/users/aedb3232c9e0/timeline)推荐的GIF录制器：licecap，非常好用，上面的gif已经用这个录制了～)

And有没有傻瓜式发布项目到JCenter的教程推荐？看过几篇都不管用。囧 ~ 
(感谢[Issues区大家的推荐](https://github.com/XunMengWinter/CircularAnim/issues)，我使用了[YangHui](https://github.com/kyze8439690)、[twiceYuan](https://github.com/twiceyuan)推荐的[JitPack.io](https://jitpack.io/docs/ANDROID/)，用起来简单很多～)


-------------------------------------------

[iOS version is here](https://github.com/entotsu/TKSubmitTransition)
