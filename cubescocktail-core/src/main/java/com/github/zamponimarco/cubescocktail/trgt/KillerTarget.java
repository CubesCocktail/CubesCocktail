package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lKiller Target", description = "gui.target.damager.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBkZmM4YTM1NjNiZjk5NmY1YzFiNzRiMGIwMTViMmNjZWIyZDA0Zjk0YmJjZGFmYjIyOTlkOGE1OTc5ZmFjMSJ9fX0=")
public class KillerTarget extends Target {

    public KillerTarget() {
    }

    public KillerTarget(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ActionTarget getTarget(Map<String, Object> args) {
        LivingEntity killer = (LivingEntity) args.get("killer");
        return new EntityTarget(killer);
    }

    public static boolean targetEnabled(ModelPath path) {
        return getPossibleTargets(path).contains(KillerTarget.class);
    }

    @Override
    public Target clone() {
        return new KillerTarget();
    }

    @Override
    public String getName() {
        return "Killer Target";
    }
}
