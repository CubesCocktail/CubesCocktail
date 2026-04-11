package com.github.zamponimarco.cubescocktail.placeholder.numeric.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lProjectile Max Life Placeholder", description = "gui.placeholder.double.projectile.max-life.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI0NjFiNGQ2YWIxYzE0MThlZWFiZDQ2N2Q4OTNmMGU4OWEyMWE0MjM2OTFiN2UxZjYwNWQ2Njk3ZDBhOGU1MSJ9fX0")
@Getter
@Setter
public class ProjectileMaxLifePlaceholder extends ProjectileNumericPlaceholder {

    public ProjectileMaxLifePlaceholder() {
        this(TARGET_DEFAULT);
    }

    public ProjectileMaxLifePlaceholder(boolean target) {
        super(target);
    }

    public ProjectileMaxLifePlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        AbstractProjectile projectile = getProjectile(target);

        if (projectile == null) {
            return 0.0;
        }

        return (double) projectile.getProjectileLife();
    }

    @Override
    public String getName() {
        return "Target Projectile Max Life";
    }

    @Override
    public NumericPlaceholder clone() {
        return new ProjectileMaxLifePlaceholder(target);
    }
}
