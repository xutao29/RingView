# RingView

### 带阴影效果图
![image](https://github.com/taoyixun/RingView/blob/master/img-folder/20160829150958.png?raw=true)
```java
    <com.taoyixun.ringviewlib.RingView
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_centerInParent="true"
        app:ringView_background_color="@android:color/white"
        app:ringView_ring_end_color="@color/colorAccent"
        app:ringView_ring_margin="20dp"
        app:ringView_ring_max="100"
        app:ringView_ring_progress="50"
        app:ringView_ring_progress_unit="%"
        app:ringView_ring_start_color="@color/colorPrimary"
        app:ringView_ring_width="40dp"
        app:ringView_shadow_color="#cccccc"
        app:ringView_shadow_width="1dp"
        app:ringView_text="Progress："
        app:ringView_text_auto_color="true"
        app:ringView_text_color="@color/colorAccent"
        app:ringView_text_margin="10dp"
        android:onClick="OnRingViewClick"
        app:ringView_text_size="18sp" />
  ```
  
### 无阴影效果图
![image](https://github.com/taoyixun/RingView/blob/master/img-folder/20160829151135.png?raw=true)
```java
    <com.taoyixun.ringviewlib.RingView
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_centerInParent="true"
        app:ringView_background_color="@android:color/white"
        app:ringView_ring_end_color="@color/colorAccent"
        app:ringView_ring_margin="0dp"
        app:ringView_ring_max="100"
        app:ringView_ring_progress="80"
        app:ringView_ring_progress_unit="%"
        app:ringView_ring_start_color="@color/colorPrimary"
        app:ringView_ring_width="40dp"
        app:ringView_shadow_color="#cccccc"
        app:ringView_shadow_width="0dp"
        app:ringView_text="Progress："
        app:ringView_text_auto_color="true"
        app:ringView_text_color="@color/colorAccent"
        app:ringView_text_margin="10dp"
        app:ringView_text_size="18sp" />
  ```
  

### 属性

* ringView_shadow_width ： 阴影宽度
* ringView_shadow_color ： 阴影颜色
* ringView_ring_margin ： 内环外边距
* ringView_ring_width ： 内环宽度
* ringView_ring_start_color ： 内环开头颜色
* ringView_ring_end_color ： 内环末尾颜色
* ringView_ring_progress ： 进度
* ringView_ring_progress_unit ： 进度单位
* ringView_ring_max ： 最大进度
* ringView_background_color ： 背景颜色
* ringView_text_color ： 文本颜色
* ringView_text ： 文本
* ringView_text_size ： 文本字体大小
* ringView_text_margin ： 文本边距
* ringView_text_auto_color ： 文本颜色是否随进度颜色变化而变化
* ...
