package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.LocationTarget;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lSelected Block Target", description = "gui.target.selected-block.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGMxNzU0ODUxZTM2N2U4YmViYTJhNmQ4ZjdjMmZlZGU4N2FlNzkzYWM1NDZiMGYyOTlkNjczMjE1YjI5MyJ9fX0=")
@Getter
@Setter
public class SelectedBlockTarget extends Target {

    public SelectedBlockTarget() {
    }

    public SelectedBlockTarget(Map<String, Object> map) {
        super(map);
    }

    public static boolean targetEnabled(ModelPath<?> path) {
        return getPossibleTargets(path).contains(SelectedBlockTarget.class);
    }

    @Override
    public ActionTarget getTarget(ActionArgument args) {
        return new LocationTarget(args.getArgument(ActionArgumentKey.SELECTED_BLOCK));
    }

    @Override
    public Target clone() {
        return new SelectedBlockTarget();
    }

    @Override
    public String getName() {
        return "Selected Block Target";
    }
}
