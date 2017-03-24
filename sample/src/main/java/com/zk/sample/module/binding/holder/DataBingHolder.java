package com.zk.sample.module.binding.holder;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.CompoundButton;

import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.R;
import com.zk.sample.data.DataManager;
import com.zk.sample.databinding.FragmentDataBindingBinding;
import com.zk.sample.module.binding.model.User;
import com.zk.sample.module.binding.model.UserObservable;
import com.zk.sample.module.binding.model.UserObservableImp;
import com.zk.sample.module.binding.view.DataBindingFragment;

/**
 * ================================================
 * Created by zhaokai on 2017/3/23.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings("unused")
public class DataBingHolder implements DataBindingFragment.DataBindingFace {

    private static final String TAG = "DataBingHolder";
    public static DataBingHolder holder;
    private User user;
    private UserObservable userObservable;
    private UserObservableImp userImp;

    private DataBingHolder() {
        user = new User();
        userObservable = new UserObservable();
        userImp = new UserObservableImp();
    }

    public static DataBingHolder getInstance() {
        if (holder == null) {
            synchronized (DataBingHolder.class) {
                if (holder == null) {
                    holder = new DataBingHolder();
                }
            }
        }
        return holder;
    }

    @Override
    public View.OnClickListener getResetOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDataBindingBinding binding = DataBindingUtil.findBinding(v);
                binding.getUser().setName("用户名");
            }
        };
    }

    @Override
    public View.OnClickListener getCleanOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDataBindingBinding binding = DataBindingUtil.findBinding(v);
                binding.etName.setText("");
            }
        };
    }

    @Override
    public View.OnClickListener getImageOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDataBindingBinding binding = DataBindingUtil.findBinding(v);
                binding.setImageUrl(DataManager.getRandomUrl());
            }
        };
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentDataBindingBinding binding = DataBindingUtil.findBinding(buttonView);
                switch (buttonView.getId()) {
                    case R.id.cb_event:
                        binding.llEvent.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                        break;
                    case R.id.cb_observe_able:
                        binding.tlObservable.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                        break;
                    case R.id.cb_observe_base:
                        binding.tlObserveBase.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                        break;
                    case R.id.cb_observe_field:
                        binding.tlField.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                        break;
                    case R.id.cb_observe_collection:
                        binding.tlCollection.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                        break;
                }
            }
        };
    }

    public User getUser() {
        return user;
    }

    public UserObservable getUserObservable() {
        return userObservable;
    }

    public UserObservableImp getUserImp() {
        return userImp;
    }
}
