package com.github.zamponimarco.cubescocktail.math;

import com.github.jummes.libs.annotation.GUINameable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@GUINameable(GUIName = "toString")
@Getter
@Setter
public class Vector implements Model, Cloneable {

    private static final NumericValue DEFAULT = new NumericValue(0);


    @Serializable(headTexture = Textures.Y_HEAD, description = "gui.vector.y",
            additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Optional(defaultValue = "DEFAULT")
    private NumericValue y;
    @Serializable(headTexture = Textures.X_HEAD, description = "gui.vector.x",
            additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Optional(defaultValue = "DEFAULT")
    private NumericValue x;
    @Serializable(headTexture = Textures.Z_HEAD, description = "gui.vector.z",
            additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Optional(defaultValue = "DEFAULT")
    private NumericValue z;

    public Vector() {
        this(DEFAULT.clone(), DEFAULT.clone(), DEFAULT.clone());
    }

    public Vector(org.bukkit.util.Vector vector) {
        this(new NumericValue(vector.getX()), new NumericValue(vector.getY()), new NumericValue(vector.getZ()));
    }

    public Vector(NumericValue x, NumericValue y, NumericValue z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(Map<String, Object> map) {
        this.x = (NumericValue) map.getOrDefault("x", DEFAULT.clone());
        this.y = (NumericValue) map.getOrDefault("y", DEFAULT.clone());
        this.z = (NumericValue) map.getOrDefault("z", DEFAULT.clone());
    }

    public org.bukkit.util.Vector computeVector(ActionTarget target, ActionSource source) {
        return new org.bukkit.util.Vector(x.getRealValue(target, source),
                y.getRealValue(target, source), z.getRealValue(target, source));
    }

    @Override
    public String toString() {
        return String.format("&c%s&6&l/&c%s&6&l/&c%s", x.getName(), y.getName(), z.getName());
    }

    public Vector clone() {
        return new Vector(x.clone(), y.clone(), z.clone());
    }
}
