package com.zk.sample.module.card.holder;

import android.view.View;
import android.widget.Button;

import com.zk.baselibrary.util.ToastUtil;

/**
 * ================================================
 * Created by zhaokai on 2017/3/24.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class CardHolder {

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtil.showToast(v.getContext(), ((Button) v).getText().toString());
        }
    };
}
