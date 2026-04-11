package com.github.zamponimarco.cubescocktail.placeholder.vector.operator;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.github.zamponimarco.cubescocktail.math.Vector;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.github.zamponimarco.cubescocktail.value.VectorValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lVector Rotate Placeholder", description = "gui.placeholder.vector.operator.rotate.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTFkNzIwY2QzOWRmM2JlNzRiMGNhYzc1ZTM5MzdmMDA4NWEzNzgyNDc0M2NhZDYzMzBkYzlmNDY2NmE0NTEwZCJ9fX0=")
@Getter
@Setter
public class VectorRotatePlaceholder extends VectorOperatorPlaceholder {


    @Serializable(headTexture = Textures.VECTOR_HEAD_AACACB83, description = "gui.placeholder.vector.operator.rotate.vector",
            additionalDescription = {"gui.additional-tooltips.value"})
    private VectorValue toRotate;
    @Serializable(headTexture = Textures.AXIS_HEAD, description = "gui.placeholder.vector.operator.rotate.axis",
            additionalDescription = {"gui.additional-tooltips.value"})
    private VectorValue axis;

    @Serializable(headTexture = Textures.ANGLE_HEAD, description = "gui.placeholder.vector.operator.rotate.angle",
            additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue angle;

    public VectorRotatePlaceholder() {
        this(TARGET_DEFAULT, new VectorValue(), new VectorValue(), new NumericValue());
    }

    public VectorRotatePlaceholder(boolean target, VectorValue toRotate, VectorValue axis, NumericValue angle) {
        super(target);
        this.toRotate = toRotate;
        this.axis = axis;
        this.angle = angle;
    }

    public VectorRotatePlaceholder(Map<String, Object> map) {
        super(map);
        this.toRotate = (VectorValue) map.getOrDefault("toRotate", new VectorValue());
        this.axis = (VectorValue) map.getOrDefault("axis", new VectorValue());
        this.angle = (NumericValue) map.getOrDefault("angle", new NumericValue());
    }

    @Override
    public VectorPlaceholder clone() {
        return new VectorRotatePlaceholder(TARGET_DEFAULT, toRotate.clone(), axis.clone(), angle.clone());
    }

    @Override
    public Vector computePlaceholder(ActionTarget target, ActionSource source) {
        Vector one = toRotate.getRealValue(target, source);
        Vector two = axis.getRealValue(target, source);
        try {
            return new Vector(one.computeVector(target, source).rotateAroundAxis(two.computeVector(target, source),
                    angle.getRealValue(target, source)));
        } catch (Exception e) {
            return new Vector();
        }
    }

    @Override
    public String getName() {
        return String.format("Rotate %s around %s for %s degrees", toRotate.getName(), axis.getName(), angle.getName());
    }

}
