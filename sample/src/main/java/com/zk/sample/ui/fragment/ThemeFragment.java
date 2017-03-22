package com.zk.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.zk.sample.ui.base.BaseFragment;
import com.zk.sample.data.DataManager;
import com.zk.sample.databinding.FragmentThemeBinding;

import java.io.File;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/7.
 * Email zhaokai1033@126.com
 * ================================================
 */

public class ThemeFragment extends BaseFragment<FragmentThemeBinding> {

    private static final String TAG = "ThemeFragment";

    public static ThemeFragment newInstance() {

        Bundle args = new Bundle();

        ThemeFragment fragment = new ThemeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_theme;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String skin = SkinConfig.getCustomSkinPath(getContext());
        if (!TextUtils.isEmpty(skin)) {
            switch (new File(skin).getName()) {
                case "skin_black.skin":
                    binding.desBlack.setText("使用中");
                    break;
                case "skin_brown.skin":
                    binding.desBrown.setText("使用中");
                    break;
                default:
                    binding.desBlue.setText("使用中");
                    break;
            }
        }
        binding.setSkin3(DataManager.getSkin("black"));
        binding.llBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkin("skin_black.skin");
                binding.desBlack.setText("使用中");
                binding.desBlue.setText("");
                binding.desBrown.setText("");
            }
        });
        binding.setSkin1(DataManager.getSkin("blue"));
        binding.llBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().restoreDefaultTheme();
                binding.desBlack.setText("");
                binding.desBlue.setText("使用中");
                binding.desBrown.setText("");
            }
        });
        binding.setSkin2(DataManager.getSkin("brown"));
        binding.llBrown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkin("skin_brown.skin");
                binding.desBlack.setText("");
                binding.desBlue.setText("");
                binding.desBrown.setText("使用中");
            }
        });
        binding.notifyChange();
    }

    /**
     * 更换皮肤
     *
     * @param name
     */
    private void setSkin(String name) {
        String skinFullName = FileUtil.getSkinDirPath(getContext()) + File.separator + name;
        File skin = new File(skinFullName);
        if (!skin.exists()) {
            FileUtil.moveRawToDir(getContext(), "skin_brown.skin", skinFullName);
            if (!skin.exists()) {
                Toast.makeText(getContext(), "请检查" + skinFullName + "是否存在", Toast.LENGTH_SHORT).show();
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
                        ToastUtil.showToast(getContext(), "切换成功");
                    }

                    @Override
                    public void onFailed(String errMsg) {

                        LogUtil.i(TAG, "loadSkinFail" + errMsg);
                        ToastUtil.showToast(getContext(), "切换失败");
                    }

                    @Override
                    public void onProgress(int progress) {

                    }

                });
    }
}
