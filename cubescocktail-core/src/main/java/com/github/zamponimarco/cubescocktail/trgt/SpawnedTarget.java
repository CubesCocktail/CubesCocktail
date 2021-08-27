package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lSpawned Target", description = "gui.target.damager.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBkZmM4YTM1NjNiZjk5NmY1YzFiNzRiMGIwMTViMmNjZWIyZDA0Zjk0YmJjZGFmYjIyOTlkOGE1OTc5ZmFjMSJ9fX0=")
public class SpawnedTarget extends Target {

    public SpawnedTarget() {
    }

    public SpawnedTarget(Map<String, Object> map) {
        super(map);
    }

    public static boolean targetEnabled(ModelPath<?> path) {
        return getPossibleTargets(path).contains(SpawnedTarget.class);
    }

    @Override
    public ActionTarget getTarget(ActionArgument args) {
        return new EntityTarget(args.getArgument(ActionArgumentKey.SPAWNED));
    }

    @Override
    public Target clone() {
        return new SpawnedTarget();
    }

    @Override
    public String getName() {
        return "Spawned Target";
    }
}
