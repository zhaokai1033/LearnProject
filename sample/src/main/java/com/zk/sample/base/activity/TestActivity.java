package com.zk.sample.base.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zk.baselibrary.widget.SwipeCloseLayout;
import com.zk.sample.R;

public class TestActivity extends AppCompatActivity {

    private SwipeCloseLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        layout = new SwipeCloseLayout(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        layout.injectWindow();
    }

}
