package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lEntity width Placeholder", description = "gui.placeholder.double.entity.width.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ2YjEyOTNkYjcyOWQwMTBmNTM0Y2UxMzYxYmJjNTVhZTVhOGM4ZjgzYTE5NDdhZmU3YTg2NzMyZWZjMiJ9fX0=")
public class WidthPlaceholder extends AttributeNumericPlaceholder {

    public WidthPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public WidthPlaceholder(boolean target) {
        super(target);
    }

    public WidthPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);
        if (entity == null) {
            return 0.0;
        }

        return entity.getWidth();
    }

    @Override
    public String getName() {
        return String.format("%s Width", target ? "Target" : "Source");
    }

    @Override
    public NumericPlaceholder clone() {
        return new WidthPlaceholder(target);
    }
}
