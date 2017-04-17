package com.zk.sample.module.demo;

import android.view.View;

/**
 * ================================================
 * Created by zhaokai on 2017/4/13.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class ViewDemoHolder {

    private String name;
    private String number = "";


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static ViewDemoHolder newInstance() {
        return new ViewDemoHolder();
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
