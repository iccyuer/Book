package com.example.book.library.base;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.xutils.x;

/**
 * Created by Administractor on 2016/12/28.
 */

public abstract class BaseApp extends Application {
    public static Context mContextGlobal;//全局上下文对象
    public static int mTitleLayoutID;//这样做当前应用使用同一个标题布局

    @Override
    public void onCreate() {
        super.onCreate();
        mContextGlobal = this;
        initXutils();
        initLogger();
        mTitleLayoutID = initTitleBar();
        initOthers();
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);//线程优先级
        config.denyCacheImageMultipleSizesInMemory();//多尺寸缓存
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());//缓存文件加密规则
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.FIFO);//图片加载顺序  LIFO  FIFO
//        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }


    private void initLogger() {
        Logger.init(this.getPackageName())      // default PRETTYLOGGER or use just init()
                .methodCount(1)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(isDebug() ? LogLevel.FULL : LogLevel.NONE);
    }

    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(isDebug()); // 是否输出debug日志, 开启debug会影响性能.
    }

    public abstract boolean isDebug();
    public abstract void initOthers();
    public abstract int initTitleBar();

}
