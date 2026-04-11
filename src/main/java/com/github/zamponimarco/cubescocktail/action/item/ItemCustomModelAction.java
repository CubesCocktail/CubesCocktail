package com.github.zamponimarco.cubescocktail.action.item;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lSet Custom Model", description = "gui.action.item.durability.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzI4MzI5ZDdkMWZkZTYyMGViYTY2MTcwZjc4NGFjZmNjNTJjNjQyZmZmZjg0YzY4YWFjMzc1MmU3OGYxNTBiOSJ9fX0=")
@Getter
@Setter
public class ItemCustomModelAction extends ItemAction {

    private static final NumericValue DURABILITY_DEFAULT = new NumericValue(10);

    private static final String DURABILITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzI4MzI5ZDdkMWZkZTYyMGViYTY2MTcwZjc4NGFjZmNjNTJjNjQyZmZmZjg0YzY4YWFjMzc1MmU3OGYxNTBiOSJ9fX0=";

    @Serializable(headTexture = DURABILITY_HEAD, description = "gui.action.item.durability.durability", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, scale = 1)
    private NumericValue customModel;

    public ItemCustomModelAction() {
        this(TARGET_DEFAULT, DURABILITY_DEFAULT.clone());
    }

    public ItemCustomModelAction(boolean target, NumericValue customModel) {
        super(target);
        this.customModel = customModel;
    }

    public ItemCustomModelAction(Map<String, Object> map) {
        super(map);
        this.customModel = (NumericValue) map.getOrDefault("customModel", DURABILITY_DEFAULT.clone());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        ItemStack item = getItemStack(target, source);
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setCustomModelData(customModel.getRealValue(target, source).intValue());
            item.setItemMeta(meta);
        }
        return ActionResult.FAILURE;
    }

    @Override
    public String getName() {
        return "&6&lSet Custom Model: &c" + customModel.getName();
    }

    @Override
    public Action clone() {
        return new ItemCustomModelAction(target, customModel.clone());
    }
}
