package com.github.zamponimarco.cubescocktail.placeholder.numeric.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lProjectile Hit Box Size Placeholder", description = "gui.placeholder.double.projectile.hit-box.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjMzYzBiYjM3ZWJlMTE5M2VlNDYxODEwMzQ2MGE3ZjEyOTI3N2E4YzdmZDA4MWI2YWVkYjM0YTkyYmQ1In19fQ")
public class ProjectileHitBoxSizePlaceholder extends ProjectileNumericPlaceholder {

    public ProjectileHitBoxSizePlaceholder() {
        this(TARGET_DEFAULT);
    }

    public ProjectileHitBoxSizePlaceholder(boolean target) {
        super(target);
    }

    public ProjectileHitBoxSizePlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        AbstractProjectile projectile = getProjectile(target);

        if (projectile == null) {
            return 0.0;
        }

        return projectile.getHitBoxSize();
    }

    @Override
    public String getName() {
        return "Target Projectile Hit Box Size";
    }

    @Override
    public NumericPlaceholder clone() {
        return new ProjectileHitBoxSizePlaceholder(target);
    }
}
