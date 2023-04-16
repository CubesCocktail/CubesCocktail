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

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lNumber Division Placeholder", description = "gui.placeholder.double.operator.division.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmMDdhYWZlMWExZmJlNzgwNDgzNGU4ZDYxN2NiZGZjNjY0OTNhOTQxYzExZDg3ZmI3MTQxZTU3ODk3ZjgzIn19fQ==")
@Setter
@Getter
public class DivisionPlaceholder extends NumberOperatorPlaceholder {

    @Serializable(headTexture = ONE_HEAD, description = "gui.placeholder.double.operator.operand-one", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue operandOne;
    @Serializable(headTexture = TWO_HEAD, description = "gui.placeholder.double.operator.operand-two", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue operandTwo;

    public DivisionPlaceholder() {
        this(TARGET_DEFAULT, OPERAND_ONE_DEFAULT.clone(), OPERAND_TWO_DEFAULT.clone());
    }

    public DivisionPlaceholder(boolean target, NumericValue operandOne, NumericValue operandTwo) {
        super(target);
        this.operandOne = operandOne;
        this.operandTwo = operandTwo;
    }

    public DivisionPlaceholder(Map<String, Object> map) {
        super(map);
        this.operandOne = (NumericValue) map.getOrDefault("operandOne", OPERAND_ONE_DEFAULT.clone());
        this.operandTwo = (NumericValue) map.getOrDefault("operandTwo", OPERAND_TWO_DEFAULT.clone());
    }

    @Override
    public NumericPlaceholder clone() {
        return new DivisionPlaceholder(target, operandOne.clone(), operandTwo.clone());
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        return operandOne.getRealValue(target, source) /
                operandTwo.getRealValue(target, source);
    }

    @Override
    public String getName() {
        return operandOne.getName() + " &6&l/&c " + operandTwo.getName();
    }
}