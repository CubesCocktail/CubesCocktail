package com.github.zamponimarco.cubescocktail.placeholder.numeric.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.Location;

import java.util.Map;

@Enumerable.Displayable(name = "&c&lNumeric Location Placeholders", description = "gui.placeholder.double.location.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUyY2M0MjAxNWU2Njc4ZjhmZDQ5Y2NjMDFmYmY3ODdmMWJhMmMzMmJjZjU1OWEwMTUzMzJmYzVkYjUwIn19fQ")
@Enumerable.Parent(classArray = {LocationYPlaceholder.class, LocationXPlaceholder.class, LocationZPlaceholder.class,
        LocationYawPlaceholder.class, LocationPitchPlaceholder.class})
public abstract class LocationPlaceholder extends NumericPlaceholder {

    public LocationPlaceholder(boolean target) {
        super(target);
    }

    public LocationPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public Location getLocation(ActionTarget target, ActionSource source) {
        if (this.target) {
            return target.getLocation();
        }
        return source.getLocation();
    }
}
