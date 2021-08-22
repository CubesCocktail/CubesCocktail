package com.github.zamponimarco.cubescocktail.projectile;

import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.entity.Entity;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class HomingProjectile extends AbstractProjectile {

    protected double turnSpeed;
    protected int projectileSpread;
    protected LivingEntity entityTarget;

    public HomingProjectile(ActionTarget target, ActionSource source, Location location, double gravity, double initialSpeed,
                            List<Action> onStartActions, List<Action> onEntityHitActions, List<Action> onBlockHitActions,
                            List<Action> onProjectileTickActions, Entity entity, double hitBoxSize, int maxDistance,
                            int projectileSpread, LivingEntity entityTarget, double turnSpeed) {
        super(target, source, location, gravity, initialSpeed, onStartActions, onEntityHitActions, onBlockHitActions,
                onProjectileTickActions, entity, hitBoxSize, maxDistance);
        this.projectileSpread = projectileSpread;
        this.entityTarget = entityTarget;
        this.turnSpeed = turnSpeed;
    }

    @Override
    protected Vector getProjectileDirection() {
        return getSpreadedInitialDirection(location.clone(), projectileSpread);
    }

    @Override
    protected void updateLocationAndDirection() {
        if (location.getDirection().isNormalized())
            location.add(location.clone().getDirection().multiply(speed).multiply(.05));

        Vector targetDifference = entityTarget.getEyeLocation().clone().toVector().subtract(location.clone().toVector());
        if (targetDifference.length() > 0) {
            targetDifference.normalize();

            lerp(location.getDirection(), targetDifference, turnSpeed);
        }
    }

    private void lerp(Vector start, Vector end, double percent) {
        start.add(end.clone().subtract(start).multiply(percent)).normalize();
    }
}
