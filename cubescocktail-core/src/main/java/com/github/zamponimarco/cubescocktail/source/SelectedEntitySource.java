package com.github.zamponimarco.cubescocktail.source;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.source.EntitySource;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "sourceEnabled", name = "&c&lSelected Entity Source", description = "gui.source.selected-entity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ")
@Getter
@Setter
public class SelectedEntitySource extends Source {

    public SelectedEntitySource() {
    }

    public SelectedEntitySource(Map<String, Object> map) {
        super(map);
    }

    public static boolean sourceEnabled(ModelPath<?> path) {
        return getPossibleSources(path).contains(SelectedEntitySource.class);
    }

    @Override
    public ActionSource getSource(ActionArgument args) {
        return new EntitySource(args.getArgument(ActionArgumentKey.SELECTED_ENTITY), null);
    }

    @Override
    public Source clone() {
        return new SelectedEntitySource();
    }

    @Override
    public String getName() {
        return "Selected Entity Source";
    }
}
