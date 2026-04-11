package com.github.zamponimarco.cubescocktail.action.item;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ItemTarget;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Parent(classArray = {DurabilityAction.class, ItemNameAction.class, ItemLoreAction.class})
@Enumerable.Displayable(name = "&9&lAction &6» &cItem Targetable", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYThlZDg2M2QxNDA1ZGQ4YWM0OGU4ZTU3MTlhYWRmYWRiYTM5Y2RjNjllZTY3MzM2NTU4ZmE4MTYwZTQ3NTk0OCJ9fX0=")
public abstract class ItemAction extends Action {

    public ItemAction(boolean target) {
        super(target);
    }

    public ItemAction(Map<String, Object> map) {
        super(map);
    }

    protected ItemStack getItemStack(ActionTarget target, ActionSource source) {
        if (this.target && target instanceof ItemTarget) {
            return ((ItemTarget) target).getTarget();
        } else if (!this.target) {
            return source.getSourceItem();
        }
        return null;
    }

}
