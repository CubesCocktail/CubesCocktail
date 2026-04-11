package com.github.zamponimarco.cubescocktail.action.targeter;

import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class ProjectileTarget implements ActionTarget {

    private final AbstractProjectile projectile;

    public ProjectileTarget(AbstractProjectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public Location getLocation() {
        return projectile.getLocation();
    }
}
