package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
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
@Enumerable.Displayable(name = "&c&lOn entity crossbow load", description = "gui.trigger.combat.crossbow-load.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGFlZTZiYjM3Y2JmYzkyYjBkODZkYjVhZGE0NzkwYzY0ZmY0NDY4ZDY4Yjg0OTQyZmRlMDQ0MDVlOGVmNTMzMyJ9fX0=")
@Getter
@Setter
public class EntityCrossbowLoadTrigger extends CombatTrigger {

    public EntityCrossbowLoadTrigger() {

    }

    public EntityCrossbowLoadTrigger(Map<String, Object> map) {
        super(map);
    }

    @Override
    public String getName() {
        return "&cCrossbow load &6&ltrigger";
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
        return new EntityCrossbowLoadTrigger();
    }
}
