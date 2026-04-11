package com.github.zamponimarco.cubescocktail.manager;

import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.key.Key;
import com.github.zamponimarco.cubescocktail.timer.TimerInfo;
import com.github.zamponimarco.cubescocktail.timer.Timerable;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class TimerManager {

    private final Map<LivingEntity, List<TimerInfo>> timers;

    public TimerManager() {
        timers = new HashMap<>();
    }

    public void removeTimers(LivingEntity entity, Key key) {
        TimerInfo info = getTimerInfo(entity, key);
        if (info != null) {
            Bukkit.getScheduler().cancelTask(info.getTask());
            timers.get(entity).remove(info);
        }
    }

    public void removeAllTimers(LivingEntity entity) {
        if (timers.containsKey(entity)) {
            timers.get(entity).forEach(info -> Bukkit.getScheduler().cancelTask(info.getTask()));
            timers.remove(entity);
        }
    }

    public void addNewTimers(LivingEntity entity, Timerable timerable) {
        List<TimerInfo> infos = timers.computeIfAbsent(entity, e -> Lists.newArrayList());
        Key key = timerable.getKey();
        if (getTimerInfo(entity, key) == null)
            infos.add(new TimerInfo(key, startTimerTask(entity, timerable)));
    }

    private int startTimerTask(LivingEntity entity, Timerable timerable) {
        return timerable.getTask(entity).runTaskTimer(CubesCocktail.getInstance(), 0, timerable.getTimer()).
                getTaskId();
    }

    private TimerInfo getTimerInfo(LivingEntity e, Key key) {
        if (timers.containsKey(e)) {
            return timers.get(e).stream().filter(info -> info.getKey().equals(key)).
                    findFirst().orElse(null);
        }
        return null;
    }
}
