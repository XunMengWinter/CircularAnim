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

So,你可以如下compile该项目，也可以直接把这个类 [CircularAnimUtil](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnimUtil.java) 拷贝到项目里去。

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
// replace {x.y.z} with the last version.
compile 'com.github.XunMengWinter:CircularAnim:{x.y.z}'
```

### 使用方法
为了使用起来简单，我将动画封装成CircularAnimUtil.

现在，让按钮收缩只需一行代码，如下：
> CircularAnimUtil.hide(mChangeBtn);


同理，让按钮伸展开：
> CircularAnimUtil.show(mChangeBtn);


以View为水波触发点收缩其它View:
> hideOther(View triggerView, View otherView)


以View为水波触发点伸展其它View:
> showOther(View triggerView, View otherView)


水波般铺满指定颜色并启动一个Activity:
> CircularAnimUtil.startActivity(MainActivity.this, EmptyActivity.class, view, R.color.colorPrimary);


这里，你还可以放图片：
> CircularAnimUtil.startActivity(MainActivity.this, EmptyActivity.class, view, R.mipmap.img_huoer_black);

也许在显示或隐藏视图时，你想要设置半径和时长，你可以调用这个方法：
> 显示：show(View myView, float startRadius, long durationMills)

> 隐藏：hide(final View myView, float endRadius, long durationMills) 


以及，你可以在startActivity时带上Intent:
> startActivity(Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes)

还可以startActivityForResult:
> startActivityForResult(Activity thisActivity, Intent intent, Integer requestCode, View triggerView, int colorOrImageRes)

以及startActivity然后finish:
> startActivityThenFinish(Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes)


同理，startActivity同样可以设置时长。

用起来非常的方便，一切逻辑性的东西都由帮助类搞定。


### 源码
下面贡献源码。你可以直接新建一个[CircularAnimUtil](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnimUtil.java)的类，然后把下面的代码复制进去就OK了。

[点此查看源码](https://raw.githubusercontent.com/XunMengWinter/CircularAnim/master/circularanim/src/main/java/top/wefor/circularanim/CircularAnimUtil.java)

另外，[GitHub Demo 地址在此](https://github.com/XunMengWinter/CircularAnim)，欢迎Star,欢迎喜欢，欢迎关注，哈哈哈 ^ ^ ~


### 后记
需要注意的是，该帮助类适配了api 19以下的版本，因此你不需要判断版本号，但在这些低版本设备上是没有水波动画效果的，不过好的是并不会影响交互逻辑。

另外，有木有手机版或者Mac版好用的Gif转换器推荐，表示好难找。
(感谢[im_brucezz](http://www.jianshu.com/users/693105fbc9cb/timeline)、[AkiossDev](http://www.jianshu.com/users/aedb3232c9e0/timeline)推荐的GIF录制器：licecap，非常好用，上面的gif已经用这个录制了～)

And有没有傻瓜式发布项目到JCenter的教程推荐？看过几篇都不管用。囧 ~ 
(感谢[Issues区大家的推荐](https://github.com/XunMengWinter/CircularAnim/issues)，我使用了[YangHui](https://github.com/kyze8439690)、[twiceYuan](https://github.com/twiceyuan)推荐的[JitPack.io](https://jitpack.io/docs/ANDROID/)，用起来简单很多～)


-------------------------------------------

[iOS version is here](https://github.com/entotsu/TKSubmitTransition)
