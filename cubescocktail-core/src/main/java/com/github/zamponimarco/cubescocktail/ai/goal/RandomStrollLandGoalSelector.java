package com.github.zamponimarco.cubescocktail.ai.goal;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Mob;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lRandom stroll land &cgoal selector", description = "gui.goal-selector.stroll-land.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRjODU0MjllYjViYzJiZWEyYzVkYmVjODllNTI4ZWQzYTU0ZDIwN2ZiMzc0NDY4MGQ2MmY4NGVkYzVlOTQxIn19fQ==")
@Getter
@Setter
public class RandomStrollLandGoalSelector extends ConditionalGoalSelector {

    private final static NumericValue PROBABILITY_DEFAULT = new NumericValue(0.001);

    @Serializable(headTexture = SPEED_HEAD, description = "gui.goal-selector.speed")
    @Serializable.Optional(defaultValue = "SPEED_DEFAULT")
    private NumericValue speed;

    public RandomStrollLandGoalSelector() {
        this(CONDITION_DEFAULT.clone(), SPEED_DEFAULT.clone());
    }

    public RandomStrollLandGoalSelector(Map<String, Object> map) {
        super(map);
        this.speed = (NumericValue) map.getOrDefault("speed", SPEED_DEFAULT.clone());
    }

    public RandomStrollLandGoalSelector(Condition condition, NumericValue speed) {
        super(condition);
        this.speed = speed;
    }

    @Override
    public void applyToEntity(Mob e, ActionSource source, ActionTarget target) {
    }

    @Override
    public String getName() {
        return "&6&lRandom stroll land &cgoal selector";
    }
}
