package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.RayTraceSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.github.zamponimarco.cubescocktail.trgt.RayTraceTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "isCasterOnlyPlayer", name = "&c&lOn Entity Sprint", description = "gui.trigger.movement.entity-sprint.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzJhN2RjYmY3ZWNhNmI2ZjYzODY1OTFkMjM3OTkxY2ExYjg4OGE0ZjBjNzUzZmY5YTMyMDJjZjBlOTIyMjllMyJ9fX0=")
@Getter
@Setter
public class EntitySprintTrigger extends MovementTrigger {

    private static final boolean ACTIVATE_DEFAULT = true;

    private static final String ACTIVATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE4OTc4Y2NiZjU3NmY0NDZlMjFjNTFkM2U4MGZjN2Y4NTY2ZWI3MjY1Y2M0M2M0YWQ3MWNmYjc4YzE2NTI1NyJ9fX0=";

    @Serializable(headTexture = ACTIVATION_HEAD, description = "gui.trigger.activate")
    private boolean onActivate;

    public EntitySprintTrigger() {
        this(ACTIVATE_DEFAULT);
    }

    public EntitySprintTrigger(boolean onActivate) {
        this.onActivate = onActivate;
    }

    public EntitySprintTrigger(Map<String, Object> map) {
        super(map);
        onActivate = (boolean) map.getOrDefault("onActivate", ACTIVATE_DEFAULT);
    }

    @Override
    public String getName() {
        return String.format("&cEntity %s Sprint &6&ltrigger", onActivate ? "Activate" : "Deactivate");
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
        return new EntitySprintTrigger(onActivate);
    }
}
