package com.github.zamponimarco.cubescocktail.placeholder.numeric.operator;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.HealthPlaceholder;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Parent(classArray = {RandomNumberPlaceholder.class, SumPlaceholder.class, DifferencePlaceholder.class,
        MultiplicationPlaceholder.class, DivisionPlaceholder.class})
@Enumerable.Displayable(name = "&c&lNumeric Operator Placeholders", description = "gui.placeholder.double.operator.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzhlZTlmZDhjMGM2ZDI5Njc1YTNhZGZkOTRjNzIzZjZkMzA2YjJhNTk4NGU2NWRiNDY3N2JhNmFjNGY5MDIwIn19fQ==")
public abstract class NumberOperatorPlaceholder extends NumericPlaceholder {

    protected static final NumericValue OPERAND_ONE_DEFAULT = new NumericValue(new HealthPlaceholder());
    protected static final NumericValue OPERAND_TWO_DEFAULT = new NumericValue(10);

    protected static final String ONE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBhMTllMjNkMjFmMmRiMDYzY2M1NWU5OWFlODc0ZGM4YjIzYmU3NzliZTM0ZTUyZTdjN2I5YTI1In19fQ==";
    protected static final String TWO_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M1OTZhNDFkYWVhNTFiZTJlOWZlYzdkZTJkODkwNjhlMmZhNjFjOWQ1N2E4YmRkZTQ0YjU1OTM3YjYwMzcifX19";

    public NumberOperatorPlaceholder(boolean target) {
        super(target);
    }

    public NumberOperatorPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }
}
