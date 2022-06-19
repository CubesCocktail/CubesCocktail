package com.github.zamponimarco.cubescocktail.ai.goal;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.ai.goal.impl.CustomFloatTargetGoal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lFloat &cgoal selector", description = "gui.goal-selector.float.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTA3YTk3YzhjMTRlOTZiNGViMmEwZjg0NDAxOTU5ZDc2NjExZTc1NDdlZWIyYjZkM2E2YTYyZGQ3ODk0YzJlIn19fQ==")
public class FloatGoalSelector extends GoalSelector {

    public FloatGoalSelector() {
    }

    public FloatGoalSelector(Map<String, Object> map) {

    }

    @Override
    public void applyToEntity(Mob e, ActionSource source, ActionTarget target) {
        int currentSize = Bukkit.getMobGoals().getAllGoals(e).size();
        Bukkit.getMobGoals().addGoal(e, currentSize, new CustomFloatTargetGoal(e));
    }

    @Override
    public String getName() {
        return "&6&lFloat &cgoal selector";
    }
}
