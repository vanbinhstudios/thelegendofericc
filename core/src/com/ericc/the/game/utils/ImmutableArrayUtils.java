package com.ericc.the.game.utils;

import com.badlogic.ashley.utils.ImmutableArray;

import java.util.ArrayList;

public class ImmutableArrayUtils {
    public static <E> ArrayList<E> toArrayList(ImmutableArray<E> immutableArray) {
        ArrayList<E> arrayList = new ArrayList<>();
        for (E e : immutableArray) {
            arrayList.add(e);
        }
        return arrayList;
    }
}
