package com.github.zamponimarco.cubescocktail.placeholder.bool;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.placeholder.Placeholder;

import java.util.Map;

@Enumerable.Parent(classArray = {OnGroundPlaceholder.class, PossessItemPlaceholder.class,
        PlayerPropertyBooleanPlaceholder.class, IsWeatherClearPlaceholder.class, InFieldOfViewPlaceholder.class,
        IsSourcePlaceholder.class})
public abstract class BooleanPlaceholder extends Placeholder<Boolean> {

    public BooleanPlaceholder(boolean target) {
        super(target);
    }

    public BooleanPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public abstract BooleanPlaceholder clone();
}
