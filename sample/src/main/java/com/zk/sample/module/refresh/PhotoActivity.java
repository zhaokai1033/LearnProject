package com.zk.sample.module.refresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.zk.baselibrary.widget.refresh.RefreshListener;
import com.zk.baselibrary.widget.refresh.RefreshLayout;
import com.zk.sample.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {
    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        setupRecyclerView((RecyclerView) findViewById(R.id.recyclerview));

        findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setupRecyclerView(RecyclerView rv) {
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        photoAdapter = new PhotoAdapter();
        rv.setAdapter(photoAdapter);

        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refresh);
//        ProgressLayout headerView = new ProgressLayout(this);
//        BezierLayout headerView = new BezierLayout(this);
//        refreshLayout.setRefreshHeader(headerView);
        refreshLayout.setMaxHeadHeight(140);
//        refreshLayout.setFloatRefresh(true);
//        refreshLayout.setPureScrollModeOn(true);
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setAutoLoadMore(true);
        TextView textView = new TextView(this);
        textView.setText("这是一个固定的头部");
        refreshLayout.addHeader(textView);

//        addHeader();
        refreshCard();
        findViewById(R.id.ib_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.startRefresh();
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshCard();
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreCard();
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });

//        refreshLayout.startRefresh();
    }

    void refreshCard() {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo("chest nut", R.mipmap.photo1));
        photos.add(new Photo("fish", R.mipmap.photo2));
        photos.add(new Photo("cat", R.mipmap.photo10));
        photos.add(new Photo("guitar", R.mipmap.photo3));
        photos.add(new Photo("common-hazel", R.mipmap.photo4));
        photos.add(new Photo("cherry", R.mipmap.photo5));
        photos.add(new Photo("flower details", R.mipmap.photo6));
        photos.add(new Photo("tree", R.mipmap.photo7));
        photos.add(new Photo("blue berries", R.mipmap.photo8));
        photos.add(new Photo("snow man", R.mipmap.photo9));
        photoAdapter.setDataList(photos);
    }

    void loadMoreCard() {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo("chest nut", R.mipmap.photo1));
        photos.add(new Photo("fish", R.mipmap.photo2));
        photos.add(new Photo("cat", R.mipmap.photo10));
        photos.add(new Photo("guitar", R.mipmap.photo3));
        photos.add(new Photo("common-hazel", R.mipmap.photo4));
        photos.add(new Photo("cherry", R.mipmap.photo5));
        photos.add(new Photo("flower details", R.mipmap.photo6));
        photos.add(new Photo("tree", R.mipmap.photo7));
        photos.add(new Photo("blue berries", R.mipmap.photo8));
        photos.add(new Photo("snow man", R.mipmap.photo9));

        photoAdapter.addItems(photos);
    }
}
