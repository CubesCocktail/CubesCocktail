package com.github.zamponimarco.cubescocktail.placeholder.numeric.item;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ItemTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Parent(classArray = {ItemDurabilityPlaceholder.class, ItemMaxDurabilityPlaceholder.class,
        ItemNumericVariablePlaceholder.class, ItemEnchantLevelPlaceholder.class})
@Enumerable.Displayable(name = "&c&lItem Placeholders", description = "gui.placeholder.double.item.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTJiMzViZGE1ZWJkZjEzNWY0ZTcxY2U0OTcyNmZiZWM1NzM5ZjBhZGVkZjAxYzUxOWUyYWVhN2Y1MTk1MWVhMiJ9fX0=")
public abstract class ItemNumericPlaceholder extends NumericPlaceholder {

    public ItemNumericPlaceholder(boolean target) {
        super(target);
    }

    public ItemNumericPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public ItemStack getItem(ActionTarget target, ActionSource source) {
        if (this.target && target instanceof ItemTarget) {
            return ((ItemTarget) target).getTarget();
        } else if (!this.target) {
            return source.getSourceItem();
        }
        return null;
    }
}
