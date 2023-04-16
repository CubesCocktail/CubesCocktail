package com.github.zamponimarco.cubescocktail.placeholder.vector.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.math.Vector;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lVelocity Placeholder", description = "gui.placeholder.vector.entity.velocity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQyMzI5YTljNDEwNDA4NDE5N2JkNjg4NjE1ODUzOTg0ZDM3ZTE3YzJkZDIzZTNlNDEyZGQ0MmQ3OGI5OGViIn19fQ==")
@Getter
@Setter
public class VelocityPlaceholder extends EntityVectorPlaceholder {

    public VelocityPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public VelocityPlaceholder(boolean target) {
        super(target);
    }

    public VelocityPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Vector computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);

        if (entity == null) {
            return new Vector();
        }
        return new Vector(entity.getVelocity().clone());
    }

    @Override
    public String getName() {
        return String.format("%s Velocity", target ? "Target" : "Source");
    }

    @Override
    public VectorPlaceholder clone() {
        return new VelocityPlaceholder(target);
    }
}
