package com.github.zamponimarco.cubescocktail.cooldown;

import com.github.zamponimarco.cubescocktail.key.Key;
import com.github.zamponimarco.cubescocktail.key.Keyed;

import java.util.ArrayList;
import java.util.List;

public interface Cooldownable extends Keyed {

    List<Key> cooldownableObjects = new ArrayList<>();

    default void registerKeyed() {
        keyedObjects.add(getKey());
        cooldownableObjects.add(getKey());
    }

    default void unregisterKeyed() {
        keyedObjects.remove(getKey());
        cooldownableObjects.remove(getKey());
    }


    CooldownOptions getCooldownOptions();

}
