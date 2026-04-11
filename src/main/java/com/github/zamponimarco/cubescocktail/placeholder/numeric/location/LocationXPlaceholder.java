package com.github.zamponimarco.cubescocktail.placeholder.numeric.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lLocation X Placeholder", description = "gui.placeholder.double.location.x.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzkxZDZlZGE4M2VkMmMyNGRjZGNjYjFlMzNkZjM2OTRlZWUzOTdhNTcwMTIyNTViZmM1NmEzYzI0NGJjYzQ3NCJ9fX0")
@Getter
@Setter
public class LocationXPlaceholder extends LocationPlaceholder {

    public LocationXPlaceholder(boolean target) {
        super(target);
    }

    public LocationXPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public LocationXPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public NumericPlaceholder clone() {
        return new LocationXPlaceholder(target);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        Location location = getLocation(target, source);
        if (location != null) {
            return location.getX();
        }
        return 0.0;
    }

    @Override
    public String getName() {
        return String.format("%s X", target ? "Target" : "Source");
    }
}
