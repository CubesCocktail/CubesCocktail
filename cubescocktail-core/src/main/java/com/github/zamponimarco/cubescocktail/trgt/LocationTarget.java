package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.Location;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lLocation Target", description = "gui.target.location.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhhMDY1NTg4YzdkYmYxMjk0NTk1YWJhNzc3OTFjM2FkNjVmMTliZjFjOWFkN2E1YzIzZGE0MGI4Mjg2MGI3In19fQ==")
public class LocationTarget extends Target {

    public LocationTarget() {
    }

    public LocationTarget(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ActionTarget getTarget(Map<String, Object> args) {
        Location location = (Location) args.get("location");
        return new com.github.zamponimarco.cubescocktail.action.targeter.LocationTarget(location);
    }

    public static boolean targetEnabled(ModelPath path) {
        return getPossibleTargets(path).contains(LocationTarget.class);
    }

    @Override
    public Target clone() {
        return new LocationTarget();
    }

    @Override
    public String getName() {
        return "Location Target";
    }
}
