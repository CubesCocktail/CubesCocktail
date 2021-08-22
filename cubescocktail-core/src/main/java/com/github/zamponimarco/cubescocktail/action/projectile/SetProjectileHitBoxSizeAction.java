package com.github.zamponimarco.cubescocktail.action.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ProjectileTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import com.github.zamponimarco.cubescocktail.value.NumericValue;

import java.util.Map;

@Enumerable.Child()
@Enumerable.Displayable(name = "&c&lSet Projectile Hit Box Size", description = "gui.action.projectile.hit-box.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjMzYzBiYjM3ZWJlMTE5M2VlNDYxODEwMzQ2MGE3ZjEyOTI3N2E4YzdmZDA4MWI2YWVkYjM0YTkyYmQ1In19fQ")
public class SetProjectileHitBoxSizeAction extends ProjectileAction {

    private static final String HIT_BOX_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjMzYzBiYjM3ZWJlMTE5M2VlNDYxODEwMzQ2MGE3ZjEyOTI3N2E4YzdmZDA4MWI2YWVkYjM0YTkyYmQ1In19fQ";

    @Serializable(headTexture = HIT_BOX_HEAD, description = "gui.action.projectile.hit-box.hit-box")
    private NumericValue hitBoxSize;

    public SetProjectileHitBoxSizeAction() {
        this(TARGET_DEFAULT, new NumericValue());
    }

    public SetProjectileHitBoxSizeAction(boolean target, NumericValue hitBoxSize) {
        super(target);
        this.hitBoxSize = hitBoxSize;
    }

    public SetProjectileHitBoxSizeAction(Map<String, Object> map) {
        super(map);
        this.hitBoxSize = (NumericValue) map.getOrDefault("hitBoxSize", new NumericValue());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, Map<String, Object> map) {
        if (!(target instanceof ProjectileTarget)) {
            return ActionResult.FAILURE;
        }

        AbstractProjectile projectile = ((ProjectileTarget) target).getProjectile();
        projectile.setHitBoxSize(hitBoxSize.getRealValue(target, source));
        return ActionResult.SUCCESS;
    }

    @Override
    public String getName() {
        return "&6&lSet Projectile Hit Box Size: &c" + hitBoxSize.getName();
    }

    @Override
    public Action clone() {
        return new SetProjectileHitBoxSizeAction(target, hitBoxSize.clone());
    }
}
