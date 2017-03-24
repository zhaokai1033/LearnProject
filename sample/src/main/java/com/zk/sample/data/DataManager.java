package com.zk.sample.data;

import com.zk.sample.R;
import com.zk.sample.module.card.model.CardItem;
import com.zk.sample.module.theme.model.SkinBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * ================================================
 *
 * @Describe :
 * Created by zhaokai on 2017/3/9.
 * @Email zhaokai1033@126.com
 * ================================================
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class DataManager {

    private static final String TAG = "DataManager";
    private static DataManager instance;

    public static DataManager getInstance() {
        if (instance == null) {
            synchronized (TAG) {
                if (instance == null) {
                    instance = new DataManager();
                }
            }
        }
        return instance;
    }

    private DataManager() {
        initData();
    }

    public HashMap<String, SkinBean> skins = new HashMap<>();
    public List<Integer> banners = new ArrayList<>();
    public List<CardItem> cardItems = new ArrayList<>();

    private void initData() {
        skins.put("black", new SkinBean("炫酷黑"));
        skins.put("blue", new SkinBean("默认蓝"));
        skins.put("brown", new SkinBean("高贵棕"));

        banners.add(R.mipmap.a);
        banners.add(R.mipmap.b);
        banners.add(R.mipmap.c);
        banners.add(R.mipmap.d);
        banners.add(R.mipmap.a);
        banners.add(R.mipmap.b);
        banners.add(R.mipmap.c);
        banners.add(R.mipmap.d);

        cardItems.add(new CardItem(R.string.title_1, R.string.text_1, "点我啊"));
        cardItems.add(new CardItem(R.string.title_2, R.string.text_2, "别点我"));
        cardItems.add(new CardItem(R.string.title_3, R.string.text_3, "点我干嘛"));
        cardItems.add(new CardItem(R.string.title_4, R.string.text_4, "不认识你"));
        cardItems.add(new CardItem(R.string.title_1, R.string.text_1, "很熟么"));
        cardItems.add(new CardItem(R.string.title_2, R.string.text_2, "再点下"));
        cardItems.add(new CardItem(R.string.title_3, R.string.text_3, "试试"));
        cardItems.add(new CardItem(R.string.title_4, R.string.text_4, "没天理"));

    }

    private final List<String> IMG_URLS = new ArrayList<>();

    public static String getRandomUrl() {
        return getInstance().getUrlRandom();
    }

    private String getUrlRandom() {
        if (IMG_URLS.size() == 0) {
            IMG_URLS.add("http://ww2.sinaimg.cn/large/7a8aed7bjw1ex8h4lnq3oj20hs0qoadj.jpg");
            IMG_URLS.add("http://pic.meizitu.com/wp-content/uploads/2015a/10/24/01.jpg");
            IMG_URLS.add("http://img3.duitang.com/uploads/item/201504/21/20150421H3510_daQSZ.jpeg");
            IMG_URLS.add("http://pic.meizitu.com/wp-content/uploads/2015a/10/18/01.jpg");
            IMG_URLS.add("http://upload.youzu.com/king/2015/0927/134113080.jpg");
        }
        return IMG_URLS.get(new Random().nextInt(IMG_URLS.size()));
    }

    public static SkinBean getSkin(String color) {
        return getInstance().skins.get(color);
    }

    public static List<Integer> getBanners() {
        return getInstance().banners;
    }

    public static List<CardItem> getCards() {
        return getInstance().cardItems;
    }
}
