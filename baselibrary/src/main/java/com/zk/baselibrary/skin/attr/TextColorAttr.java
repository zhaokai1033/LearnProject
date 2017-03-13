package com.zk.baselibrary.skin.attr;

import android.view.View;
import android.widget.TextView;

import com.zk.baselibrary.skin.attr.base.SkinAttr;
import com.zk.baselibrary.skin.utils.SkinResourcesUtils;


/**
 * Created by _SOLID
 * Date:2016/4/13
 * Time:22:53
 */
public class TextColorAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                tv.setTextColor(SkinResourcesUtils.getColorStateList(attrValueRefId));
            }
        }
    }
}
