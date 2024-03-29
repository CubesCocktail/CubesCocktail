package com.github.zamponimarco.cubescocktail.source;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "sourceEnabled", name = "&c&lLocation Source", description = "gui.source.location.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhhMDY1NTg4YzdkYmYxMjk0NTk1YWJhNzc3OTFjM2FkNjVmMTliZjFjOWFkN2E1YzIzZGE0MGI4Mjg2MGI3In19fQ==")
@Getter
@Setter
public class LocationSource extends Source {

    public LocationSource() {
    }

    public LocationSource(Map<String, Object> map) {
        super(map);
    }

    public static boolean sourceEnabled(ModelPath<?> path) {
        return getPossibleSources(path).contains(LocationSource.class);
    }

    @Override
    public ActionSource getSource(ActionArgument args) {
        Location location = args.getArgument(ActionArgumentKey.LOCATION);
        LivingEntity entity = args.getArgument(ActionArgumentKey.CASTER);
        ItemStack item = args.getArgument(ActionArgumentKey.ITEM);
        return new com.github.zamponimarco.cubescocktail.action.source.LocationSource(location, entity, item);
    }

    @Override
    public Source clone() {
        return new LocationSource();
    }

    @Override
    public String getName() {
        return "Location Source";
    }
}
