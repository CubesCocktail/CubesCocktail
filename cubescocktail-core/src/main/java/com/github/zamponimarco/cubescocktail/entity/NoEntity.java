package com.github.zamponimarco.cubescocktail.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lNo entity", description = "gui.entity.no-entity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZkMjQwMDAwMmFkOWZiYmJkMDA2Njk0MWViNWIxYTM4NGFiOWIwZTQ4YTE3OGVlOTZlNGQxMjlhNTIwODY1NCJ9fX0=")
@Getter
@Setter
public class NoEntity extends Entity {

    public NoEntity() {

    }

    public NoEntity(Map<String, Object> map) {
        super();
    }

    public static NoEntity deserialize(Map<String, Object> map) {
        return new NoEntity();
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location l, ActionTarget target, ActionSource source) {
        return null;
    }

    @Override
    public Entity clone() {
        return new NoEntity();
    }

    @Override
    public EntityType getType() {
        return null;
    }
}
