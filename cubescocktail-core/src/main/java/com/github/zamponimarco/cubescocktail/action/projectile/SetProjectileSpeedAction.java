package com.github.zamponimarco.cubescocktail.action.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ProjectileTarget;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child()
@Enumerable.Displayable(name = "&c&lSet Projectile Speed", description = "gui.action.projectile.speed.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTc2OWUyYzEzNGVlNWZjNmRhZWZlNDEyZTRhZjNkNTdkZjlkYmIzY2FhY2Q4ZTM2ZTU5OTk3OWVjMWFjNCJ9fX0")
@Getter
@Setter
public class SetProjectileSpeedAction extends ProjectileAction {

    private static final String SPEED_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTc2OWUyYzEzNGVlNWZjNmRhZWZlNDEyZTRhZjNkNTdkZjlkYmIzY2FhY2Q4ZTM2ZTU5OTk3OWVjMWFjNCJ9fX0";

    @Serializable(headTexture = SPEED_HEAD, description = "gui.action.projectile.speed.speed")
    private NumericValue speed;

    public SetProjectileSpeedAction() {
        this(TARGET_DEFAULT, new NumericValue());
    }

    public SetProjectileSpeedAction(boolean target, NumericValue speed) {
        super(target);
        this.speed = speed;
    }

    public SetProjectileSpeedAction(Map<String, Object> map) {
        super(map);
        this.speed = (NumericValue) map.getOrDefault("speed", new NumericValue());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        if (!(target instanceof ProjectileTarget)) {
            return ActionResult.FAILURE;
        }

        AbstractProjectile projectile = ((ProjectileTarget) target).getProjectile();
        projectile.setSpeed(speed.getRealValue(target, source));
        return ActionResult.SUCCESS;
    }

    @Override
    public String getName() {
        return "&6&lSet Projectile Speed: &c" + speed.getName();
    }

    @Override
    public Action clone() {
        return new SetProjectileSpeedAction(target, speed.clone());
    }
}
