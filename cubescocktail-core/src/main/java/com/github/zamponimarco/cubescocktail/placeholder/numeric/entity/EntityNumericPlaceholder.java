package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.player.PlayerNumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.suprememob.MobNumericPlaceholder;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Parent(classArray = {AttributeNumericPlaceholder.class, PlayerNumericPlaceholder.class, HealthPlaceholder.class,
        MaxHealthPlaceholder.class, NumericVariablePlaceholder.class, ArmorPlaceholder.class, SpeedPlaceholder.class,
        KnockbackResistancePlaceholder.class, WidthPlaceholder.class, HeightPlaceholder.class, MobNumericPlaceholder.class,
        EyeHeightPlaceholder.class, CooldownPlaceholder.class})
@Enumerable.Displayable(name = "&c&lEntity Placeholders", description = "gui.placeholder.double.entity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==")
public abstract class EntityNumericPlaceholder extends NumericPlaceholder {

    public EntityNumericPlaceholder(boolean target) {
        super(target);
    }

    public EntityNumericPlaceholder(Map<String, Object> map) {
        super(map);
    }

    protected LivingEntity getEntity(ActionTarget target, ActionSource source) {
        if (this.target) {
            if (target instanceof EntityTarget) {
                return ((EntityTarget) target).getTarget();
            }
            return null;
        }
        return source.getCaster();
    }
}
