package com.zk.sample.module.binding.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zk.sample.BR;

/**
 * ================================================
 * Created by zhaokai on 2017/3/23.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class User extends BaseObservable {

    @Bindable
    public String name;
    @Bindable
    public String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
