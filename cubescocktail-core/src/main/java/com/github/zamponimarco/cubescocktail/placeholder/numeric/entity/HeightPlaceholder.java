package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lHeight Placeholder", description = "gui.placeholder.double.entity.height.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTc2OWUyYzEzNGVlNWZjNmRhZWZlNDEyZTRhZjNkNTdkZjlkYmIzY2FhY2Q4ZTM2ZTU5OTk3OWVjMWFjNCJ9fX0=")
@Getter
@Setter
public class HeightPlaceholder extends EntityNumericPlaceholder {

    public HeightPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public HeightPlaceholder(boolean target) {
        super(target);
    }

    public HeightPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);
        if (entity == null) {
            return 0.0;
        }

        return entity.getHeight();
    }

    @Override
    public String getName() {
        return String.format("%s Height", target ? "Target" : "Source");
    }

    @Override
    public NumericPlaceholder clone() {
        return new HeightPlaceholder(target);
    }
}
