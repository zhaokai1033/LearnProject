// BackService.aidl
package com.zk.sample;
import com.zk.sample.INotify;

// Declare any non-default types here with import statements

interface IBackService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void send(String aString);

    void registerCallBack(INotify notify);

    void unRegisterCallBack(INotify cb);
}
