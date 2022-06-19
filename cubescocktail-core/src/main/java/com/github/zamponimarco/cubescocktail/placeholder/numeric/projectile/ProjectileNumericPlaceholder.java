package com.github.zamponimarco.cubescocktail.placeholder.numeric.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ProjectileTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Displayable(name = "&c&lProjectile Location Placeholders", description = "gui.placeholder.double.projectile.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FhMjVhZTFiOGU2ZTgxY2FkMTU3NTdjMzk3YmYwYzk5M2E1ZDg3NmQ5N2NiZWFlNGEyMGYyNDMzYzMyM2EifX19")
@Enumerable.Parent(classArray = {ProjectileLifePlaceholder.class, ProjectileMaxLifePlaceholder.class, ProjectileSpeedPlaceholder.class,
        ProjectileGravityPlaceholder.class, ProjectileHitBoxSizePlaceholder.class})
public abstract class ProjectileNumericPlaceholder extends NumericPlaceholder {

    public ProjectileNumericPlaceholder(boolean target) {
        super(target);
    }

    public ProjectileNumericPlaceholder(Map<String, Object> map) {
        super(map);
    }

    protected AbstractProjectile getProjectile(ActionTarget target) {
        if (target instanceof ProjectileTarget) {
            return ((ProjectileTarget) target).getProjectile();
        }
        return null;
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }
}
