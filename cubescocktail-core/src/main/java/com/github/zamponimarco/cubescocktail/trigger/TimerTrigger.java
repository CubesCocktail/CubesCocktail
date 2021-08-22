package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Deprecated
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lTimer trigger", description = "gui.trigger.timer.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlOGNmZjc1ZjdkNDMzMjYwYWYxZWNiMmY3NzNiNGJjMzgxZDk1MWRlNGUyZWI2NjE0MjM3NzlhNTkwZTcyYiJ9fX0=")
public class TimerTrigger extends Trigger {

    private int timer;

    public TimerTrigger() {

    }

    public TimerTrigger(Map<String, Object> map) {
        super(map);
        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        this.timer = (int) map.getOrDefault("timer", 20);
        List<Action> onWearerActions = (List<Action>) map.get("onWearerActions");
        if (onWearerActions != null && !onWearerActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new CasterTarget(), onWearerActions));
        }
    }

    @Override
    public String getName() {
        return "&cTimer &6&ltrigger";
    }

    @Override
    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList();
    }

    @Override
    public Collection<Class<? extends Source>> getPossibleSources() {
        return Lists.newArrayList();
    }

    @Override
    public Trigger clone() {
        return new TimerTrigger();
    }
}
