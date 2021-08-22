package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.LocationSource;
import com.github.zamponimarco.cubescocktail.source.RayTraceSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.github.zamponimarco.cubescocktail.trgt.LocationTarget;
import com.github.zamponimarco.cubescocktail.trgt.RayTraceTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.*;

@Getter
@Enumerable.Child
@Enumerable.Displayable(condition = "isCasterOnlyPlayer", name = "&c&lOn Block Break", description = "gui.trigger.interact.block.break.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjZlYTIxMzU4Mzg0NjE1MzQzNzJmMmRhNmM4NjJkMjFjZDVmM2QyYzcxMTlmMmJiNjc0YmJkNDI3OTEifX19")
public class BlockBreakTrigger extends InteractionTrigger {

    public BlockBreakTrigger() {
    }

    public BlockBreakTrigger(Map<String, Object> map) {
        super(map);
        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        List<Action> onEntityActions = (List<Action>) map.get("onEntityActions");
        if (onEntityActions != null && !onEntityActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new CasterTarget(), onEntityActions));
        }

        List<Action> onBlockActions = (List<Action>) map.get("onBlockActions");
        if (onBlockActions != null && !onBlockActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new LocationTarget(), onBlockActions));
        }
    }

    @Override
    public String getName() {
        return "&cBlock Break &6&ltrigger";
    }

    @Override
    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(CasterTarget.class, LocationTarget.class, RayTraceTarget.class);
    }

    @Override
    public Collection<Class<? extends Source>> getPossibleSources() {
        return Lists.newArrayList(CasterSource.class, LocationSource.class, RayTraceSource.class);
    }

    @Override
    public Trigger clone() {
        return new BlockBreakTrigger();
    }
}
