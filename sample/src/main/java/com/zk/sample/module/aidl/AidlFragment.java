package com.zk.sample.module.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.IBackService;
import com.zk.sample.INotify;
import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;

/**
 * ========================================
 * Created by zhaokai on 2017/10/12.
 * Email zhaokai1033@126.com
 * des:
 * ========================================
 */

public class AidlFragment extends BaseFragment {

    private static final String TAG = "AidlFragment";
    private TextView show;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;

    private Intent backService;
    private ServiceConnection conn;
    private IBackService mService;
    private INotify local;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_aidl;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        initView();
        initEvent();
        initService();
    }

    private void initService() {

        local = new INotify.Stub() {
            @Override
            public void notifyCallBack(String aString) throws RemoteException {
                show(aString);
            }

            @Override
            public String getNotifyName() throws RemoteException {
                return "local";
            }
        };

        backService = new Intent(getActivity(), BackService.class);

        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = IBackService.Stub.asInterface(service);
                try {
                    mService.registerCallBack(local);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                try {
                    mService.unRegisterCallBack(local);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "取消绑定");
            }
        };
    }

    private void startService() {
        getActivity().startService(backService);
    }

    private void bindService() {
        getActivity().bindService(backService, conn, Context.BIND_AUTO_CREATE);
    }

    private void initEvent() {

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mService != null) {
                    try {
                        mService.send(System.currentTimeMillis() + "");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        show(e.getMessage());
                    }
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mService.registerCallBack(local);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mService.unRegisterCallBack(local);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getActivity().unbindService(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showToast(getContext(), "未绑定");
                }
            }
        });
    }

    private void show(String message) {
        show.setText(show.getText().toString() + "\n" + message);
        int offset = show.getLineCount() * show.getLineHeight();
        if (offset > (show.getHeight() - show.getLineHeight() - 20)) {
            show.scrollTo(0, offset - show.getHeight() + show.getLineHeight() + 20);
        }
    }

    private void initView() {
        show = ((TextView) findViewById(R.id.show));
        show.setMovementMethod(ScrollingMovementMethod.getInstance());
        b1 = ((Button) findViewById(R.id.bt1));
        b1.setText("绑定服务");
        b2 = ((Button) findViewById(R.id.bt2));
        b2.setText("发送数据");
        b3 = ((Button) findViewById(R.id.bt3));
        b3.setText("注册回调");
        b4 = ((Button) findViewById(R.id.bt4));
        b4.setText("取消注册");
        b5 = ((Button) findViewById(R.id.bt5));
        b5.setText("取消绑定");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unbindService(conn);
    }
}
