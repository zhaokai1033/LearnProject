package com.zk.sample.module.binding.view;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ActionMode;
import android.view.View;
import android.widget.CompoundButton;

import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.databinding.FragmentDataBindingBinding;
import com.zk.sample.module.binding.BindingEvent;
import com.zk.sample.module.binding.holder.DataBingHolder;
import com.zk.sample.module.binding.model.User;
import com.zk.sample.module.binding.model.UserImg;

/**
 * ================================================
 * Created by zhaokai on 2017/3/23.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class DataBindingFragment extends BaseFragment<FragmentDataBindingBinding> {

    private static final String TAG = "DataBindingFragment";
    private ActionMode mActionMode;


    public static DataBindingFragment newInstance() {

        Bundle args = new Bundle();

        DataBindingFragment fragment = new DataBindingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_data_binding;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //此处使用单例模式保证数据源的唯一性 可以确保转屏等操作是用的统一数据
        DataBingHolder holder = DataBingHolder.getInstance();
        binding.setUser(holder.getUser());
        binding.setUserObservable(holder.getUserObservable());
        binding.setUserImp(holder.getUserImp());
        binding.setHolder(holder);

        ObservableMap<String, String> userMap = new ObservableArrayMap<>();
        userMap.put("name", "示例");
        userMap.put("password", "密码");
        binding.setUserMap(userMap);
        binding.setEvent(new BindingEvent());
        binding.setUserImg(new UserImg());

        copyTest();
    }

    private void copyTest() {
//        binding.etName.setCustomSelectionActionModeCallback(mActionModeCallback);
//        binding.etName.setOnLongClickListener(longPressListener);
    }

    public interface DataBindingFace {

        void getOnResetClickListener(User user);

        View.OnClickListener getCleanOnClickListener();

        View.OnClickListener getImageOnClickListener();

        CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener();

    }



}
