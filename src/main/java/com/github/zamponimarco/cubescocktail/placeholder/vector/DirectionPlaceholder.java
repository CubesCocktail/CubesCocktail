package com.github.zamponimarco.cubescocktail.placeholder.vector;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.math.Vector;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lDirection Placeholder", description = "gui.placeholder.vector.direction.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg0OTI5NjgxNzFiOWZhYjk4Njg0MDU1MDNjNTc2YzQyNzIzNzQ0YTMxYmYxZTFkMGUzOWJkYTRkN2ZiMCJ9fX0=")
@Getter
@Setter
public class DirectionPlaceholder extends VectorPlaceholder {

    public DirectionPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public DirectionPlaceholder(boolean target) {
        super(target);
    }

    public DirectionPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Vector computePlaceholder(ActionTarget target, ActionSource source) {
        Location location;
        if (this.target) {
            location = target.getLocation();
        } else {
            location = source.getLocation();
        }
        if (location == null) {
            return new Vector();
        }
        return new Vector(location.getDirection().clone());
    }

    @Override
    public String getName() {
        return String.format("%s Direction", target ? "Target" : "Source");
    }

    @Override
    public VectorPlaceholder clone() {
        return new DirectionPlaceholder(target);
    }
}
