package com.solid.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    // chops a list into non-view sublists of length L
    public static <T> List<List<T>> partition(final List<T> list, final int targetSize) {
        final List<List<T>> parts = new ArrayList<>();
        final int size = list.size();
        for (int i = 0; i < size; i += targetSize) {
            parts.add(new ArrayList<>(
                                       list.subList(i, Math.min(size, i + targetSize))));
        }
        return parts;
    }
}
