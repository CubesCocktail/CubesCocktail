package com.github.zamponimarco.cubescocktail.placeholder.vector;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.math.Vector;
import com.github.zamponimarco.cubescocktail.placeholder.Placeholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.entity.EntityVectorPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.operator.VectorOperatorPlaceholder;

import java.util.Map;


@Enumerable.Displayable
@Enumerable.Parent(classArray = {DirectionPlaceholder.class, EntityVectorPlaceholder.class, VectorOperatorPlaceholder.class,
        VectorVariablePlaceholder.class, VectorFromLocationPlaceholder.class})
public abstract class VectorPlaceholder extends Placeholder<Vector> {

    public VectorPlaceholder(boolean target) {
        super(target);
    }

    public VectorPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public abstract VectorPlaceholder clone();
}
