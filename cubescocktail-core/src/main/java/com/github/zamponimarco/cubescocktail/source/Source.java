package com.github.zamponimarco.cubescocktail.source;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.annotation.PossibleSources;

import java.util.*;

@Enumerable.Parent(classArray = {CasterSource.class, RayTraceSource.class, DamagedSource.class, DamagerSource.class,
        LocationSource.class, KillerSource.class, InteractorSource.class, SpawnedSource.class})
public abstract class Source implements Model, Cloneable {

    public Source() {

    }

    public Source(Map<String, Object> map) {

    }

    protected static Set<Class<? extends Source>> getPossibleSources(ModelPath<?> path) {
        Set<Class<? extends Source>> targetSet = new HashSet<>();
        path.getModelPath().forEach(model -> {
            PossibleSources targets = model.getClass().getAnnotation(PossibleSources.class);

            if (Objects.nonNull(targets)) {
                try {
                    targetSet.addAll((Collection<? extends Class<? extends Source>>)
                            model.getClass().getDeclaredMethod(targets.value()).invoke(model));
                } catch (Exception ignored) {
                }
            }
        });
        return targetSet;
    }

    public abstract ActionSource getSource(Map<String, Object> args);

    @Override
    public abstract Source clone();

    public abstract String getName();
}
