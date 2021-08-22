package com.github.zamponimarco.cubescocktail.placeholder.vector.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Parent(classArray = {VelocityPlaceholder.class})
@Enumerable.Displayable(name = "&c&lEntity Vector Placeholders", description = "gui.placeholder.vector.entity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ")
public abstract class EntityVectorPlaceholder extends VectorPlaceholder {
    public EntityVectorPlaceholder(boolean target) {
        super(target);
    }

    public EntityVectorPlaceholder(Map<String, Object> map) {
        super(map);
    }

    protected LivingEntity getEntity(ActionTarget target, ActionSource source) {
        if (this.target) {
            if (target instanceof EntityTarget) {
                return ((EntityTarget) target).getTarget();
            }
            return null;
        }
        return source.getCaster();
    }
}
