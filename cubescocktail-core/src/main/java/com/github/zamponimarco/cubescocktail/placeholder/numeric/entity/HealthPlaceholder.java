package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lHealth Placeholder", description = "gui.placeholder.double.entity.health.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZmZGQ0YjEzZDU0ZjZjOTFkZDVmYTc2NWVjOTNkZDk0NThiMTlmOGFhMzRlZWI1YzgwZjQ1NWIxMTlmMjc4In19fQ==")
public class HealthPlaceholder extends EntityNumericPlaceholder {

    public HealthPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public HealthPlaceholder(boolean target) {
        super(target);
    }

    public HealthPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public NumericPlaceholder clone() {
        return new MaxHealthPlaceholder(target);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);
        if (entity == null) {
            return 0.0;
        }

        return entity.getHealth();
    }

    @Override
    public String getName() {
        return String.format("%s Health", target ? "Target" : "Source");
    }

}
