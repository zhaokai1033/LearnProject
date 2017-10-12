package com.zk.sample.module.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.zk.sample.IBackService;
import com.zk.sample.INotify;


/**
 * ========================================
 * Created by zhaokai on 2017/9/30.
 * Email zhaokai1033@126.com
 * des:
 * ========================================
 */

public class BackService extends Service {

    private RemoteCallbackList<INotify> mCallBacks = new RemoteCallbackList<>();

    private IBinder mBinder = new IBackService.Stub() {

        @Override
        public void send(String aString) throws RemoteException {
            final int len = mCallBacks.beginBroadcast();
            for (int i = 0; i < len; i++) {
                try {
                    // 通知回调
                    mCallBacks.getBroadcastItem(i).notifyCallBack(aString);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mCallBacks.finishBroadcast();
        }

        @Override
        public void registerCallBack(INotify notify) throws RemoteException {
            mCallBacks.register(notify);
            notify.notifyCallBack(notify.getNotifyName() + " register");
        }

        @Override
        public void unRegisterCallBack(INotify cb) throws RemoteException {
            cb.notifyCallBack(cb.getNotifyName() + " unRegister");
            mCallBacks.unregister(cb);
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
