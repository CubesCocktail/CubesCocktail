package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.mobdrink;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Parent(classArray = {MobLevelPlaceholder.class})
@Enumerable.Displayable(name = "&c&lMobDrink Placeholders", condition = "mobDrinkEnabled", description = "gui.placeholder.double.mob.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjEyYjU4Yzg0MWIzOTQ4NjNkYmNjNTRkZTFjMmFkMjY0OGFmOGYwM2U2NDg5ODhjMWY5Y2VmMGJjMjBlZTIzYyJ9fX0=")
public abstract class MobNumericPlaceholder extends NumericPlaceholder {

    public MobNumericPlaceholder(boolean target) {
        super(target);
    }

    public MobNumericPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public static boolean mobDrinkEnabled(ModelPath path) {
        return CubesCocktail.getInstance().getMobDrinkHook().isEnabled();
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
