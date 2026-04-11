package com.github.zamponimarco.cubescocktail.condition.numeric;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.GUINameable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@GUINameable(GUIName = "getName")
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lEquals Condition", description = "gui.condition.equals.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAzMDgyZjAzM2Y5NzI0Y2IyMmZlMjdkMGRlNDk3NTA5MDMzNTY0MWVlZTVkOGQ5MjdhZGY1YThiNjdmIn19fQ==")
@Getter
@Setter
public class EqualsCondition extends NumericCondition {


    @Serializable(headTexture = Textures.ONE_HEAD, description = "gui.condition.operand-one", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue operandOne;
    @Serializable(headTexture = Textures.TWO_HEAD, description = "gui.condition.operand-two", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue operandTwo;

    public EqualsCondition() {
        this(NEGATE_DEFAULT, new NumericValue(), new NumericValue());
    }

    public EqualsCondition(boolean negate, NumericValue operandOne, NumericValue operandTwo) {
        super(negate);
        this.operandOne = operandOne;
        this.operandTwo = operandTwo;
    }

    public EqualsCondition(Map<String, Object> map) {
        super(map);
        this.operandOne = (NumericValue) map.get("operandOne");
        this.operandTwo = (NumericValue) map.get("operandTwo");
    }

    @Override
    public boolean testCondition(ActionTarget target, ActionSource source) {
        double operandOne = this.operandOne.getRealValue(target, source);
        double operandTwo = this.operandTwo.getRealValue(target, source);

        return Math.abs(operandOne - operandTwo) <= 0.000001;
    }

    @Override
    public String getName() {
        return String.format("&c" + operandOne.getName() + "&6&l%s&c" + operandTwo.getName(), negate ? " ≠ " : " = ");
    }

    @Override
    public Condition clone() {
        return new EqualsCondition(negate, operandOne.clone(), operandTwo.clone());
    }
}
