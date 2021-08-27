package com.github.zamponimarco.cubescocktail.source;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.source.EntitySource;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "sourceEnabled", name = "&c&lInteractor Source", description = "gui.source.damager.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBkZmM4YTM1NjNiZjk5NmY1YzFiNzRiMGIwMTViMmNjZWIyZDA0Zjk0YmJjZGFmYjIyOTlkOGE1OTc5ZmFjMSJ9fX0=")
public class InteractorSource extends Source {

    public InteractorSource() {
    }

    public InteractorSource(Map<String, Object> map) {
        super(map);
    }

    public static boolean sourceEnabled(ModelPath<?> path) {
        return getPossibleSources(path).contains(InteractorSource.class);
    }

    @Override
    public ActionSource getSource(ActionArgument args) {
        return new EntitySource(args.getArgument(ActionArgumentKey.INTERACTOR), null);
    }

    @Override
    public Source clone() {
        return new InteractorSource();
    }

    @Override
    public String getName() {
        return "Interactor Source";
    }
}
