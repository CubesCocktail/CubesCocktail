package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.RayTraceSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.github.zamponimarco.cubescocktail.trgt.RayTraceTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Enumerable.Child
@Enumerable.Displayable(condition = "isCasterOnlyPlayer", name = "&c&lRight click trigger", description = "gui.trigger.interact.right-click.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0=")
public class RightClickTrigger extends InteractionTrigger {

    private static final boolean BLOCK_DEFAULT = false;

    private static final String BLOCK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU0ZDljNDg4YzNmYmRlNTQ1NGUzODYxOWY5Y2M1YjViYThjNmMwMTg2ZjhhYTFkYTYwOTAwZmNiYzNlYTYifX19";

    @Serializable(headTexture = BLOCK_HEAD, description = "gui.trigger.interact.block-activate")
    @Serializable.Optional(defaultValue = "BLOCK_DEFAULT")
    protected boolean activateOnBlock;

    public RightClickTrigger() {
        this(BLOCK_DEFAULT);
    }

    public RightClickTrigger(boolean activateOnBlock) {
        this.activateOnBlock = activateOnBlock;
    }

    public RightClickTrigger(Map<String, Object> map) {
        super(map);
        activateOnBlock = (boolean) map.getOrDefault("activateOnBlock", BLOCK_DEFAULT);

        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        List<Action> onCasterActions = (List<Action>) map.get("onCasterActions");
        if (onCasterActions != null && !onCasterActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new CasterTarget(), onCasterActions));
        }

        List<Action> onRayCastPointActions = (List<Action>) map.get("onRayCastPointActions");
        if (onRayCastPointActions != null && !onRayCastPointActions.isEmpty()) {
            double onRayCastMaxDistance;
            try {
                onRayCastMaxDistance = (double) map.getOrDefault("onRayCastMaxDistance", 30.0);
            } catch (ClassCastException e) {
                onRayCastMaxDistance = 30;
            }
            groups.add(new ActionGroup(new CasterSource(), new RayTraceTarget(onRayCastMaxDistance), onRayCastPointActions));
        }
    }

    @Override
    public String getName() {
        return "&cRight click &6&ltrigger";
    }

    @Override
    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(CasterTarget.class, RayTraceTarget.class);
    }

    @Override
    public Collection<Class<? extends Source>> getPossibleSources() {
        return Lists.newArrayList(CasterSource.class, RayTraceSource.class);
    }

    @Override
    public Trigger clone() {
        return new RightClickTrigger(activateOnBlock);
    }
}
