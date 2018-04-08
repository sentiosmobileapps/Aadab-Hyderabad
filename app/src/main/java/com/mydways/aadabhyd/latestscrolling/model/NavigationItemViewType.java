package com.mydways.aadabhyd.latestscrolling.model;

/**
 * @author Kishore Adda on 8/4/18.
 */
public enum NavigationItemViewType {
    VIEW_TYPE_HEADER(1), VIEW_TYPE_CATEGORY(2);
    public int value;
    NavigationItemViewType(int viewType){
        value = viewType;
    }
}
