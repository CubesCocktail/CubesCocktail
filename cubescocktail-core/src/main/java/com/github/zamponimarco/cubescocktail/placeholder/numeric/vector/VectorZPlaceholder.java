package com.github.zamponimarco.cubescocktail.placeholder.numeric.vector;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.DirectionPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lVector Z Placeholder", description = "gui.placeholder.double.vector.z.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA1ZjE4ZDQxNmY2OGU5YmQxOWQ1NWRmOWZhNzQyZWRmYmYxYTUyNWM4ZTI5ZjY1OWFlODMzYWYyMTdkNTM1In19fQ")
public class VectorZPlaceholder extends NumericVectorPlaceholder {

    public VectorZPlaceholder() {
        this(TARGET_DEFAULT, new DirectionPlaceholder());
    }

    public VectorZPlaceholder(boolean target, VectorPlaceholder placeholder) {
        super(target, placeholder);
    }

    public VectorZPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        return placeholder.computePlaceholder(target, source).getZ().getRealValue(target, source);
    }

    @Override
    public String getName() {
        return placeholder.getName() + " Z";
    }

    @Override
    public NumericPlaceholder clone() {
        return new VectorZPlaceholder(target, placeholder.clone());
    }
}