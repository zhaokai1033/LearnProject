package com.zk.baselibrary.skin.attr;

import android.view.View;
import android.widget.ImageView;

import com.zk.baselibrary.skin.attr.base.SkinAttr;
import com.zk.baselibrary.skin.utils.SkinResourcesUtils;


/**
 * Created by _SOLID
 * Date:2017/2/15
 * Time:17:39
 * Desc:
 */

public class ImageViewSrcAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            if (isDrawable()) {
                iv.setImageDrawable(SkinResourcesUtils.getDrawable(attrValueRefId));
            } else if (isColor()) {
                iv.setBackgroundColor(SkinResourcesUtils.getColor(attrValueRefId));
            }
        }
    }
}
