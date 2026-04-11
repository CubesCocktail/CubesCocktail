package com.github.zamponimarco.cubescocktail.placeholder.numeric.vector;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.DirectionPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Displayable(name = "&c&lNumeric Vector Placeholders", description = "gui.placeholder.double.vector.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q0ZTZjMzkyZTE0ZjM0YjZhMzFlMzYzNWE4YmYxOGQ5NzNlZWY2ZGM5YjFhMzUxOTQ1MTQ5NzE5N2Q0YyJ9fX0=")
@Enumerable.Parent(classArray = {VectorYPlaceholder.class, VectorXPlaceholder.class, VectorZPlaceholder.class})
public abstract class NumericVectorPlaceholder extends NumericPlaceholder {

    private static final String PLACEHOLDER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q0ZTZjMzkyZTE0ZjM0YjZhMzFlMzYzNWE4YmYxOGQ5NzNlZWY2ZGM5YjFhMzUxOTQ1MTQ5NzE5N2Q0YyJ9fX0";

    @Serializable(headTexture = PLACEHOLDER_HEAD, description = "gui.placeholder.double.vector.placeholder")
    protected VectorPlaceholder placeholder;

    public NumericVectorPlaceholder(boolean target, VectorPlaceholder placeholder) {
        super(target);
        this.placeholder = placeholder;
    }

    public NumericVectorPlaceholder(Map<String, Object> map) {
        super(map);
        this.placeholder = (VectorPlaceholder) map.getOrDefault("placeholder", new DirectionPlaceholder());
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }
}
