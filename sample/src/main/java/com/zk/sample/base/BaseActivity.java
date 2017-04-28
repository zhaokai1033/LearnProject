package com.zk.sample.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zk.baselibrary.app.BaseFra;
import com.zk.baselibrary.skin.SkinBaseActivity;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.baselibrary.widget.WaveTextView;
import com.zk.sample.R;
import com.zk.sample.module.binding.view.LoginDialogFragment;

/**
 * ================================================
 *
 * @Describe :
 * Created by zhaokai on 2017/3/9.
 * @Email zhaokai1033@126.com
 * ================================================
 */

public abstract class BaseActivity<VDB extends ViewDataBinding> extends SkinBaseActivity implements LoginDialogFragment.LoginInputListener {

    protected VDB binding;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
        onCreated(savedInstanceState);
    }

    /**
     * Activity 创建时调用
     */
    protected abstract void onCreated(Bundle savedInstanceState);

    @Override
    protected void onPostCreated(Bundle savedInstanceState) {

    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onLoginInputComplete(String username, String password) {
        Toast.makeText(this, "帐号：" + username + ",  密码 :" + password,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    protected View getLoadingView() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_loading, null);
        final WaveTextView textView = ((WaveTextView) view.findViewById(R.id.loading));
        textView.setText("加载中");
        textView.setTextSize(28);
        textView.setGravity(Gravity.CENTER);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.startWave();
            }
        });
        view.setBackgroundColor(Color.GRAY);
        return view;
    }

    @Override
    protected View getEmptyView() {
        TextView textView = new TextView(this);
        textView.setText("没东西呢");
        textView.setTextSize(28);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.GRAY);
        return textView;
    }

    @Override
    protected View getErrorView() {
        TextView textView = new TextView(this);
        textView.setText("出错了……");
        textView.setTextSize(28);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.GRAY);
        return textView;
    }

    @Override
    protected void onStateClick(View view, State state) {
        ToastUtil.showShortToast(this, state.toString());
    }

    /**
     * 获取当前页可替换的FrameLayout 资源
     */
    @SuppressWarnings("unused")
    public abstract int getFragmentContentId();

    public BaseFra changeFragment(BaseFra current, BaseFra target, boolean isNeedRefresh, boolean canBack) {
        if (getFragmentContentId() == 0) {
            throw new IllegalArgumentException("FragmentContentId cannot be 0 ");
        }
        return changeFragment(current, target, getFragmentContentId(), isNeedRefresh, canBack);
    }
}
