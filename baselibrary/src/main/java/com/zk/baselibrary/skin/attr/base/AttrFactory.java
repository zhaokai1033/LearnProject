package com.zk.baselibrary.skin.attr.base;

import com.zk.baselibrary.skin.attr.BackgroundAttr;
import com.zk.baselibrary.skin.attr.ImageViewSrcAttr;
import com.zk.baselibrary.skin.attr.NavigationViewAttr;
import com.zk.baselibrary.skin.attr.TextColorAttr;

import java.util.HashMap;

/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:9:47
 */
public class AttrFactory {

    private static final String BACKGROUND = "background";
    private static final String TEXT_COLOR = "textColor";
    private static final String SRC = "src";
    private static final String NAVIGATION_VIEW_MENU = "navigationViewMenu";

    private static AttrFactory instance;
    private HashMap<String, SkinAttr> sSupportAttr;

    void init() {
        sSupportAttr = new HashMap<>();
        sSupportAttr.put(BACKGROUND, new BackgroundAttr());
        sSupportAttr.put(TEXT_COLOR, new TextColorAttr());
        sSupportAttr.put(SRC, new ImageViewSrcAttr());
        sSupportAttr.put(NAVIGATION_VIEW_MENU, new NavigationViewAttr());
    }

    private AttrFactory() {
    }

    public static AttrFactory getInstance() {
        if (instance == null) {
            synchronized (AttrFactory.class) {
                if (instance == null) {
                    instance = new AttrFactory();
                    instance.init();
                }
            }
        }
        return instance;
    }


    public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {

        SkinAttr mSkinAttr = getInstance().sSupportAttr.get(attrName).clone();
        if (mSkinAttr == null) return null;
        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefId = attrValueRefId;
        mSkinAttr.attrValueRefName = attrValueRefName;
        mSkinAttr.attrValueTypeName = typeName;
        return mSkinAttr;
    }

    /**
     * check current attribute if can be support
     *
     * @param attrName attribute name
     * @return true : supported <br>
     * false: not supported
     */
    public static boolean isSupportedAttr(String attrName) {
        return getInstance().sSupportAttr.containsKey(attrName);
    }

    /**
     * add support's attribute
     *
     * @param attrName attribute name
     * @param skinAttr skin attribute
     */
    public static void addSupportAttr(String attrName, SkinAttr skinAttr) {
        getInstance().sSupportAttr.put(attrName, skinAttr);
    }
}
