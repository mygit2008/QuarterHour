package example.com.quarterhour;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * @author zhangjunyou
 * @date 2018/7/17
 * @description
 * @Copyright 版权所有, 未经授权不得转载其他 .
 */

public class MyApp extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(context);
        AutoLayoutConifg.getInstance().useDeviceSize();
    }
}
