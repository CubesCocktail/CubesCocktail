package com.github.zamponimarco.cubescocktail.condition.numeric;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.GUINameable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.value.NumericValue;

import java.util.Map;

@GUINameable(GUIName = "getName")
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lLess Than Condition", description = "gui.condition.less-than.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg2NzExOTgzODJkZTkzZTFkM2M3ODM0ZGU4NjcwNGE2ZWNjNzkxNDE5ZjBkZGI0OWE0MWE5NjA4YWQ0NzIifX19")
public class LessThanCondition extends Condition {

    private static final String ONE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBhMTllMjNkMjFmMmRiMDYzY2M1NWU5OWFlODc0ZGM4YjIzYmU3NzliZTM0ZTUyZTdjN2I5YTI1In19fQ==";
    private static final String TWO_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M1OTZhNDFkYWVhNTFiZTJlOWZlYzdkZTJkODkwNjhlMmZhNjFjOWQ1N2E4YmRkZTQ0YjU1OTM3YjYwMzcifX19";

    @Serializable(headTexture = ONE_HEAD, description = "gui.condition.operand-one", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue operandOne;
    @Serializable(headTexture = TWO_HEAD, description = "gui.condition.operand-two", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue operandTwo;

    public LessThanCondition() {
        this(NEGATE_DEFAULT, new NumericValue(), new NumericValue());
    }

    public LessThanCondition(boolean negate, NumericValue operandOne, NumericValue operandTwo) {
        super(negate);
        this.operandOne = operandOne;
        this.operandTwo = operandTwo;
    }

    public LessThanCondition(Map<String, Object> map) {
        super(map);
        this.operandOne = (NumericValue) map.get("operandOne");
        this.operandTwo = (NumericValue) map.get("operandTwo");
    }

    @Override
    public boolean testCondition(ActionTarget target, ActionSource source) {
        return operandOne.getRealValue(target, source) < operandTwo.getRealValue(target, source);
    }

    @Override
    public String getName() {
        return String.format("&c" + operandOne.getName() + "&6&l%s&c" + operandTwo.getName(), negate ? " ≥ " : " < ");
    }

    @Override
    public Condition clone() {
        return new LessThanCondition(negate, operandOne.clone(), operandTwo.clone());
    }
}
