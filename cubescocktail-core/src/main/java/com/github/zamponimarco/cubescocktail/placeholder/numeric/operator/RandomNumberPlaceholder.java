package com.github.zamponimarco.cubescocktail.placeholder.numeric.operator;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lRandom Number Placeholder", description = "gui.placeholder.double.operator.random.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTBkMmEzY2U0OTk5ZmVkMzMwZDNhNWQwYTllMjE4ZTM3ZjRmNTc3MTk4MDg2NTczOTZkODMyMjM5ZTEyIn19fQ==")
public class RandomNumberPlaceholder extends NumberOperatorPlaceholder {

    private static final NumericValue MIN_DEFAULT = new NumericValue(0);
    private static final NumericValue MAX_DEFAULT = new NumericValue(1);
    private static final boolean IS_NATURAL_DEFAULT = true;

    private static final String MIN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzkxMmQ0NWIxYzc4Y2MyMjQ1MjcyM2VlNjZiYTJkMTU3NzdjYzI4ODU2OGQ2YzFiNjJhNTQ1YjI5YzcxODcifX19";
    private static final String MAX_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTk5YWFmMjQ1NmE2MTIyZGU4ZjZiNjI2ODNmMmJjMmVlZDlhYmI4MWZkNWJlYTFiNGMyM2E1ODE1NmI2NjkifX19";
    private static final String NATURAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM0ZDc1Yjk5ZmFlYzVlMTJhZDJmOTJkYTYxYWM0OTI2MjZlNTA1YjZhODRmNmQ2OWJiZjI0OTllN2I4NDQyNyJ9fX0=";

    @Serializable(headTexture = NATURAL_HEAD, description = "gui.placeholder.double.operator.random.is-natural")
    private boolean isNatural;
    @Serializable(headTexture = MAX_HEAD, description = "gui.placeholder.double.operator.random.max", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue max;
    @Serializable(headTexture = MIN_HEAD, description = "gui.placeholder.double.operator.random.min", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue min;

    public RandomNumberPlaceholder() {
        this(TARGET_DEFAULT, MIN_DEFAULT.clone(), MAX_DEFAULT.clone(), IS_NATURAL_DEFAULT);
    }

    public RandomNumberPlaceholder(boolean target, NumericValue min, NumericValue max, boolean isNatural) {
        super(target);
        this.min = min;
        this.max = max;
        this.isNatural = isNatural;
    }

    public RandomNumberPlaceholder(Map<String, Object> map) {
        super(map);
        this.min = (NumericValue) map.getOrDefault("min", MIN_DEFAULT.clone());
        this.max = (NumericValue) map.getOrDefault("max", MAX_DEFAULT.clone());
        this.isNatural = (boolean) map.getOrDefault("isNatural", IS_NATURAL_DEFAULT);
    }

    @Override
    public NumericPlaceholder clone() {
        return new RandomNumberPlaceholder(target, min.clone(), max.clone(), isNatural);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        double minValue = this.min.getRealValue(target, source);
        double maxValue = this.max.getRealValue(target, source);
        double min = Math.min(minValue, maxValue);
        double max = Math.max(minValue, maxValue);
        double number = Math.random() * (max - min) + min;
        return isNatural ? Math.round(number) : number;
    }

    @Override
    public String getName() {
        return String.format("Random [%s, %s]", min.getName(), max.getName());
    }
}
