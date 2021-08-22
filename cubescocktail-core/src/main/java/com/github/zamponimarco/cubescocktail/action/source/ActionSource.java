package com.github.zamponimarco.cubescocktail.action.source;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface ActionSource {

    LivingEntity getCaster();

    Location getLocation();

    ItemStack getSourceItem();

    enum Type {
        ENTITY,
        LOCATION
    }
}
