package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lEye Height Placeholder", description = "gui.placeholder.double.entity.eye-height.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg0OTI5NjgxNzFiOWZhYjk4Njg0MDU1MDNjNTc2YzQyNzIzNzQ0YTMxYmYxZTFkMGUzOWJkYTRkN2ZiMCJ9fX0=")
public class EyeHeightPlaceholder extends EntityNumericPlaceholder {

    public EyeHeightPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public EyeHeightPlaceholder(boolean target) {
        super(target);
    }

    public EyeHeightPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);
        if (entity == null) {
            return 0.0;
        }

        return entity.getEyeHeight();
    }

    @Override
    public String getName() {
        return String.format("%s Eye Height", target ? "Target" : "Source");
    }

    @Override
    public NumericPlaceholder clone() {
        return new EyeHeightPlaceholder(target);
    }
}
