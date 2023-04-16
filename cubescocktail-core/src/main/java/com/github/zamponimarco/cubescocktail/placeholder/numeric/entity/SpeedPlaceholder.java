package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lSpeed Placeholder", description = "gui.placeholder.double.entity.speed.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQyMzI5YTljNDEwNDA4NDE5N2JkNjg4NjE1ODUzOTg0ZDM3ZTE3YzJkZDIzZTNlNDEyZGQ0MmQ3OGI5OGViIn19fQ==")
@Getter
@Setter
public class SpeedPlaceholder extends AttributeNumericPlaceholder {

    public SpeedPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public SpeedPlaceholder(boolean target) {
        super(target);
    }

    public SpeedPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);
        if (entity == null) {
            return 0.0;
        }

        return getAttributeValue(entity, Attribute.GENERIC_MOVEMENT_SPEED);
    }

    @Override
    public String getName() {
        return String.format("%s Speed", target ? "Target" : "Source");
    }

    @Override
    public NumericPlaceholder clone() {
        return new SpeedPlaceholder(target);
    }
}
