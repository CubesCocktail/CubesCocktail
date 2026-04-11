package com.github.zamponimarco.cubescocktail.entity.sorter;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Map;

@Enumerable.Parent(classArray = {ProximitySorter.class, RandomSorter.class})
public abstract class EntitySorter implements Model, Cloneable {

    public EntitySorter() {

    }

    public EntitySorter(Map<String, Object> map) {

    }

    public abstract void sortCollection(List<LivingEntity> list, ActionTarget target, ActionSource source);

    public abstract EntitySorter clone();
}
