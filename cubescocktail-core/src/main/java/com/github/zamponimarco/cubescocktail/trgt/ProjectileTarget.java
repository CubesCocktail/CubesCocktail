package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lProjectile Target", description = "gui.target.projectile.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE1ZmVjNjUxOGE0MWYxNjYxMzFlNjViMTBmNDZmYjg3ZTk3YzQ5MmI0NmRiYzI1ZGUyNjM3NjcyMWZhNjRlMCJ9fX0=")
public class ProjectileTarget extends Target {

    public ProjectileTarget() {
    }

    public ProjectileTarget(Map<String, Object> map) {
        super(map);
    }

    public static boolean targetEnabled(ModelPath<?> path) {
        return getPossibleTargets(path).contains(ProjectileTarget.class);
    }

    @Override
    public ActionTarget getTarget(ActionArgument args) {
        return new com.github.zamponimarco.cubescocktail.action.targeter.
                ProjectileTarget(args.getArgument(ActionArgumentKey.PROJECTILE));
    }

    @Override
    public Target clone() {
        return new ProjectileTarget();
    }

    @Override
    public String getName() {
        return "Projectile Target";
    }
}
