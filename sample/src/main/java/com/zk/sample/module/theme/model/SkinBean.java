package com.zk.sample.module.theme.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zk.sample.BR;


/**
 * ================================================
 *
 * @Describe :
 * Created by zhaokai on 2017/3/8.
 * @Email zhaokai1033@126.com
 * ================================================
 */

public class SkinBean extends BaseObservable {

    public String name;

    @Bindable
    public boolean current;

    public SkinBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
        notifyPropertyChanged(BR.current);
    }
}
