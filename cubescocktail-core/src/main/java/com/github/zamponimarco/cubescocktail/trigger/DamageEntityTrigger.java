package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.source.*;
import com.github.zamponimarco.cubescocktail.target.*;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lOn entity damage", description = "gui.trigger.combat.damage-entity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjUyNTU5ZjJiY2VhZDk4M2Y0YjY1NjFjMmI1ZjJiNTg4ZjBkNjExNmQ0NDY2NmNlZmYxMjAyMDc5ZDI3Y2E3NCJ9fX0=")
public class DamageEntityTrigger extends CombatTrigger {

    public DamageEntityTrigger() {

    }

    public DamageEntityTrigger(Map<String, Object> map) {
        super(map);

        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        List<Action> onDamagedActions = (List<Action>) map.get("onDamagedActions");
        if (onDamagedActions != null && !onDamagedActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new CasterTarget(), onDamagedActions));
        }

        List<Action> onDamagerActions = (List<Action>) map.get("onDamagerActions");
        if (onDamagerActions != null && !onDamagerActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new DamagerTarget(), onDamagerActions));
        }
    }

    @Override
    public String getName() {
        return "&cDamage Entity &6&ltrigger";
    }

    @Override
    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(CasterTarget.class, RayTraceTarget.class, DamagerTarget.class);
    }

    @Override
    public Collection<Class<? extends Source>> getPossibleSources() {
        return Lists.newArrayList(CasterSource.class, DamagerSource.class, RayTraceSource.class);
    }

    @Override
    public Trigger clone() {
        return new DamageEntityTrigger();
    }
}
