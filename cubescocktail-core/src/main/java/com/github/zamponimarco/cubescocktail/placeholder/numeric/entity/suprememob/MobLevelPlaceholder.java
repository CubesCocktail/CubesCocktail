package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.suprememob;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lMob Level Placeholder", description = "gui.placeholder.double.mob.level", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjUwNTU2YTg0MTY0MDY5ZGYzYjg5NTkzOWQwYWI1MDhmZmE4ZTE0MDQ3MTA2OTM4YjU1OWY1ODg5ZTViZmJlNCJ9fX0=")
public class MobLevelPlaceholder extends MobNumericPlaceholder {

    public MobLevelPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public MobLevelPlaceholder(boolean target) {
        super(target);
    }

    public MobLevelPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        /*
        if (!SupremeMob.getInstance().isEnabled()) {
            return 0.0;
        }

        LivingEntity entity = getEntity(target, source);

        if (entity == null) {
            return 0.0;
        }

        return (double) Mob.getLevel(entity);
        */
        return 0.0;
    }

    @Override
    public String getName() {
        return "Mob level";
    }

    @Override
    public NumericPlaceholder clone() {
        return new MobLevelPlaceholder(target);
    }
}
