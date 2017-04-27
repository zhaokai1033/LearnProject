package com.zk.sample.module.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.baselibrary.app.BaseAct;
import com.zk.sample.R;
import com.zk.sample.UIControl;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.databinding.FragmentDialogBinding;

/**
 * ================================================
 * Created by zhaokai on 2017/4/26.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class DialogFragment extends BaseFragment<FragmentDialogBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        binding.dialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIControl.showDialogType1("任意位置对话框", "可以在任意获取ApplicationContext的位置弹出");
            }
        });
        binding.dialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIControl.showDialogType2("利用Activity弹对话框", "这是利用Intent 启动一个对话框样式的Activity", DemoDialog.class);
            }
        });
        binding.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).showStateView(BaseAct.State.LOADING);
                ((BaseActivity) getActivity()).mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((BaseActivity) getActivity()).hideStateView();
                    }
                }, 5000);
            }
        });
    }
}
