package com.zk.baselibrary.skin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zk.baselibrary.app.BaseAct;
import com.zk.baselibrary.app.BaseFra;
import com.zk.baselibrary.skin.attr.base.DynamicAttr;
import com.zk.baselibrary.skin.face.IDynamicNewView;
import com.zk.baselibrary.skin.face.ISkinUpdate;
import com.zk.baselibrary.skin.loader.SkinInflaterFactory;
import com.zk.baselibrary.skin.loader.SkinManager;
import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.UIController;

import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:24
 * Your activity need extend this
 */
@SuppressWarnings("unused")
public abstract class SkinBaseActivity extends BaseAct implements ISkinUpdate, IDynamicNewView {

    private SkinInflaterFactory mSkinInflaterFactory;

    private final static String TAG = "SkinBaseActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory();
        mSkinInflaterFactory.setAppCompatActivity(this);
        LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, getLayoutResId());
        changeStatusColor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

    @Override
    public void onThemeUpdate() {
        LogUtil.i(TAG, "onThemeUpdate");
        mSkinInflaterFactory.applySkin();
        changeStatusColor();
    }

    public SkinInflaterFactory getInflaterFactory() {
        return mSkinInflaterFactory;
    }

    public void changeStatusColor() {
        if (!SkinConfig.isCanChangeStatusColor()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = SkinManager.getInstance().getColorPrimaryDark();
            if (color != -1) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(SkinManager.getInstance().getColorPrimaryDark());
            }
        }
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    @Override
    public void dynamicAddView(View view, String attrName, int attrValueResId) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    @Override
    public void dynamicAddFontView(TextView textView) {
        mSkinInflaterFactory.dynamicAddFontEnableView(textView);
    }

    public final void removeSkinView(View v) {
        mSkinInflaterFactory.removeSkinView(v);
    }


}
