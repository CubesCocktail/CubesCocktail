package com.github.zamponimarco.cubescocktail.ai.goal;

import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Parent(classArray = {ConditionalGoalSelector.class, ClearGoalSelector.class, MeleeAttackGoalSelector.class,
        AvoidTargetGoalSelector.class, FollowTargetGoalSelector.class,
        FloatGoalSelector.class, ThrowProjectileGoalSelector.class})
public abstract class GoalSelector implements Model {

    protected static final NumericValue SPEED_DEFAULT = new NumericValue(1);

    protected static final String CONDITION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMyNzEwNTI3MTllZjY0MDc5ZWU4YzE0OTg5NTEyMzhhNzRkYWM0YzI3Yjk1NjQwZGI2ZmJkZGMyZDZiNWI2ZSJ9fX0=";
    protected static final String SPEED_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQyMzI5YTljNDEwNDA4NDE5N2JkNjg4NjE1ODUzOTg0ZDM3ZTE3YzJkZDIzZTNlNDEyZGQ0MmQ3OGI5OGViIn19fQ==";


    public GoalSelector() {
    }

    public GoalSelector(Map<String, Object> map) {

    }

    public abstract void applyToEntity(Mob e, ActionSource source, ActionTarget target);

    public abstract String getName();

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(getClass().
                getAnnotation(Enumerable.Displayable.class).headTexture()), MessageUtils.color(getName()), Libs.getLocale().
                getList("gui.additional-tooltips.delete"));
    }
}
