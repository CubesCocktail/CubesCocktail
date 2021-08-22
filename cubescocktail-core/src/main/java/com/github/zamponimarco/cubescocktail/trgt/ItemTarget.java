package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lItem Target", description = "gui.target.item.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTJiMzViZGE1ZWJkZjEzNWY0ZTcxY2U0OTcyNmZiZWM1NzM5ZjBhZGVkZjAxYzUxOWUyYWVhN2Y1MTk1MWVhMiJ9fX0=")
public class ItemTarget extends Target {

    public ItemTarget() {
    }

    public ItemTarget(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ActionTarget getTarget(Map<String, Object> args) {
        LivingEntity caster = (LivingEntity) args.get("caster");
        ItemStack item = (ItemStack) args.get("item");
        return new com.github.zamponimarco.cubescocktail.action.targeter.ItemTarget(item, caster);
    }

    public static boolean targetEnabled(ModelPath path) {
        return getPossibleTargets(path).contains(ItemTarget.class);
    }

    @Override
    public Target clone() {
        return new ItemTarget();
    }

    @Override
    public String getName() {
        return "Item Target";
    }
}
