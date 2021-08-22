package com.github.zamponimarco.cubescocktail.action.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

@Enumerable.Parent(classArray = {SetProjectileDirectionAction.class, SetProjectileGravityAction.class,
        SetProjectileHitBoxSizeAction.class, SetProjectileLifeAction.class, SetProjectileSpeedAction.class,
        RelativeTeleportProjectileAction.class})
@Enumerable.Displayable(name = "&9&lAction &6» &cProjectile Targetable", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzNjMDYyNzE1NDkxYjNhNTczYjdlZDEwZWE4ZjliYzE5ZmZlMTljZTliNDk4ZWJiMjRmNmYwOWU0NzNlMWUifX19")
public abstract class ProjectileAction extends Action {

    public ProjectileAction(boolean target) {
        super(target);
    }

    public ProjectileAction(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }
}
