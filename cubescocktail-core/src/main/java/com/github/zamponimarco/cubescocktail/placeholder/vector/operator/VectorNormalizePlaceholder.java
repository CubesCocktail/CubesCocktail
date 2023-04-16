package com.github.zamponimarco.cubescocktail.placeholder.vector.operator;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.math.Vector;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;
import com.github.zamponimarco.cubescocktail.value.VectorValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lNormalize Vector Placeholder", description = "gui.placeholder.vector.operator.normalize.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==")
@Getter
@Setter
public class VectorNormalizePlaceholder extends VectorOperatorPlaceholder {

    @Serializable(headTexture = ONE_HEAD, description = "gui.placeholder.vector.operator.operand-one",
            additionalDescription = {"gui.additional-tooltips.value"})
    private VectorValue operandOne;

    public VectorNormalizePlaceholder() {
        this(TARGET_DEFAULT, new VectorValue());
    }

    public VectorNormalizePlaceholder(boolean target, VectorValue operandOne) {
        super(target);
        this.operandOne = operandOne;
    }

    public VectorNormalizePlaceholder(Map<String, Object> map) {
        super(map);
        this.operandOne = (VectorValue) map.getOrDefault("operandOne", new Vector());
    }

    @Override
    public Vector computePlaceholder(ActionTarget target, ActionSource source) {
        try {
            return new Vector(operandOne.getRealValue(target, source).computeVector(target, source).normalize());
        } catch (Exception e) {
            return new Vector();
        }
    }

    @Override
    public String getName() {
        return operandOne.getName() + " &6&l Normalized&c ";
    }

    @Override
    public VectorPlaceholder clone() {
        return new VectorNormalizePlaceholder(TARGET_DEFAULT, operandOne.clone());
    }
}
