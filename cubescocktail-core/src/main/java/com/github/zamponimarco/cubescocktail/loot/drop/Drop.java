package com.github.zamponimarco.cubescocktail.loot.drop;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import java.util.Collection;
import java.util.Map;

@Enumerable.Parent(classArray = {ItemDrop.class})
@Getter
@Setter
public abstract class Drop implements Model {

    private static final String CONDITION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMyNzEwNTI3MTllZjY0MDc5ZWU4YzE0OTg5NTEyMzhhNzRkYWM0YzI3Yjk1NjQwZGI2ZmJkZGMyZDZiNWI2ZSJ9fX0=";

    @Serializable(headTexture = CONDITION_HEAD, description = "gui.loot.drop.condition",
            additionalDescription = {"gui.additional-tooltips.recreate"})
    private Condition condition;

    public Drop(Condition condition) {
        this.condition = condition;
    }

    public Drop(Map<String, Object> map) {
        this.condition = (Condition) map.get("condition");
    }

    public abstract Collection<ItemStack> getLoot(ActionSource source, ActionTarget target, LootContext context);

    public Collection<ItemStack> populateLoot(ActionSource source, ActionTarget target, LootContext context) {
        if (condition.checkCondition(target, source)) {
            return getLoot(source, target, context);
        }
        return Lists.newArrayList();
    }

}
