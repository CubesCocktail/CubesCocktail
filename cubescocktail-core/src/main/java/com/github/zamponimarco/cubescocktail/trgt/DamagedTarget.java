package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lDamaged Target", description = "gui.target.damaged.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjUyNTU5ZjJiY2VhZDk4M2Y0YjY1NjFjMmI1ZjJiNTg4ZjBkNjExNmQ0NDY2NmNlZmYxMjAyMDc5ZDI3Y2E3NCJ9fX0=")
public class DamagedTarget extends Target {

    public DamagedTarget() {
    }

    public DamagedTarget(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ActionTarget getTarget(Map<String, Object> args) {
        LivingEntity damaged = (LivingEntity) args.get("damaged");
        return new EntityTarget(damaged);
    }

    public static boolean targetEnabled(ModelPath path) {
        return getPossibleTargets(path).contains(DamagedTarget.class);
    }

    @Override
    public Target clone() {
        return new DamagedTarget();
    }

    @Override
    public String getName() {
        return "Damaged Target";
    }
}
