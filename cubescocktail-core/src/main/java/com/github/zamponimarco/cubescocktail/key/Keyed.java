package com.github.zamponimarco.cubescocktail.key;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public interface Keyed {

    List<Key> keyedObjects = Lists.newArrayList();

    default void registerKeyed() {
        keyedObjects.add(getKey());
    }

    default void unregisterKeyed() {
        keyedObjects.remove(getKey());
    }

    Key getKey();

}
