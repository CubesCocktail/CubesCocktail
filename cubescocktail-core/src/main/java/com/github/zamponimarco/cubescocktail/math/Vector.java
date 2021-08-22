package com.github.zamponimarco.cubescocktail.math;

import com.github.jummes.libs.annotation.GUINameable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@GUINameable(GUIName = "toString")
@Getter
@Setter
public class Vector implements Model, Cloneable {

    private static final NumericValue DEFAULT = new NumericValue(0);

    private static final String X_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzkxZDZlZGE4M2VkMmMyNGRjZGNjYjFlMzNkZjM2OTRlZWUzOTdhNTcwMTIyNTViZmM1NmEzYzI0NGJjYzQ3NCJ9fX0=";
    private static final String Y_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODlmZjhjNzQ0OTUwNzI5ZjU4Y2I0ZTY2ZGM2OGVhZjYyZDAxMDZmOGE1MzE1MjkxMzNiZWQxZDU1ZTMifX19=";
    private static final String Z_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA1ZjE4ZDQxNmY2OGU5YmQxOWQ1NWRmOWZhNzQyZWRmYmYxYTUyNWM4ZTI5ZjY1OWFlODMzYWYyMTdkNTM1In19fQ===";

    @Serializable(headTexture = Y_HEAD, description = "gui.vector.y",
            additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Optional(defaultValue = "DEFAULT")
    private NumericValue y;
    @Serializable(headTexture = X_HEAD, description = "gui.vector.x",
            additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Optional(defaultValue = "DEFAULT")
    private NumericValue x;
    @Serializable(headTexture = Z_HEAD, description = "gui.vector.z",
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
