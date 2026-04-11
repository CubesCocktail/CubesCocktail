package com.github.zamponimarco.cubescocktail.placeholder.numeric.item;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lDurability Placeholder", description = "gui.placeholder.double.item.durability.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzI4MzI5ZDdkMWZkZTYyMGViYTY2MTcwZjc4NGFjZmNjNTJjNjQyZmZmZjg0YzY4YWFjMzc1MmU3OGYxNTBiOSJ9fX0=")
@Getter
@Setter
public class ItemDurabilityPlaceholder extends ItemNumericPlaceholder {

    public ItemDurabilityPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public ItemDurabilityPlaceholder(boolean target) {
        super(target);
    }

    public ItemDurabilityPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        ItemStack item = getItem(target, source);
        if (item != null && item.hasItemMeta() && item.getItemMeta() instanceof Damageable) {
            return (double) ((Damageable) item.getItemMeta()).getDamage();
        }
        return 0.0;
    }

    @Override
    public String getName() {
        return String.format("%s Item Durability", target ? "Target" : "Source");
    }

    @Override
    public NumericPlaceholder clone() {
        return new ItemDurabilityPlaceholder(target);
    }
}
