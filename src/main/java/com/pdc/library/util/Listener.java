package com.pdc.library.util;

public interface Listener<T> {
    void onEvent(T event);
}
