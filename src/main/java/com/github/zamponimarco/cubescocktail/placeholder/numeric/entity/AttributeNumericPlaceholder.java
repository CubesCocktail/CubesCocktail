package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public abstract class AttributeNumericPlaceholder extends EntityNumericPlaceholder {

    public AttributeNumericPlaceholder(boolean target) {
        super(target);
    }

    public AttributeNumericPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public double getAttributeValue(LivingEntity entity, Attribute attribute) {
        AttributeInstance attr = entity.getAttribute(attribute);
        if (attr == null) {
            return 0.0;
        }
        return attr.getValue();
    }
}
