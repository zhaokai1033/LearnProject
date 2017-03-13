package com.zk.sample.app;

import com.zk.baselibrary.app.BaseApplication;
import com.zk.baselibrary.skin.SkinConfig;
import com.zk.baselibrary.skin.loader.SkinManager;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/6.
 * Email zhaokai1033@126.com
 * <p>
 * ================================================
 */

public class SampleApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initConfig();
    }

    private void initConfig() {
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.setCanChangeFont(true);
        SkinConfig.setDebug(true);
        SkinConfig.setTransitionAnim(false);
        SkinManager.getInstance().init(this);
    }
}
