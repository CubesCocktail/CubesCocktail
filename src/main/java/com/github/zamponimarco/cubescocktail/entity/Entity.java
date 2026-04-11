package com.github.zamponimarco.cubescocktail.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Map;

@Enumerable.Parent(classArray = {NoEntity.class, GenericEntity.class, ItemEntity.class, FallingBlockEntity.class,
        MobDrinkEntity.class})
public abstract class Entity implements Model, Cloneable {

    public Entity() {
    }

    public Entity(Map<String, Object> map) {
    }

    public abstract org.bukkit.entity.Entity spawnEntity(Location l, ActionTarget target, ActionSource source);

    public abstract Entity clone();

    public abstract EntityType getType();

}
