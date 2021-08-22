package com.github.zamponimarco.cubescocktail.placeholder.bool;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lOn Ground Placeholder", description = "gui.placeholder.boolean.on-ground.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk1ZDM3OTkzZTU5NDA4MjY3ODQ3MmJmOWQ4NjgyMzQxM2MyNTBkNDMzMmEyYzdkOGM1MmRlNDk3NmIzNjIifX19")
public class OnGroundPlaceholder extends BooleanPlaceholder {

    public OnGroundPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public OnGroundPlaceholder(boolean target) {
        super(target);
    }

    public OnGroundPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public BooleanPlaceholder clone() {
        return new OnGroundPlaceholder(target);
    }

    @Override
    public Boolean computePlaceholder(ActionTarget target, ActionSource source) {
        if (this.target) {
            if (target instanceof EntityTarget) {
                return ((EntityTarget) target).getTarget().isOnGround();
            }
        }
        return source.getCaster().isOnGround();
    }

    @Override
    public String getName() {
        return String.format("%s Is On Ground", target ? "Target" : "Source");
    }
}
