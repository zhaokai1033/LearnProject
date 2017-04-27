package com.zk.sample.module.dialog;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.R;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.databinding.ActivityDialogBinding;

/**
 * ================================================
 * Created by zhaokai on 2017/4/26.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class DemoDialog extends BaseActivity<ActivityDialogBinding> {

    private static final int FINISH = 10021;

    @Override
    public int getFragmentContentId() {
        return 0;
    }

    @Override
    protected void onHandleMessage(Message msg) {
        switch (msg.what) {
            case FINISH:
                ToastUtil.showToast(getApplicationContext(), ((String) msg.obj));
                finish();
                break;
        }
    }

    @Override
    protected int getStateContentView() {
        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityCreated(@Nullable Bundle savedInstanceState) {
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain(mHandler, FINISH, String.valueOf(binding.username.getText().toString()) + ":" + binding.password.getText().toString());
                mHandler.sendMessageDelayed(message, 1000);
            }
        });
    }
}
