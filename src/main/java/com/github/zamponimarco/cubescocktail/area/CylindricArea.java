package com.github.zamponimarco.cubescocktail.area;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.github.zamponimarco.cubescocktail.value.VectorValue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lCylinder area", description = "gui.area.cylinder.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY1MWIzM2E3NzM0NDdkZTBmM2VlMTA3ZDdkNzg0ZjE4MzNjZTUxNGUzYjdhMDhjMmIzMWI3MWNhYzYwY2EifX19")
public class CylindricArea extends Area {

    private static final String RANGE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ2YjEyOTNkYjcyOWQwMTBmNTM0Y2UxMzYxYmJjNTVhZTVhOGM4ZjgzYTE5NDdhZmU3YTg2NzMyZWZjMiJ9fX0=";
    @Serializable(headTexture = RANGE_HEAD, description = "gui.area.cylinder.height", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue height;
    @Serializable(headTexture = RANGE_HEAD, description = "gui.area.cylinder.range", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue range;
    @Serializable(headTexture = RANGE_HEAD, description = "gui.area.borders")
    private boolean onlyBorders;

    public CylindricArea() {
        this(DEFAULT_TRANSLATION.clone(), new NumericValue(3), new NumericValue(3), false);
    }

    public CylindricArea(VectorValue centerTranslation, NumericValue height, NumericValue range, boolean onlyBorders) {
        super(centerTranslation);
        this.height = height;
        this.range = range;
        this.onlyBorders = onlyBorders;
    }

    public CylindricArea(Map<String, Object> map) {
        super(map);
        this.height = (NumericValue) map.get("height");
        this.range = (NumericValue) map.get("range");
        this.onlyBorders = (boolean) map.get("onlyBorders");
    }

    /**
     * Method implementation provided by WorldEdit library
     * <p>
     * WorldEdit, a Minecraft world manipulation toolkit
     * Copyright (C) sk89q <http://www.sk89q.com>
     * Copyright (C) WorldEdit team and contributors
     */
    @Override
    public List<Location> getBlocks(Location center, ActionTarget target, ActionSource source) {
        Location finalLocation = finalLocation(center, target, source);
        List<Location> blocks = new ArrayList<>();
        double radius = range.getRealValue(target, source);
        int height = this.height.getRealValue(target, source).intValue();

        radius += 0.5;


        if (height == 0) {
            return blocks;
        } else if (height < 0) {
            height = -height;
            finalLocation.subtract(0, height, 0);
        }

        if (finalLocation.getBlockY() < 0) {
            finalLocation.setY(0);
        } else if (finalLocation.getBlockY() + height - 1 > 255) {
            height = 255 - finalLocation.getBlockY() + 1;
        }

        final double invRadius = 1 / radius;

        final int ceilRadius = (int) Math.ceil(radius);

        double nextXn = 0;
        forX:
        for (int x = 0; x <= ceilRadius; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadius;
            double nextZn = 0;
            for (int z = 0; z <= ceilRadius; ++z) {
                final double zn = nextZn;
                nextZn = (z + 1) * invRadius;

                double distanceSq = lengthSq(xn, zn);
                if (distanceSq > 1) {
                    if (z == 0) {
                        break forX;
                    }
                    break;
                }

                if (onlyBorders) {
                    if (lengthSq(nextXn, zn) <= 1 && lengthSq(xn, nextZn) <= 1) {
                        continue;
                    }
                }

                for (int y = 0; y < height; ++y) {
                    blocks.add(finalLocation.clone().add(x, y, z));
                    blocks.add(finalLocation.clone().add(-x, y, z));
                    blocks.add(finalLocation.clone().add(x, y, -z));
                    blocks.add(finalLocation.clone().add(-x, y, -z));
                }
            }
        }
        return blocks;
    }

    @Override
    public Collection<LivingEntity> entitiesInside(Location center, ActionTarget target, ActionSource source) {
        double height = this.height.getRealValue(target, source);
        double range = this.range.getRealValue(target, source);
        Location l = finalLocation(center, target, source);
        return l.getWorld().getNearbyEntities(getMinimumBoundingBox(height, range, l)).stream().filter(entity -> {
            Vector diff = entity.getLocation().clone().subtract(l).toVector();
            diff.setY(0);
            return (entity instanceof LivingEntity) && diff.length() < range;
        }).map(entity -> (LivingEntity) entity).collect(Collectors.toList());
    }

    @NotNull
    private BoundingBox getMinimumBoundingBox(double height, double range, Location l) {
        return new BoundingBox(l.getX() - range, l.getY() - height, l.getZ() - range,
                l.getX() + range, l.getY() + height, l.getZ() + range);
    }

    @Override
    public String getName() {
        return "&6&lCylinder range: &c" + range.getName() + "&6&l, height: &c" + height.getName();
    }

    @Override
    public Area clone() {
        return new CylindricArea(centerTranslation.clone(), height.clone(), range.clone(), onlyBorders);
    }
}
