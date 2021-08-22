package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.annotation.PossibleTargets;

import java.util.*;

@Enumerable.Parent(classArray = {CasterTarget.class, RayTraceTarget.class, LocationTarget.class, DamagedTarget.class,
        ItemTarget.class, DamagerTarget.class, KillerTarget.class, InteractorTarget.class, SpawnedTarget.class})
public abstract class Target implements Model, Cloneable {

    public Target() {

    }

    public Target(Map<String, Object> map) {

    }

    protected static Set<Class<? extends Target>> getPossibleTargets(ModelPath<?> path) {
        Set<Class<? extends Target>> targetSet = new HashSet<>();
        path.getModelPath().forEach(model -> {
            PossibleTargets targets = model.getClass().getAnnotation(PossibleTargets.class);

            if (Objects.nonNull(targets)) {
                try {
                    targetSet.addAll((Collection<? extends Class<? extends Target>>)
                            model.getClass().getDeclaredMethod(targets.value()).invoke(model));
                } catch (Exception ignored) {
                }
            }
        });
        return targetSet;
    }

    public abstract ActionTarget getTarget(Map<String, Object> args);

    @Override
    public abstract Target clone();

    public abstract String getName();
}
