package com.github.zamponimarco.cubescocktail.action.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ProjectileTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import com.github.zamponimarco.cubescocktail.value.VectorValue;

import java.util.Map;

@Enumerable.Child()
@Enumerable.Displayable(name = "&c&lSet Projectile Direction", description = "gui.action.projectile.direction.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q0ZTZjMzkyZTE0ZjM0YjZhMzFlMzYzNWE4YmYxOGQ5NzNlZWY2ZGM5YjFhMzUxOTQ1MTQ5NzE5N2Q0YyJ9fX0")
public class SetProjectileDirectionAction extends ProjectileAction {

    private static final String VECTOR_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q0ZTZjMzkyZTE0ZjM0YjZhMzFlMzYzNWE4YmYxOGQ5NzNlZWY2ZGM5YjFhMzUxOTQ1MTQ5NzE5N2Q0YyJ9fX0";

    @Serializable(headTexture = VECTOR_HEAD, description = "gui.action.projectile.direction.direction")
    private VectorValue vector;

    public SetProjectileDirectionAction() {
        this(TARGET_DEFAULT, new VectorValue());
    }

    public SetProjectileDirectionAction(boolean target, VectorValue vector) {
        super(target);
        this.vector = vector;
    }

    public SetProjectileDirectionAction(Map<String, Object> map) {
        super(map);
        this.vector = (VectorValue) map.getOrDefault("vector", new VectorValue());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        if (!(target instanceof ProjectileTarget)) {
            return ActionResult.FAILURE;
        }

        AbstractProjectile projectile = ((ProjectileTarget) target).getProjectile();
        projectile.getLocation().setDirection(vector.getRealValue(target, source).computeVector(target, source));
        return ActionResult.SUCCESS;
    }

    @Override
    public String getName() {
        return "&6&lSet Projectile Direction: &c" + vector.getName();
    }

    @Override
    public Action clone() {
        return new SetProjectileDirectionAction(target, vector.clone());
    }
}
