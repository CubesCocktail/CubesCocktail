package com.github.zamponimarco.cubescocktail.placeholder.numeric.vector;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.DirectionPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lVector Y Placeholder", description = "gui.placeholder.double.vector.y.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODlmZjhjNzQ0OTUwNzI5ZjU4Y2I0ZTY2ZGM2OGVhZjYyZDAxMDZmOGE1MzE1MjkxMzNiZWQxZDU1ZTMifX19")
public class VectorYPlaceholder extends NumericVectorPlaceholder {

    public VectorYPlaceholder() {
        this(TARGET_DEFAULT, new DirectionPlaceholder());
    }

    public VectorYPlaceholder(boolean target, VectorPlaceholder placeholder) {
        super(target, placeholder);
    }

    public VectorYPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        return placeholder.computePlaceholder(target, source).getY().getRealValue(target, source);
    }

    @Override
    public String getName() {
        return placeholder.getName() + " Y";
    }

    @Override
    public NumericPlaceholder clone() {
        return new VectorYPlaceholder(target, placeholder.clone());
    }
}