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
@Enumerable.Displayable(name = "&c&lArmor Placeholder", description = "gui.placeholder.double.entity.armor.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJkMzFhZDFlOTNmMTY2Zjk3ODU5Y2Q5ZjJiODBkYTUyYTgyOTQ0MDA5N2Y1ZDY3YThjMjEwZDEyMmI5ZDVlNSJ9fX0=")
@Getter
@Setter
public class ArmorPlaceholder extends AttributeNumericPlaceholder {

    public ArmorPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public ArmorPlaceholder(boolean target) {
        super(target);
    }

    public ArmorPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);
        if (entity == null) {
            return 0.0;
        }

        return getAttributeValue(entity, Attribute.GENERIC_ARMOR);
    }

    @Override
    public String getName() {
        return String.format("%s Armor", target ? "Target" : "Source");
    }

    @Override
    public NumericPlaceholder clone() {
        return new ArmorPlaceholder(target);
    }
}
