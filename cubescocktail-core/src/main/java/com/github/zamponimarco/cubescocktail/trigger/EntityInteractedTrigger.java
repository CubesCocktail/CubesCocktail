package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.InteractorSource;
import com.github.zamponimarco.cubescocktail.source.RayTraceSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.target.CasterTarget;
import com.github.zamponimarco.cubescocktail.target.InteractorTarget;
import com.github.zamponimarco.cubescocktail.target.RayTraceTarget;
import com.github.zamponimarco.cubescocktail.target.Target;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lEntity Interaction", description = "gui.trigger.movement.jump.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ3ZDFkZDRhN2RhZmYyYWFmMjhlNmExMmEwMWY0MmQ3ZTUxNTkzZWYzZGVhNzYyZWY4MTg0N2IxZDRjNTUzOCJ9fX0=")
public class EntityInteractedTrigger extends InteractionTrigger {

    public EntityInteractedTrigger() {

    }

    public EntityInteractedTrigger(Map<String, Object> map) {
        super(map);

        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        List<Action> onInteractorActions = (List<Action>) map.get("onInteractorActions");
        if (onInteractorActions != null && !onInteractorActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new InteractorTarget(), onInteractorActions));
        }

        List<Action> onEntityActions = (List<Action>) map.get("onEntityActions");
        if (onEntityActions != null && !onEntityActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new CasterTarget(), onEntityActions));
        }
    }

    @Override
    public String getName() {
        return "&cEntity Interaction &6&ltrigger";
    }

    @Override
    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(CasterTarget.class, RayTraceTarget.class, InteractorTarget.class);
    }

    @Override
    public Collection<Class<? extends Source>> getPossibleSources() {
        return Lists.newArrayList(CasterSource.class, RayTraceSource.class, InteractorSource.class);
    }

    @Override
    public Trigger clone() {
        return new EntityInteractedTrigger();
    }
}
