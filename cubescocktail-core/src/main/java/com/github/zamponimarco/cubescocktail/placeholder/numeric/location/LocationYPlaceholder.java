package com.github.zamponimarco.cubescocktail.placeholder.numeric.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.Location;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lLocation Y Placeholder", description = "gui.placeholder.double.location.y.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODlmZjhjNzQ0OTUwNzI5ZjU4Y2I0ZTY2ZGM2OGVhZjYyZDAxMDZmOGE1MzE1MjkxMzNiZWQxZDU1ZTMifX19")
public class LocationYPlaceholder extends LocationPlaceholder {

    public LocationYPlaceholder(boolean target) {
        super(target);
    }

    public LocationYPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public LocationYPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public NumericPlaceholder clone() {
        return new LocationYPlaceholder(target);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        Location location = getLocation(target, source);
        if (location != null) {
            return location.getY();
        }
        return 0.0;
    }

    @Override
    public String getName() {
        return String.format("%s Y", target ? "Target" : "Source");
    }
}
