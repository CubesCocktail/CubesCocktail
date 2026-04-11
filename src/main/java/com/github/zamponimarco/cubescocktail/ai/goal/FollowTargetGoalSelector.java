package com.github.zamponimarco.cubescocktail.ai.goal;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.ai.goal.impl.CustomFollowTargetGoal;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.util.HeadUtils;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lFollow target &cgoal selector", description = "gui.goal-selector.follow.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjlkMWQzMTEzZWM0M2FjMjk2MWRkNTlmMjgxNzVmYjQ3MTg4NzNjNmM0NDhkZmNhODcyMjMxN2Q2NyJ9fX0=")
@Getter
@Setter
public class FollowTargetGoalSelector extends ConditionalGoalSelector {

    @Serializable(headTexture = HeadUtils.ATTENTION_HEAD, description = "gui.additional-tooltips.unknown")
    private NumericValue speedModifier;
    @Serializable(headTexture = HeadUtils.ATTENTION_HEAD, description = "gui.additional-tooltips.unknown")
    private NumericValue stopDistance;

    public FollowTargetGoalSelector() {
        this(CONDITION_DEFAULT.clone(), new NumericValue(1), new NumericValue(2));
    }

    public FollowTargetGoalSelector(Condition condition, NumericValue speedModifier, NumericValue stopDistance) {
        super(condition);
        this.speedModifier = speedModifier;
        this.stopDistance = stopDistance;
    }

    public FollowTargetGoalSelector(Map<String, Object> map) {
        super(map);
        this.speedModifier = (NumericValue) map.getOrDefault("speedModifier", new NumericValue(1));
        this.stopDistance = (NumericValue) map.getOrDefault("stopDistance", new NumericValue(2));
    }

    @Override
    public void applyToEntity(Mob e, ActionSource source, ActionTarget target) {
        int currentSize = Bukkit.getMobGoals().getAllGoals(e).size();
        Bukkit.getMobGoals().addGoal(e, currentSize, new CustomFollowTargetGoal(e, speedModifier.getRealValue(target, source),
                stopDistance.getRealValue(target, source)));
    }

    @Override
    public String getName() {
        return "&6&lFollow target &cgoal selector";
    }
}
