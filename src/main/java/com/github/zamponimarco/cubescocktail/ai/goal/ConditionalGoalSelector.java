package com.github.zamponimarco.cubescocktail.ai.goal;

import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;

import java.util.Map;

public abstract class ConditionalGoalSelector extends GoalSelector {

    protected static final Condition CONDITION_DEFAULT = new AlwaysTrueCondition();

    @Serializable(headTexture = CONDITION_HEAD, description = "gui.goal-selector.condition",
            additionalDescription = {"gui.additional-tooltips.recreate"})
    protected Condition condition;

    public ConditionalGoalSelector(Map<String, Object> map) {
        this.condition = (Condition) map.getOrDefault("condition", CONDITION_DEFAULT.clone());
    }

    public ConditionalGoalSelector(Condition condition) {
        this.condition = condition;
    }
}
