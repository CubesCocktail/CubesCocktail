package com.github.zamponimarco.cubescocktail.placeholder.numeric.item;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lMax Durability Placeholder", description = "gui.placeholder.double.item.max-durability.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjFiYjJjM2JkNjVjZGQ4NGE4MDRlY2E5OGI3YTQ2NzM1ZjAxZTZhMWM5MTk5ZDY2NjE2NjNkYmRiNGY1YjQifX19")
@Getter
@Setter
public class ItemMaxDurabilityPlaceholder extends ItemNumericPlaceholder {

    public ItemMaxDurabilityPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public ItemMaxDurabilityPlaceholder(boolean target) {
        super(target);
    }

    public ItemMaxDurabilityPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        ItemStack item = getItem(target, source);
        if (item != null) {
            return (double) item.getType().getMaxDurability();
        }
        return 0.0;
    }

    @Override
    public String getName() {
        return String.format("%s Item Max Durability", target ? "Target" : "Source");
    }

    @Override
    public NumericPlaceholder clone() {
        return new ItemMaxDurabilityPlaceholder(target);
    }
}
