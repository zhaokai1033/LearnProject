package com.zk.baselibrary.base;

import android.app.Application;

import com.zk.baselibrary.app.BaseApplication;
import com.zk.baselibrary.skin.SkinConfig;
import com.zk.baselibrary.skin.loader.SkinManager;
import com.zk.baselibrary.util.FileUtil;

import java.io.File;
import java.io.IOException;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:54
 * Desc:
 */
public class SkinBaseApplication extends BaseApplication {

    public void onCreate() {

        super.onCreate();
        initSkinLoader();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {
        setUpSkinFile();
        SkinManager.getInstance().init(this);
    }

    private void setUpSkinFile() {
        try {
            String[] skinFiles = getAssets().list(SkinConfig.SKIN_DIR_NAME);
            for (String fileName : skinFiles) {
                File file = new File(FileUtil.getSkinDir(this), fileName);
                if (!file.exists())
                    FileUtil.moveRawToDir(this, fileName, FileUtil.getSkinDir(this).getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
