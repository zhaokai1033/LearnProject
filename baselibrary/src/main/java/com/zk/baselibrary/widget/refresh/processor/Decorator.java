package com.zk.baselibrary.widget.refresh.processor;

import com.zk.baselibrary.widget.refresh.RefreshLayout;

public abstract class Decorator implements IDecorator {
    protected IDecorator decorator;
    protected RefreshLayout.CoContext cp;

    public Decorator(RefreshLayout.CoContext processor, IDecorator decorator1) {
        cp = processor;
        decorator = decorator1;
    }
}
