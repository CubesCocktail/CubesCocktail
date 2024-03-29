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
@Enumerable.Displayable(condition = "sourceEnabled", name = "&c&lCaster Source", description = "gui.source.caster.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3OWRmMzlkNzVmYzBjZDFhNTMxMGViOGE1ODYxZDI1NDg4OGVkM2Y2ZDllMjVjMTNkNTFkZmUzYzFjODc5OSJ9fX0=")
@Getter
@Setter
public class CasterSource extends Source {

    public CasterSource() {
    }

    public CasterSource(Map<String, Object> map) {
        super(map);
    }

    public static boolean sourceEnabled(ModelPath<?> path) {
        return getPossibleSources(path).contains(CasterSource.class);
    }

    @Override
    public ActionSource getSource(ActionArgument args) {
        return new EntitySource(args.getArgument(ActionArgumentKey.CASTER), null);
    }

    @Override
    public Source clone() {
        return new CasterSource();
    }

    @Override
    public String getName() {
        return "Caster Source";
    }
}
