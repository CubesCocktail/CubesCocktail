package com.github.zamponimarco.cubescocktail.timer;

import com.github.zamponimarco.cubescocktail.key.Key;
import com.github.zamponimarco.cubescocktail.key.Keyed;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public interface Timerable extends Keyed {

    List<Key> timerableObjects = Lists.newArrayList();

    default void registerKeyed() {
        keyedObjects.add(getKey());
        timerableObjects.add(getKey());
    }

    default void unregisterKeyed() {
        keyedObjects.remove(getKey());
        timerableObjects.remove(getKey());
    }

    int getTimer();

    BukkitRunnable getTask(LivingEntity entity);

}
