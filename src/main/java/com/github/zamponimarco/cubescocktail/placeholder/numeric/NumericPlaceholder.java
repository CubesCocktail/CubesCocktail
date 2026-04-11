package com.github.zamponimarco.cubescocktail.placeholder.numeric;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.placeholder.Placeholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.block.NumericBlockPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.EntityNumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.item.ItemNumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.location.LocationPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.operator.NumberOperatorPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.projectile.ProjectileNumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.vector.NumericVectorPlaceholder;

import java.util.Map;

@Enumerable.Parent(classArray = {EntityNumericPlaceholder.class, NumberOperatorPlaceholder.class, LocationPlaceholder.class,
        NumericBlockPlaceholder.class, SavedNumericPlaceholder.class, ItemNumericPlaceholder.class,
        NumericVectorPlaceholder.class, ProjectileNumericPlaceholder.class})
public abstract class NumericPlaceholder extends Placeholder<Double> {

    public NumericPlaceholder(boolean target) {
        super(target);
    }

    public NumericPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public abstract NumericPlaceholder clone();

}
