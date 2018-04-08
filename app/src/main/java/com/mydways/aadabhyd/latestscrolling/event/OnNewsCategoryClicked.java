package com.mydways.aadabhyd.latestscrolling.event;

import com.mydways.aadabhyd.latestscrolling.model.DrawerItem; /**
 * @author Kishore Adda on 8/4/18.
 */
public class OnNewsCategoryClicked {

    private DrawerItem item;

    public DrawerItem getItem() {
        return item;
    }

    public OnNewsCategoryClicked(DrawerItem drawerItem) {
        item = drawerItem;
    }
}
