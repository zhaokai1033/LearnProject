package com.zk.sample.module.binding.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zk.sample.BR;

/**
 * ================================================
 * Created by zhaokai on 2017/3/24.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class UserImg extends BaseObservable {
    @Bindable
    public String title;
    @Bindable
    public String url;

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }


}
