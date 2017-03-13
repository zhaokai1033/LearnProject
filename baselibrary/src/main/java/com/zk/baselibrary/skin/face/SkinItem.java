package com.zk.baselibrary.skin.face;

import android.view.View;

import com.zk.baselibrary.skin.attr.base.SkinAttr;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:9:21
 * Desc:store view and attribute collection
 */
public class SkinItem {

    public View view;

    public List<SkinAttr> attrs;

    public SkinItem() {
        attrs = new ArrayList<>();
    }

    public void apply() {
        if (attrs == null || attrs.size() == 0) {
            return;
        }
        for (SkinAttr at : attrs) {
            at.apply(view);
        }
    }

    public void clean() {
        if (!(attrs == null || attrs.size() == 0)) {
            attrs.clear();
        }
    }

    @Override
    public String toString() {
        return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
    }
}
