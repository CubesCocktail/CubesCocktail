package com.github.zamponimarco.cubescocktail.action.targeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

@AllArgsConstructor
@Getter
public class EntityTarget implements ActionTarget {

    private final LivingEntity target;

    @Override
    public Location getLocation() {
        return target.getLocation();
    }
}
