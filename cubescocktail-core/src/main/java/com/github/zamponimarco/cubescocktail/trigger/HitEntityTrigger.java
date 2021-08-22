package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.DamagedSource;
import com.github.zamponimarco.cubescocktail.source.RayTraceSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.*;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lOn entity hit", description = "gui.trigger.combat.hit-entity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBkZmM4YTM1NjNiZjk5NmY1YzFiNzRiMGIwMTViMmNjZWIyZDA0Zjk0YmJjZGFmYjIyOTlkOGE1OTc5ZmFjMSJ9fX0=")
public class HitEntityTrigger extends CombatTrigger {

    public HitEntityTrigger() {

    }

    public HitEntityTrigger(Map<String, Object> map) {
        super(map);

        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        List<Action> onDamagedActions = (List<Action>) map.get("onDamagedActions");
        if (onDamagedActions != null && !onDamagedActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new DamagedTarget(), onDamagedActions));
        }

        List<Action> onDamagerActions = (List<Action>) map.get("onDamagerActions");
        if (onDamagerActions != null && !onDamagerActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new DamagerTarget(), onDamagerActions));
        }
    }

    @Override
    public String getName() {
        return "&cHit entity &6&ltrigger";
    }

    @Override
    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(CasterTarget.class, RayTraceTarget.class, DamagedTarget.class);
    }

    @Override
    public Collection<Class<? extends Source>> getPossibleSources() {
        return Lists.newArrayList(CasterSource.class, RayTraceSource.class, DamagedSource.class);
    }

    @Override
    public Trigger clone() {
        return new HitEntityTrigger();
    }
}
