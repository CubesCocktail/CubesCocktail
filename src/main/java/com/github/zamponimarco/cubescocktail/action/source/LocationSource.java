package com.github.zamponimarco.cubescocktail.action.source;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class LocationSource implements ActionSource {
    private final @NonNull Location source;
    private final @NonNull LivingEntity originalCaster;
    private final ItemStack sourceItem;

    @Override
    public LivingEntity getCaster() {
        return originalCaster;
    }

    @Override
    public Location getLocation() {
        return source;
    }

    @Override
    public ItemStack getSourceItem() {
        return sourceItem;
    }
}
