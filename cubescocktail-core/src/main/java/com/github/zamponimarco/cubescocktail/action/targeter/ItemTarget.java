package com.github.zamponimarco.cubescocktail.action.targeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public class ItemTarget implements ActionTarget {

    private final ItemStack target;
    private final LivingEntity owner;

    @Override
    public Location getLocation() {
        return owner.getLocation();
    }
}
