package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lSelected Entity Target", description = "gui.target.caster.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3OWRmMzlkNzVmYzBjZDFhNTMxMGViOGE1ODYxZDI1NDg4OGVkM2Y2ZDllMjVjMTNkNTFkZmUzYzFjODc5OSJ9fX0=")
public class SelectedEntityTarget extends Target {

    public SelectedEntityTarget() {
    }

    public SelectedEntityTarget(Map<String, Object> map) {
        super(map);
    }

    public static boolean targetEnabled(ModelPath<?> path) {
        return getPossibleTargets(path).contains(SelectedEntityTarget.class);
    }

    @Override
    public ActionTarget getTarget(ActionArgument args) {
        return new EntityTarget(args.getArgument(ActionArgumentKey.SELECTED_ENTITY));
    }

    @Override
    public Target clone() {
        return new SelectedEntityTarget();
    }

    @Override
    public String getName() {
        return "Selected Entity Target";
    }
}
