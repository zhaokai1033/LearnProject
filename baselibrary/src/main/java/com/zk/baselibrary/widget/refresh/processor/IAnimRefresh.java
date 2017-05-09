package com.zk.baselibrary.widget.refresh.processor;



public interface IAnimRefresh {
    void scrollHeadByMove(float moveY);
    void scrollBottomByMove(float moveY);
    void animHeadToRefresh();
    void animHeadBack(boolean isFinishRefresh);
    void animHeadHideByVy(int vy);
    void animBottomToLoad();
    void animBottomBack(boolean isFinishRefresh);
    void animBottomHideByVy(int vy);
}
