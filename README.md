# CoolAndroidAnim
&emsp;&emsp;一个酷炫的android loading效果,想法来源于Google的nexus机子6.0系统的开机动画,也是对于它的一个模仿:从四个小球的
不同的组合动画(包括了缩放,旋转,移动,变色等)演变成loading字样的效果.

# 效果
动画分为两部分：
- 循环变化的动画效果
- 调用结束API时从循环动画切换到结束动画

![](https://github.com/TomWithJerry/CoolAndroidAnim/raw/master/pic1.gif)
![](https://github.com/TomWithJerry/CoolAndroidAnim/raw/master/pic2.gif)

# api
CoolAnimView.java
``` java
/**
 * 调用该方法可以使动画进入结束动画的阶段,而具体结束动画的时间,需要通过接口回调来获得
 */
void stopAnim();

/**
 * 设置结束动画的监听
 */
void setOnCoolAnimViewListener(OnCoolAnimViewListener onCoolAnimViewListener);
```

# 使用方法
1.通过XML设置
``` xml
<com.tomandjerry.coolanim.lib.CoolAnimView
    android:id="@+id/cool_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
2.通过java代码引入
``` java
layout.addView(new CoolAnimView(MainActivity.this), params);
```

# 结构
![](https://github.com/TomWithJerry/CoolAndroidAnim/raw/master/struc.jpeg)

# 作者
[@Yellow5A5](https://github.com/Yellow5A5),[@yxping](https://github.com/yxping)

# TODO
该库还未提供自由更改大小的能力