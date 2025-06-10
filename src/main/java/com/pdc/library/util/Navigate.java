package com.pdc.library.util;

import com.pdc.library.view.MenuAction;

public record Navigate(MenuAction targetMenu, String path) {
    public static Navigate targetless(MenuAction targetMenu) {
        return new Navigate(targetMenu, null);
    }
}
