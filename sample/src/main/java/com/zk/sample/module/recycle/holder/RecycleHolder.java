package com.zk.sample.module.recycle.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.baselibrary.util.http.HttpUtils;
import com.zk.baselibrary.util.http.RequestBodyBuilder;
import com.zk.sample.module.recycle.model.GitSearch;
import com.zk.sample.module.recycle.view.RecycleAdapter;
import com.zk.sample.module.recycle.view.RecycleViewFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ================================================
 * Created by zhaokai on 2017/3/27.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class RecycleHolder implements RecycleViewFragment.RecycleHolderFace {
    private static final String TAG = "RecycleHolder";
    public static RecycleHolder holder;
    private final RecycleAdapter adapter;

    private RecycleHolder() {
        adapter = new RecycleAdapter();
    }

    public static RecycleHolder getInstance() {
        if (holder == null) {
            synchronized (TAG) {
                if (holder == null) {
                    holder = new RecycleHolder();
                }
            }
        }
        return holder;
    }

    //    https://github.com/search?q=zhaokai&type=Users&utf8=✓
    private String baseUrl = "https://api.github.com/search/users";

    @Override
    public void onSearchClick(View view, final String name) {
        ToastUtil.showToast(view.getContext(), name);
//
        Observable.create(new ObservableOnSubscribe<GitSearch>() {
            @Override
            public void subscribe(ObservableEmitter<GitSearch> e) throws Exception {
                GitSearch result = HttpUtils.getResult(baseUrl,
                        RequestBodyBuilder
                                .newFormBody()
                                .add("q", name)
                        , GitSearch.class);
                e.onNext(result);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GitSearch>() {
                    @Override
                    public void accept(@NonNull GitSearch result) throws Exception {
                        adapter.setItems(result.getItems());
                        LogUtil.d(TAG, "count:" + result.getTotal_count());
                    }
                });
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }
}
