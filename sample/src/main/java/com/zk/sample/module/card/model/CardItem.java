package com.zk.sample.module.card.model;


public class CardItem {

    private final String btName;
    private int textResource;
    private int titleResource;

    public CardItem(int title, int text, String btName) {
        titleResource = title;
        textResource = text;
        this.btName = btName;
    }

    public int getText() {
        return textResource;
    }

    public int getTitle() {
        return titleResource;
    }

    public String getBtName() {
        return btName;
    }
}
