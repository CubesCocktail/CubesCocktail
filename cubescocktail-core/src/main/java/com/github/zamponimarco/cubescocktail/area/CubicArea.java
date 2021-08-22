package com.github.zamponimarco.cubescocktail.area;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.github.zamponimarco.cubescocktail.value.VectorValue;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lCubic area", description = "gui.area.cubic.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU0ZDljNDg4YzNmYmRlNTQ1NGUzODYxOWY5Y2M1YjViYThjNmMwMTg2ZjhhYTFkYTYwOTAwZmNiYzNlYTYifX19")
public class CubicArea extends Area {

    private static final String RANGE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ2YjEyOTNkYjcyOWQwMTBmNTM0Y2UxMzYxYmJjNTVhZTVhOGM4ZjgzYTE5NDdhZmU3YTg2NzMyZWZjMiJ9fX0=";
    private static final String BORDERS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA5NWE3ZmQ5MGRhYTFiYmU3MDY5MDg5NzQwZTA1ZDBiZmM2NjI5NmVlM2M0MGVlNzFhNGUwYTY2MTZiMmJiYyJ9fX0=";

    @Serializable(headTexture = RANGE_HEAD, description = "gui.area.cubic.range",
            additionalDescription = {"gui.additional-tooltips.value"})
    private final NumericValue range;
    @Serializable(headTexture = BORDERS_HEAD, description = "gui.area.borders")
    private final boolean onlyBorders;

    public CubicArea() {
        this(DEFAULT_TRANSLATION.clone(), new NumericValue(3), false);
    }

    public CubicArea(VectorValue centerTranslation, NumericValue range, boolean onlyBorders) {
        super(centerTranslation);
        this.range = range;
        this.onlyBorders = onlyBorders;
    }

    public CubicArea(Map<String, Object> map) {
        super(map);
        this.range = (NumericValue) map.getOrDefault("range", new NumericValue(3));
        this.onlyBorders = (boolean) map.getOrDefault("onlyBorders", false);
    }

    @Override
    public List<Location> getBlocks(Location center, ActionTarget target, ActionSource source) {
        Location finalCenter = finalLocation(center, target, source);
        List<Location> blocks = new ArrayList<>();
        double radius = range.getRealValue(target, source) + 0.5;

        for (int x = 0; x <= radius; ++x) {
            for (int y = 0; y <= radius; ++y) {
                for (int z = 0; z <= radius; ++z) {

                    if (onlyBorders) {
                        if (x < radius - 1 && y < radius - 1 && z < radius - 1) {
                            continue;
                        }
                    }

                    blocks.add(finalCenter.clone().add(x, y, z));
                    blocks.add(finalCenter.clone().add(-x, y, z));
                    blocks.add(finalCenter.clone().add(x, -y, z));
                    blocks.add(finalCenter.clone().add(-x, -y, z));
                    blocks.add(finalCenter.clone().add(x, y, -z));
                    blocks.add(finalCenter.clone().add(-x, y, -z));
                    blocks.add(finalCenter.clone().add(x, -y, -z));
                    blocks.add(finalCenter.clone().add(-x, -y, -z));
                }
            }
        }

        return blocks;
    }

    @Override
    public Collection<LivingEntity> entitiesInside(Location center, ActionTarget target, ActionSource source) {
        Location l = finalLocation(center, target, source);
        return l.getWorld().getNearbyEntities(getMinimumBoundingBox(l, range.getRealValue(target, source))).
                stream().filter(entity -> entity instanceof LivingEntity).
                map(entity -> (LivingEntity) entity).collect(Collectors.toList());
    }

    private BoundingBox getMinimumBoundingBox(Location l, double range) {
        return new BoundingBox(l.getX() - range, l.getY() - range, l.getZ() - range,
                l.getX() + range, l.getY() + range, l.getZ() + range);
    }

    @Override
    public String getName() {
        return "&6&lCubic range:&c " + range.getName();
    }

    @Override
    public Area clone() {
        return new CubicArea(centerTranslation.clone(), range.clone(), onlyBorders);
    }
}
