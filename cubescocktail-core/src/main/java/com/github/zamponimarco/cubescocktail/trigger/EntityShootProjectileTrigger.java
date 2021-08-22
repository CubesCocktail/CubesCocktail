package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.RayTraceSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.target.CasterTarget;
import com.github.zamponimarco.cubescocktail.target.RayTraceTarget;
import com.github.zamponimarco.cubescocktail.target.Target;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lOn entity shoot bow", description = "gui.trigger.combat.projectile-shoot.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU0Nzk1MTA0MjJiMWM1ZGNjNzdmNzVmZGMzMzQ2ZWQ0ZDlkYmJjYzFlODg1YjRhMjk5MmEyNzM3MzM2NDZhOSJ9fX0=")
public class EntityShootProjectileTrigger extends CombatTrigger {

    public EntityShootProjectileTrigger() {

    }

    public EntityShootProjectileTrigger(Map<String, Object> map) {
        super(map);

        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        List<Action> onEntityActions = (List<Action>) map.get("onEntityActions");
        if (onEntityActions != null && !onEntityActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new CasterTarget(), onEntityActions));
        }
    }

    @Override
    public String getName() {
        return "&cShoot Projectile &6&ltrigger";
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
        return new EntityShootProjectileTrigger();
    }
}
