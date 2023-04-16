package com.github.zamponimarco.cubescocktail.source;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.source.LocationSource;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "sourceEnabled", name = "&c&lSelected Block Source", description = "gui.source.selected-block.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGMxNzU0ODUxZTM2N2U4YmViYTJhNmQ4ZjdjMmZlZGU4N2FlNzkzYWM1NDZiMGYyOTlkNjczMjE1YjI5MyJ9fX0")
@Getter
@Setter
public class SelectedBlockSource extends Source {

    public SelectedBlockSource() {
    }

    public SelectedBlockSource(Map<String, Object> map) {
        super(map);
    }

    public static boolean sourceEnabled(ModelPath<?> path) {
        return getPossibleSources(path).contains(SelectedBlockSource.class);
    }

    @Override
    public ActionSource getSource(ActionArgument args) {
        return new LocationSource(args.getArgument(ActionArgumentKey.SELECTED_BLOCK),
                args.getArgument(ActionArgumentKey.CASTER), null);
    }

    @Override
    public Source clone() {
        return new SelectedBlockSource();
    }

    @Override
    public String getName() {
        return "Selected Block Source";
    }
}
