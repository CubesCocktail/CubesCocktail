package com.github.zamponimarco.cubescocktail.placeholder.string;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.placeholder.Placeholder;
import com.github.zamponimarco.cubescocktail.placeholder.string.operator.StringOperatorPlaceholder;

import java.util.Map;

@Enumerable.Parent(classArray = {PlayerNamePlaceholder.class, WorldNamePlaceholder.class, ObjectToStringPlaceholder.class,
        StringOperatorPlaceholder.class, StringVariablePlaceholder.class, BiomePlaceholder.class, EntityTypePlaceholder.class})
public abstract class StringPlaceholder extends Placeholder<String> {
    public StringPlaceholder(boolean target) {
        super(target);
    }

    public StringPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public abstract StringPlaceholder clone();
}
