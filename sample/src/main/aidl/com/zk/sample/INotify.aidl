// INotify.aidl
package com.zk.sample;

// Declare any non-default types here with import statements

interface INotify {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void notifyCallBack(String aString);

    String getNotifyName();
}
