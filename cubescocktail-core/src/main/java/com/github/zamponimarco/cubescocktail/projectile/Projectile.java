package com.github.zamponimarco.cubescocktail.projectile;

import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.entity.Entity;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class Projectile extends AbstractProjectile {

    protected int projectileSpread;

    public Projectile(ActionTarget target, ActionSource source, Location location, double gravity, double initialSpeed,
                      List<Action> onStartActions, List<Action> onEntityHitActions, List<Action> onBlockHitActions,
                      List<Action> onProjectileTickActions, Entity entity, double hitBoxSize, int maxDistance,
                      int projectileSpread) {
        super(target, source, location, gravity, initialSpeed, onStartActions, onEntityHitActions, onBlockHitActions,
                onProjectileTickActions, entity, hitBoxSize, maxDistance);
        this.projectileSpread = projectileSpread;
    }

    @Override
    protected Vector getProjectileDirection() {
        return getSpreadedInitialDirection(location.clone(), projectileSpread);
    }

    @Override
    protected void updateLocationAndDirection() {
        if (location.getDirection().length() > 0)
            location.add(location.clone().getDirection().multiply(speed).multiply(.05));
    }

}
