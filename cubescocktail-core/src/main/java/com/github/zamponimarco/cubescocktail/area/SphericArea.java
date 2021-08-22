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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lSpheric area", description = "gui.area.spheric.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjcyYmNkZjZhNTQ4OTc0YmY3YjExNWFjZWU2M2NiMjg0MzY3YTBmNzQ1YmMwZmYzNTdjMTQyMzE1MjQzZDhkOSJ9fX0")
public class SphericArea extends Area {

    private static final String RANGE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ2YjEyOTNkYjcyOWQwMTBmNTM0Y2UxMzYxYmJjNTVhZTVhOGM4ZjgzYTE5NDdhZmU3YTg2NzMyZWZjMiJ9fX0=";
    private static final String BORDERS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA5NWE3ZmQ5MGRhYTFiYmU3MDY5MDg5NzQwZTA1ZDBiZmM2NjI5NmVlM2M0MGVlNzFhNGUwYTY2MTZiMmJiYyJ9fX0=";

    @Serializable(headTexture = RANGE_HEAD, description = "gui.area.spheric.range", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue range;
    @Serializable(headTexture = BORDERS_HEAD, description = "gui.area.borders")
    private boolean onlyBorders;

    public SphericArea() {
        this(DEFAULT_TRANSLATION.clone(), new NumericValue(3.0), false);
    }

    public SphericArea(VectorValue centerTranslation, NumericValue range, boolean onlyBorders) {
        super(centerTranslation);
        this.range = range;
        this.onlyBorders = onlyBorders;
    }

    public SphericArea(Map<String, Object> map) {
        super(map);
        this.range = (NumericValue) map.get("range");
        this.onlyBorders = (boolean) map.getOrDefault("onlyBorders", false);
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
        Location finalCenter = finalLocation(center, target, source);
        List<Location> blocks = new ArrayList<>();
        double radius = range.getRealValue(target, source) + 0.5;

        final double invRadius = 1 / radius;

        double nextXn = 0;
        forX:
        for (int x = 0; x <= radius; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadius;
            double nextYn = 0;
            forY:
            for (int y = 0; y <= radius; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadius;
                double nextZn = 0;
                for (int z = 0; z <= radius; ++z) {
                    final double zn = nextZn;
                    nextZn = (z + 1) * invRadius;

                    double distanceSq = lengthSq(xn, yn, zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break;
                    }

                    if (onlyBorders) {
                        if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
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
        double radius = range.getRealValue(target, source);
        Location l = finalLocation(center, target, source);
        return l.getWorld().getNearbyEntities(getMinimumBoundingBox(radius, l)).stream().filter(entity ->
                (entity instanceof LivingEntity) && entity.getLocation().distance(l) <= radius).
                map(entity -> (LivingEntity) entity).collect(Collectors.toList());
    }

    @NotNull
    private BoundingBox getMinimumBoundingBox(double radius, Location l) {
        return new BoundingBox(l.getX() - radius, l.getY() - radius, l.getZ() - radius,
                l.getX() + radius, l.getY() + radius, l.getZ() + radius);
    }

    @Override
    public String getName() {
        return "&6&lSphere range: " + range.getName();
    }

    @Override
    public Area clone() {
        return new SphericArea(centerTranslation.clone(), range.clone(), onlyBorders);
    }
}
