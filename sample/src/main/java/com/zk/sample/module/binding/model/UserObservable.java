package com.zk.sample.module.binding.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

/**
 * ================================================
 * Created by zhaokai on 2017/3/23.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class UserObservable {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

}
