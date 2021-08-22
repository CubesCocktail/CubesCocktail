package com.github.zamponimarco.cubescocktail.action.source;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;

@AllArgsConstructor
public class EntitySource implements ActionSource {
    private final @NonNull LivingEntity source;
    private final ItemStack sourceItem;
    @Getter
    private final MainHand hand;

    public EntitySource(LivingEntity source, ItemStack sourceItem) {
        this(source, sourceItem, MainHand.RIGHT);
    }

    @Override
    public LivingEntity getCaster() {
        return source;
    }

    @Override
    public Location getLocation() {
        return source.getLocation();
    }

    @Override
    public ItemStack getSourceItem() {
        return sourceItem;
    }
}
