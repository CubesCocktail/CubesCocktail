package com.github.zamponimarco.cubescocktail.placeholder.numeric.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lProjectile Speed Placeholder", description = "gui.placeholder.double.projectile.speed.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTc2OWUyYzEzNGVlNWZjNmRhZWZlNDEyZTRhZjNkNTdkZjlkYmIzY2FhY2Q4ZTM2ZTU5OTk3OWVjMWFjNCJ9fX0")
public class ProjectileSpeedPlaceholder extends ProjectileNumericPlaceholder {

    public ProjectileSpeedPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public ProjectileSpeedPlaceholder(boolean target) {
        super(target);
    }

    public ProjectileSpeedPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        AbstractProjectile projectile = getProjectile(target);

        if (projectile == null) {
            return 0.0;
        }

        return projectile.getSpeed();
    }

    @Override
    public String getName() {
        return "Target Projectile Speed";
    }

    @Override
    public NumericPlaceholder clone() {
        return new ProjectileSpeedPlaceholder(target);
    }
}
