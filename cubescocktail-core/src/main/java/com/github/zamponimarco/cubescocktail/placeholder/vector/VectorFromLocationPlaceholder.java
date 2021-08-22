package com.github.zamponimarco.cubescocktail.placeholder.vector;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.math.Vector;
import org.bukkit.Location;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lVector From Location Placeholder", description = "gui.placeholder.vector.location.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0=")
public class VectorFromLocationPlaceholder extends VectorPlaceholder {

    public VectorFromLocationPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public VectorFromLocationPlaceholder(boolean target) {
        super(target);
    }

    public VectorFromLocationPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Vector computePlaceholder(ActionTarget target, ActionSource source) {
        Location l;
        if (this.target) {
            l = target.getLocation();
        } else {
            l = source.getLocation();
        }
        return new Vector(l.toVector());
    }

    @Override
    public String getName() {
        return String.format("%s Location Vector", target ? "Target" : "Source");
    }

    @Override
    public VectorPlaceholder clone() {
        return new VectorFromLocationPlaceholder(target);
    }
}
