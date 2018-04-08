package com.mydways.aadabhyd.latestscrolling.model;

/**
 * @author Kishore Adda on 8/4/18.
 */
public enum DisplayViewType {
    LATEST_NEWS(1), CATEGORY_NEWS(2);
    int type;

    DisplayViewType(int displayView) {
        type = displayView;
    }
}
