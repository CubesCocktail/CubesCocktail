package com.github.zamponimarco.cubescocktail.placeholder.numeric.vector;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.DirectionPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lVector X Placeholder", description = "gui.placeholder.double.vector.x.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzkxZDZlZGE4M2VkMmMyNGRjZGNjYjFlMzNkZjM2OTRlZWUzOTdhNTcwMTIyNTViZmM1NmEzYzI0NGJjYzQ3NCJ9fX0")
@Getter
@Setter
public class VectorXPlaceholder extends NumericVectorPlaceholder {

    public VectorXPlaceholder() {
        this(TARGET_DEFAULT, new DirectionPlaceholder());
    }

    public VectorXPlaceholder(boolean target, VectorPlaceholder placeholder) {
        super(target, placeholder);
    }

    public VectorXPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        return placeholder.computePlaceholder(target, source).getX().getRealValue(target, source);
    }

    @Override
    public String getName() {
        return placeholder.getName() + " X";
    }

    @Override
    public NumericPlaceholder clone() {
        return new VectorXPlaceholder(target, placeholder.clone());
    }
}
