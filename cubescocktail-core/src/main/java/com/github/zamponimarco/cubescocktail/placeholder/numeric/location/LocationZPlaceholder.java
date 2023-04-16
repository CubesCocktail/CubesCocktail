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
@Enumerable.Displayable(name = "&c&lLocation Z Placeholder", description = "gui.placeholder.double.location.z.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA1ZjE4ZDQxNmY2OGU5YmQxOWQ1NWRmOWZhNzQyZWRmYmYxYTUyNWM4ZTI5ZjY1OWFlODMzYWYyMTdkNTM1In19fQ")
@Getter
@Setter
public class LocationZPlaceholder extends LocationPlaceholder {

    public LocationZPlaceholder(boolean target) {
        super(target);
    }

    public LocationZPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public LocationZPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public NumericPlaceholder clone() {
        return new LocationZPlaceholder(target);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        Location location = getLocation(target, source);
        if (location != null) {
            return location.getZ();
        }
        return 0.0;
    }

    @Override
    public String getName() {
        return String.format("%s Z", target ? "Target" : "Source");
    }
}
