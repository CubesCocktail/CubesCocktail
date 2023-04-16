package com.github.zamponimarco.cubescocktail.placeholder.bool;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lField of view placeholder", description = "gui.placeholder.boolean.fov.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg0OTI5NjgxNzFiOWZhYjk4Njg0MDU1MDNjNTc2YzQyNzIzNzQ0YTMxYmYxZTFkMGUzOWJkYTRkN2ZiMCJ9fX0=")
@Getter
@Setter
public class InFieldOfViewPlaceholder extends BooleanPlaceholder {

    protected static final boolean TARGET_DEFAULT = false;
    private static final NumericValue FOV_DEFAULT = new NumericValue(90);

    private static final String FOV_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg0OTI5NjgxNzFiOWZhYjk4Njg0MDU1MDNjNTc2YzQyNzIzNzQ0YTMxYmYxZTFkMGUzOWJkYTRkN2ZiMCJ9fX0";

    @Serializable(headTexture = FOV_HEAD, description = "gui.placeholder.boolean.fov.fov")
    @Serializable.Number(minValue = 0, maxValue = 360, scale = 1)
    private NumericValue fov;

    public InFieldOfViewPlaceholder() {
        this(true, FOV_DEFAULT.clone());
    }

    public InFieldOfViewPlaceholder(boolean target, NumericValue fov) {
        super(target);
        this.fov = fov;
    }

    public InFieldOfViewPlaceholder(Map<String, Object> map) {
        super(map);
        this.fov = (NumericValue) map.getOrDefault("fov", FOV_DEFAULT.clone());
    }

    @Override
    public Boolean computePlaceholder(ActionTarget target, ActionSource source) {
        Location watcher = this.target ? source.getLocation() : target.getLocation();
        Location watchee = this.target ? target.getLocation() : source.getLocation();
        float fov = this.fov.getRealValue(target, source).floatValue();

        Vector minDirection = watcher.getDirection().clone().setY(0).rotateAroundY(-Math.toRadians(fov / 2)).normalize();
        Vector maxDirection = watcher.getDirection().clone().setY(0).rotateAroundY(Math.toRadians(fov / 2)).normalize();
        Vector toCheck = watchee.clone().subtract(watcher).toVector().setY(0).normalize();
        return cross(minDirection.getX(), minDirection.getZ(), toCheck.getX(), toCheck.getZ()) <= 0 &&
                cross(toCheck.getX(), toCheck.getZ(), maxDirection.getX(), maxDirection.getZ()) <= 0;
    }

    private double cross(double x1, double x2, double y1, double y2) {
        return x1 * y2 - x2 * y1;
    }

    @Override
    public String getName() {
        return String.format("%s in %s fov: %s", target ? "Target" : "Source", target ? "Source" : "Target", fov.getName());
    }

    @Override
    public BooleanPlaceholder clone() {
        return new InFieldOfViewPlaceholder(target, fov.clone());
    }
}
