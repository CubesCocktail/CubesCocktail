package com.github.zamponimarco.cubescocktail.action.targeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@AllArgsConstructor
@Getter
public class LocationTarget implements ActionTarget {

    private final Location target;

    @Override
    public Location getLocation() {
        return target;
    }
}
