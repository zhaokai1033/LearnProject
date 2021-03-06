package com.zk.sample.module.card.adpater;

import android.support.v7.widget.CardView;

/**
 * ================================================
 * Created by zhaokai on 2017/3/21.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 12;

    float getBaseElevation();

    CardView getCardViewAt(int position);

}
