package com.github.zamponimarco.cubescocktail.area;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.VectorValue;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lUnion Area", description = "gui.area.union.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNhYzM0MTg1YmNlZTYyNmRkOWJjOTY2YmU2NDk4NDM4ZmJmYTc1NDFjODYyYWM3MTZmZmVmOWZkMTg1In19fQ==")
public class UnionArea extends Area {

    @Serializable(headTexture = SHAPE_HEAD, description = "gui.area.areas", additionalDescription = {"gui.additional-tooltips.value"})
    private List<Area> areas;

    public UnionArea(VectorValue centerTranslation, List<Area> areas) {
        super(centerTranslation);
        this.areas = areas;
    }

    public UnionArea() {
        this(DEFAULT_TRANSLATION.clone(), Lists.newArrayList());
    }

    public UnionArea(Map<String, Object> map) {
        super(map);
        this.areas = (List<Area>) map.getOrDefault("areas", Lists.newArrayList());
    }

    @Override
    public List<Location> getBlocks(Location center, ActionTarget target, ActionSource source) {
        Set<Location> set = new HashSet();
        areas.stream().map(area -> area.getBlocks(center, target, source)).forEach(set::addAll);
        return new ArrayList<>(set);
    }

    @Override
    public Collection<LivingEntity> entitiesInside(Location center, ActionTarget target, ActionSource source) {
        Set<LivingEntity> set = new HashSet();
        areas.stream().map(area -> area.entitiesInside(center, target, source)).forEach(set::addAll);
        return new ArrayList<>(set);
    }

    @Override
    public ItemStack getTranslationItem() {
        return null;
    }

    @Override
    public String getName() {
        return "&6&lUnion";
    }

    @Override
    public Area clone() {
        return new UnionArea(centerTranslation.clone(), areas.stream().map(Area::clone).
                collect(Collectors.toList()));
    }
}
