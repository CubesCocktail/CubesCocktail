package com.github.zamponimarco.cubescocktail.action.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ProjectileTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import com.github.zamponimarco.cubescocktail.value.NumericValue;

import java.util.Map;

@Enumerable.Child()
@Enumerable.Displayable(name = "&c&lSet Projectile Gravity", description = "gui.action.projectile.gravity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY4ZGZiYzk1YWRiNGY2NDhjMzYxNjRhMTVkNjhlZjVmOWM3Njk3ZDg2Zjg3MjEzYzdkN2E2NDU1NzdhYTY2In19fQ")
public class SetProjectileGravityAction extends ProjectileAction {

    private static final String GRAVITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY4ZGZiYzk1YWRiNGY2NDhjMzYxNjRhMTVkNjhlZjVmOWM3Njk3ZDg2Zjg3MjEzYzdkN2E2NDU1NzdhYTY2In19fQ";

    @Serializable(headTexture = GRAVITY_HEAD, description = "gui.action.projectile.gravity.gravity")
    private NumericValue gravity;

    public SetProjectileGravityAction() {
        this(TARGET_DEFAULT, new NumericValue());
    }

    public SetProjectileGravityAction(boolean target, NumericValue gravity) {
        super(target);
        this.gravity = gravity;
    }

    public SetProjectileGravityAction(Map<String, Object> map) {
        super(map);
        this.gravity = (NumericValue) map.getOrDefault("gravity", new NumericValue());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        if (!(target instanceof ProjectileTarget)) {
            return ActionResult.FAILURE;
        }

        AbstractProjectile projectile = ((ProjectileTarget) target).getProjectile();
        projectile.setGravity(gravity.getRealValue(target, source));
        return ActionResult.SUCCESS;
    }

    @Override
    public String getName() {
        return "&6&lSet Projectile Gravity: &c" + gravity.getName();
    }

    @Override
    public Action clone() {
        return new SetProjectileGravityAction(target, gravity.clone());
    }
}
