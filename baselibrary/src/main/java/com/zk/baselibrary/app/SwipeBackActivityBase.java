package com.zk.baselibrary.app;

import com.zk.baselibrary.widget.SwipeBackLayout;

/**
 * @author Yrom
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    void scrollToFinishActivity();

}
