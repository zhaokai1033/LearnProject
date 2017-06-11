package com.zk.sample.module.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zk.baselibrary.util.ClassUtil;
import com.zk.baselibrary.util.SystemUtil;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.baselibrary.util.UIUtil;
import com.zk.sample.R;
import com.zk.sample.UIControl;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.base.activity.TestActivity;
import com.zk.sample.databinding.FragmentHomeBinding;
import com.zk.sample.module.behavior.BehaviorActivity;
import com.zk.sample.module.binding.view.DataBindingFragment;
import com.zk.sample.module.card.view.DemoCardFragment;
import com.zk.sample.module.dialog.DialogFragment;
import com.zk.sample.module.file.FileListFragment;
import com.zk.sample.module.permission.PermissionFragment;
import com.zk.sample.module.recycle.view.RecycleViewFragment;
import com.zk.sample.module.refresh.PhotoActivity;
import com.zk.sample.module.system.view.SystemFragment;
import com.zk.sample.module.view.ViewFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/6.
 * Email zhaokai1033@126.com
 * ================================================
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    public static final String[] items = new String[]{
            "卡片", "DataBinding->Observe+event", "DataBinding->recycleView", "SystemUi",
            "切换方向", "文字示例", "控件示例", "权限示例", "文件管理", "对话框", "侧滑返回", "反射调用",
            "滑动行为", "列表刷新"
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setSwipeBackEnable(false);
        initRecycle();
    }

    private void initRecycle() {
        binding.refresh.setPureScrollModeOn();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recycle.setLayoutManager(layoutManager);
        HomeAdapter adapter = new HomeAdapter(Arrays.asList(items)) {
            @Override
            protected View.OnClickListener getOnItemClick() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initClick(((TextView) v).getText().toString(), v);
                    }
                };
            }
        };
//        TextView header = new TextView(getContext());
//        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.dip2px(getContext(), 80)));
//        header.setText("这是头部");
//        adapter.addHeader(header);
//        TextView footer = new TextView(getContext());
//        footer.setText("这是尾部");
//        footer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.dip2px(getContext(), 80)));
//        adapter.addFooter(footer);
        binding.recycle.setAdapter(adapter);
    }

    private void initClick(String s, View v) {
        switch (s) {
            case "卡片":
                UIControl.showCustomFragment(((BaseActivity) getActivity()), DemoCardFragment.newInstance());
                break;
            case "DataBinding->Observe+event":
                UIControl.showCustomFragment(((BaseActivity) getActivity()), DataBindingFragment.newInstance());
                break;
            case "DataBinding->recycleView":
                UIControl.showCustomFragment(((BaseActivity) getActivity()), RecycleViewFragment.newInstance());
                break;
            case "SystemUi":
                UIControl.showCustomFragment(((BaseActivity) getActivity()), SystemFragment.newInstance());
                break;
            case "切换方向":
                SystemUtil.changeDirection(getActivity());
                break;
            case "文字示例":
                shaderText((TextView) v);
                getActivity().setTitle("测试标题");
                break;
            case "控件示例":
                UIControl.showCustomFragment(((BaseActivity) getActivity()), ClassUtil.createInstance(ViewFragment.class));
                break;
            case "权限示例":
                UIControl.showCustomFragment(((BaseActivity) getActivity()), ClassUtil.createInstance(PermissionFragment.class));
                break;
            case "文件管理":
                UIControl.showCustomFragment(((BaseActivity) getActivity()), ClassUtil.createInstance(FileListFragment.class));
                break;
            case "对话框":
                UIControl.showCustomFragment(((BaseActivity) getActivity()), ClassUtil.createInstance(DialogFragment.class));
                break;
            case "侧滑返回":
                UIControl.startActivity(getActivity(), new Intent(getContext(), TestActivity.class));
                break;
            case "反射调用":
                RejectTest test = new RejectTest();
                try {
                    Method method = RejectTest.class.getMethod("method", String.class);
                    method.invoke(test, "参数123");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case "滑动行为":
                Intent intent = new Intent(getActivity(), BehaviorActivity.class);
                startActivity(intent);
                break;
            case "列表刷新":
                startActivity(new Intent(getActivity(), PhotoActivity.class));
                break;
        }
    }

    private void shaderText(TextView v) {
        Shader shader = v.getPaint().getShader();
        if (shader == null) {
            shader = new LinearGradient(0, 0, 0, 50, Color.parseColor("#a35726"), Color.parseColor("#d88f2d"), Shader.TileMode.CLAMP);
            v.getPaint().setShader(shader);
        } else {
            v.getPaint().setShader(null);
            v.invalidate();
        }
    }

    public class RejectTest {
        public void method(String arg) {
            ToastUtil.showToast(getActivity(), "成功调用" + arg);
        }
    }

}
