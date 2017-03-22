package com.zk.sample.module.theme.viewModel;

import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zk.baselibrary.skin.SkinConfig;
import com.zk.baselibrary.skin.loader.SkinManager;
import com.zk.baselibrary.util.FileUtil;
import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.module.theme.view.ThemeFragment;
import com.zk.sample.data.DataManager;
import com.zk.sample.module.theme.model.SkinBean;

import java.io.File;

/**
 * ================================================
 * Created by zhaokai on 2017/3/22.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class ThemeHolder extends BaseObservable implements ThemeFragment.DataBindingFace {

    private static final String TAG = "ThemeHolder";

    private final BaseFragment fragment;

    private SkinBean blue;
    private SkinBean black;
    private SkinBean brown;


    public ThemeHolder(BaseFragment fragment) {
        this.fragment = fragment;
        this.black = DataManager.getSkin("black");
        this.blue = DataManager.getSkin("blue");
        this.brown = DataManager.getSkin("brown");
        initDefault();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_black:
                    setSkin("skin_black.skin");
                    black.setCurrent(true);
                    brown.setCurrent(false);
                    blue.setCurrent(false);
                    break;
                case R.id.ll_blue:
                    black.setCurrent(false);
                    brown.setCurrent(false);
                    blue.setCurrent(true);
                    SkinManager.getInstance().restoreDefaultTheme();
                    break;
                case R.id.ll_brown:
                    setSkin("skin_brown.skin");
                    black.setCurrent(false);
                    brown.setCurrent(true);
                    blue.setCurrent(false);
                    break;
            }
        }
    };

    /**
     * 更换皮肤
     */
    private void setSkin(String name) {
        String skinFullName = FileUtil.getSkinDirPath(fragment.getContext()) + File.separator + name;
        File skin = new File(skinFullName);
        if (!skin.exists()) {
            FileUtil.moveRawToDir(fragment.getContext(), "skin_brown.skin", skinFullName);
            if (!skin.exists()) {
                Toast.makeText(fragment.getContext(), "请检查" + skinFullName + "是否存在", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        SkinManager.getInstance().loadSkin(skin.getAbsolutePath(),
                new SkinManager.SkinLoaderListener() {
                    @Override
                    public void onStart() {
                        Log.e(TAG, "loadSkinStart");
                    }

                    @Override
                    public void onSuccess() {
                        LogUtil.i(TAG, "loadSkinSuccess");
                        ToastUtil.showToast(fragment.getContext(), "切换成功");
                    }

                    @Override
                    public void onFailed(String errMsg) {

                        LogUtil.i(TAG, "loadSkinFail" + errMsg);
                        ToastUtil.showToast(fragment.getContext(), "切换失败");
                    }

                    @Override
                    public void onProgress(int progress) {

                    }

                });
    }

    @Override
    public SkinBean getSkinBlack() {
        return black;
    }

    @Override
    public SkinBean getSkinBrown() {
        return brown;
    }

    @Override
    public SkinBean getSkinBlue() {
        return blue;
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return listener;
    }

    private void initDefault() {
        String skin = SkinConfig.getCustomSkinPath(fragment.getContext());
        if (!TextUtils.isEmpty(skin)) {
            switch (new File(skin).getName()) {
                case "skin_black.skin":
                    black.setCurrent(true);
                    break;
                case "skin_brown.skin":
                    brown.setCurrent(true);
                    break;
                default:
                    blue.setCurrent(true);
                    break;
            }
        }
    }
}
