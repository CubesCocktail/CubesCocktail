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
@Enumerable.Displayable(condition = "sourceEnabled", name = "&c&lDamaged Source", description = "gui.source.damaged.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjUyNTU5ZjJiY2VhZDk4M2Y0YjY1NjFjMmI1ZjJiNTg4ZjBkNjExNmQ0NDY2NmNlZmYxMjAyMDc5ZDI3Y2E3NCJ9fX0=")
@Getter
@Setter
public class DamagedSource extends Source {

    public DamagedSource() {
    }

    public DamagedSource(Map<String, Object> map) {
        super(map);
    }

    public static boolean sourceEnabled(ModelPath<?> path) {
        return getPossibleSources(path).contains(DamagedSource.class);
    }

    @Override
    public ActionSource getSource(ActionArgument args) {
        return new EntitySource(args.getArgument(ActionArgumentKey.DAMAGED), null);
    }

    @Override
    public Source clone() {
        return new DamagedSource();
    }

    @Override
    public String getName() {
        return "Damaged Source";
    }
}
