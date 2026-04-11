package com.github.zamponimarco.cubescocktail.ai.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.ai.trgt.impl.NearestAttackableTargetGoal;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lNearest &ctarget selector", description = "gui.target-selector.nearest.description",
        headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==")
@Getter
@Setter
public class NearestAttackableTargetSelector extends TargetSelector {


    private static final String SELECTOR_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM4ZDI3NTk1NjlkNTE1ZDI0NTRkNGE3ODkxYTk0Y2M2M2RkZmU3MmQwM2JmZGY3NmYxZDQyNzdkNTkwIn19fQ==";

    @Serializable(headTexture = SELECTOR_HEAD, description = "gui.target-selector.nearest.selectors")
    private Condition condition;

    public NearestAttackableTargetSelector() {
        this(new AlwaysTrueCondition());
    }

    public NearestAttackableTargetSelector(Map<String, Object> map) {
        super(map);
        this.condition = (Condition) map.getOrDefault("condition", new AlwaysTrueCondition());
    }

    public NearestAttackableTargetSelector(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void applyToEntity(Mob mob, ActionSource source, ActionTarget target) {
        int currentSize = Bukkit.getMobGoals().getAllGoals(mob).size();
        Bukkit.getMobGoals().addGoal(mob, currentSize, new NearestAttackableTargetGoal(mob, condition));
    }

    @Override
    public String getName() {
        return "&6&lNearest target such that " + condition.getName();
    }
}
