package com.github.zamponimarco.cubescocktail.ai.trgt;

import com.destroystokyo.paper.entity.ai.GoalType;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lClear &ctarget selector", description = "gui.target-selector.clear.description",
        headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
public class ClearTargetSelector extends TargetSelector {

    public ClearTargetSelector() {
    }

    public ClearTargetSelector(Map<String, Object> map) {
        super(map);
    }

    @Override
    public void applyToEntity(Mob e, ActionSource source, ActionTarget target) {
        Bukkit.getMobGoals().removeAllGoals(e, GoalType.TARGET);
    }

    @Override
    public String getName() {
        return "&6&lClear targets";
    }
}
