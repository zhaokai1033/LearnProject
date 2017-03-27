package com.zk.sample.module.binding.model;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.zk.sample.BR;

/**
 * ================================================
 * Created by zhaokai on 2017/3/24.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class UserObservableImp implements Observable {

    private PropertyChangeRegistry mRegistry = new PropertyChangeRegistry();

    @Bindable
    public String name;
    @Bindable
    public String password;

    public void setName(String name) {
        this.name = name;
        mRegistry.notifyChange(this, BR.name);
    }

    public void setPassword(String password) {
        this.password = password;
        mRegistry.notifyChange(this, BR.password);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback cb) {
        mRegistry.add(cb);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback cb) {
        mRegistry.remove(cb);
    }
}
