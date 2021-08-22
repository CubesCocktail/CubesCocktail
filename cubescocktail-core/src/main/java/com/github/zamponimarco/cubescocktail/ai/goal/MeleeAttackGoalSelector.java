package com.github.zamponimarco.cubescocktail.ai.goal;

import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.util.HeadUtils;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.github.zamponimarco.cubescocktail.ai.goal.impl.CustomMeleeAttackTargetGoal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lMelee attack &cgoal selector", description = "gui.goal-selector.melee.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBkZmM4YTM1NjNiZjk5NmY1YzFiNzRiMGIwMTViMmNjZWIyZDA0Zjk0YmJjZGFmYjIyOTlkOGE1OTc5ZmFjMSJ9fX0=")
public class MeleeAttackGoalSelector extends ConditionalGoalSelector {

    private static final boolean FOLLOWING_DEFAULT = false;
    private static final NumericValue RELOAD_DEFAULT = new NumericValue(20);

    private static final String CONDITION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMyNzEwNTI3MTllZjY0MDc5ZWU4YzE0OTg5NTEyMzhhNzRkYWM0YzI3Yjk1NjQwZGI2ZmJkZGMyZDZiNWI2ZSJ9fX0=";

    @Serializable(headTexture = SPEED_HEAD, description = "gui.goal-selector.speed")
    @Serializable.Optional(defaultValue = "SPEED_DEFAULT")
    private NumericValue speedModifier;

    @Serializable(headTexture = SPEED_HEAD, description = "gui.goal-selector.speed")
    @Serializable.Number(minValue = 0, scale = 1.0)
    @Serializable.Optional(defaultValue = "SPEED_DEFAULT")
    private NumericValue attackReload;

    @Serializable(headTexture = HeadUtils.ATTENTION_HEAD, description = "gui.additional-tooltips.unknown")
    @Serializable.Optional(defaultValue = "FOLLOWING_DEFAULT")
    private boolean followingActionTargetEvenIfNotSeen;

    public MeleeAttackGoalSelector() {
        this(CONDITION_DEFAULT.clone(), SPEED_DEFAULT.clone(), RELOAD_DEFAULT.clone(), FOLLOWING_DEFAULT);
    }

    public MeleeAttackGoalSelector(Map<String, Object> map) {
        super(map);
        this.speedModifier = (NumericValue) map.getOrDefault("speedModifier", SPEED_DEFAULT.clone());
        this.attackReload = (NumericValue) map.getOrDefault("attackReload", RELOAD_DEFAULT.clone());
        this.followingActionTargetEvenIfNotSeen = (boolean) map.getOrDefault("followingActionTargetEvenIfNotSeen", FOLLOWING_DEFAULT);
        this.condition = (Condition) map.getOrDefault("condition", new AlwaysTrueCondition());
    }

    public MeleeAttackGoalSelector(Condition condition, NumericValue speedModifier, NumericValue attackReload,
                                   boolean followingActionTargetEvenIfNotSeen) {
        super(condition);
        this.speedModifier = speedModifier;
        this.attackReload = attackReload;
        this.followingActionTargetEvenIfNotSeen = followingActionTargetEvenIfNotSeen;
    }

    @Override
    public void applyToEntity(Mob e, ActionSource source, ActionTarget target) {
        int currentSize = Bukkit.getMobGoals().getAllGoals(e).size();
        Bukkit.getMobGoals().addGoal(e, currentSize, new CustomMeleeAttackTargetGoal(e, speedModifier.getRealValue(target, source),
                attackReload.getRealValue(target, source).intValue(), followingActionTargetEvenIfNotSeen, condition));
    }

    @Override
    public String getName() {
        return "&6&lMelee attack &cgoal selector";
    }
}
