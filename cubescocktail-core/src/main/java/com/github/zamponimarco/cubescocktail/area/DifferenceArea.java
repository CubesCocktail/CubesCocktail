package com.github.zamponimarco.cubescocktail.area;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.VectorValue;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lDifference Area", description = "gui.area.difference.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ2YjEyOTNkYjcyOWQwMTBmNTM0Y2UxMzYxYmJjNTVhZTVhOGM4ZjgzYTE5NDdhZmU3YTg2NzMyZWZjMiJ9fX0=")
@Getter
@Setter
public class DifferenceArea extends Area {

    @Serializable(headTexture = SHAPE_HEAD, description = "gui.area.areas", additionalDescription = {"gui.additional-tooltips.value"})
    private List<Area> areas;

    public DifferenceArea() {
        this(DEFAULT_TRANSLATION.clone(), Lists.newArrayList());
    }

    public DifferenceArea(VectorValue centerTranslation, List<Area> areas) {
        super(centerTranslation);
        this.areas = areas;
    }

    public DifferenceArea(Map<String, Object> map) {
        super(map);
        this.areas = (List<Area>) map.getOrDefault("areas", Lists.newArrayList());
    }

    @Override
    public List<Location> getBlocks(Location center, ActionTarget target, ActionSource source) {
        return areas.stream().map(area -> area.getBlocks(center, target, source)).
                reduce((blocks, blocks2) -> {
                    blocks.removeAll(blocks2);
                    return blocks;
                }).orElse(Lists.newArrayList());
    }

    @Override
    public Collection<LivingEntity> entitiesInside(Location center, ActionTarget target, ActionSource source) {
        return areas.stream().map(area -> area.entitiesInside(center, target, source)).
                reduce((livingEntities, livingEntities2) -> {
                    livingEntities.removeAll(livingEntities2);
                    return livingEntities;
                }).orElse(Lists.newArrayList());
    }

    @Override
    public ItemStack getTranslationItem() {
        return null;
    }

    @Override
    public String getName() {
        return "&6&lDifference";
    }

    @Override
    public Area clone() {
        return new DifferenceArea(centerTranslation.clone(), areas.stream().map(Area::clone).
                collect(Collectors.toList()));
    }
}
