package com.github.zamponimarco.cubescocktail.placeholder.numeric.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lProjectile Gravity Placeholder", description = "gui.placeholder.double.projectile.gravity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY4ZGZiYzk1YWRiNGY2NDhjMzYxNjRhMTVkNjhlZjVmOWM3Njk3ZDg2Zjg3MjEzYzdkN2E2NDU1NzdhYTY2In19fQ")
public class ProjectileGravityPlaceholder extends ProjectileNumericPlaceholder {

    public ProjectileGravityPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public ProjectileGravityPlaceholder(boolean target) {
        super(target);
    }

    public ProjectileGravityPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        AbstractProjectile projectile = getProjectile(target);

        if (projectile == null) {
            return 0.0;
        }

        return projectile.getGravity();
    }

    @Override
    public String getName() {
        return "Target Projectile Gravity";
    }

    @Override
    public NumericPlaceholder clone() {
        return new ProjectileGravityPlaceholder(target);
    }
}
