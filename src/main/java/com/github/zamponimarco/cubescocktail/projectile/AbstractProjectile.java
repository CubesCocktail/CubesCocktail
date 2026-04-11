package com.github.zamponimarco.cubescocktail.projectile;

import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ProjectileTarget;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class AbstractProjectile {

    protected AbstractProjectile projectile;
    protected ActionArgument args;
    protected ActionTarget target;
    protected ActionSource source;
    protected Location location;
    protected double gravity;
    protected double speed;
    protected List<Action> onStartActions;
    protected List<Action> onEntityHitActions;
    protected List<Action> onBlockHitActions;
    protected List<Action> onProjectileTickActions;
    protected Entity entity;
    protected double hitBoxSize;
    protected int maxLife;
    protected int projectileLife;
    private Location oldLocation;
    private com.github.zamponimarco.cubescocktail.entity.Entity baseEntity;

    public AbstractProjectile(ActionArgument args, ActionTarget target, ActionSource source, Location location, double gravity, double speed,
                              List<Action> onStartActions, List<Action> onEntityHitActions, List<Action> onBlockHitActions,
                              List<Action> onProjectileTickActions, com.github.zamponimarco.cubescocktail.entity.Entity baseEntity,
                              double hitBoxSize, int maxLife) {
        this.args = args;
        this.target = target;
        this.source = source;
        this.location = location;
        this.gravity = gravity;
        this.speed = speed;
        this.onStartActions = onStartActions;
        this.onEntityHitActions = onEntityHitActions;
        this.onBlockHitActions = onBlockHitActions;
        this.onProjectileTickActions = onProjectileTickActions;
        this.hitBoxSize = hitBoxSize;
        this.maxLife = maxLife;
        this.baseEntity = baseEntity;

        this.projectile = this;
    }

    public static boolean isProjectile(Entity entity) {
        return entity.getMetadata("projectile").stream().anyMatch(value ->
                Objects.equals(value.getOwningPlugin(), CubesCocktail.getInstance()));
    }

    protected abstract Vector getProjectileDirection();

    protected abstract void updateLocationAndDirection();

    public void run() {
        projectileLife = 0;
        location.setDirection(getProjectileDirection());
        oldLocation = location.clone();

        args.setArgument(ActionArgumentKey.PROJECTILE, projectile);

        onStartActions.forEach(action -> action.execute(new ProjectileTarget(projectile), source, args));

        this.entity = getEntity(baseEntity);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (projectileHitBlock()) {
                    onBlockHitActions.forEach(Action -> Action.execute(new ProjectileTarget(projectile), source, args));
                    if (!args.isArgumentPresent(ActionArgumentKey.CANCELLED) ||
                            !args.getArgument(ActionArgumentKey.CANCELLED))
                        remove();
                    source.getCaster().removeMetadata("hitFace", CubesCocktail.getInstance());
                }

                List<LivingEntity> hitEntities = getHitEntities();
                if (!hitEntities.isEmpty()) {
                    hitEntities.forEach(livingEntity -> onEntityHitActions.forEach(action -> action.
                            execute(new EntityTarget(livingEntity), source, args)));
                    remove();
                }

                onProjectileTickActions.forEach(action -> action.execute(new ProjectileTarget(projectile), source, args));

                if (projectileLife++ > maxLife) {
                    remove();
                }

                if (gravity != 0) {
                    location.setDirection(location.getDirection().subtract(new Vector(0, gravity * .05, 0)));
                }

                oldLocation = location.clone();

                updateLocationAndDirection();

                if (entity != null && location.getDirection().isNormalized())
                    entity.setVelocity(location.getDirection().multiply(speed).multiply(.05));
            }

            private void remove() {
                if (entity != null) {
                    entity.remove();
                }
                this.cancel();
            }
        }.runTaskTimer(CubesCocktail.getInstance(), 0, 1).getTaskId();
    }

    public Entity getEntity(com.github.zamponimarco.cubescocktail.entity.Entity entity) {
        Entity projectile = entity.spawnEntity(location, target, source);
        if (projectile != null) {
            projectile.setMetadata("projectile", new FixedMetadataValue(CubesCocktail.getInstance(),
                    true));
            projectile.setGravity(false);
        }
        return projectile;
    }

    private boolean projectileHitBlock() {
        if (!location.getDirection().isNormalized()) {
            return location.getBlock().getType().isSolid();
        }
        RayTraceResult result = oldLocation.getWorld().rayTraceBlocks(oldLocation, location.getDirection(),
                location.distance(oldLocation));
        if (result != null) {
            Vector v = result.getHitPosition();
            location.set(v.getX(), v.getY(), v.getZ()).add(location.getDirection().multiply(.00001));
            if (result.getHitBlockFace() != null) {
                source.getCaster().setMetadata("hitFace", new FixedMetadataValue(CubesCocktail.getInstance(),
                        result.getHitBlockFace().name()));
            }
            return true;
        }
        return false;
    }

    private List<LivingEntity> getHitEntities() {
        if (hitBoxSize == 0) {
            return Lists.newArrayList();
        }
        try {
            return location.getWorld().getNearbyEntities(location, hitBoxSize, hitBoxSize, hitBoxSize,
                            entity -> entity instanceof LivingEntity && !entity.equals(source.getCaster())
                                    && !isProjectile(entity)).stream().map(entity -> (LivingEntity) entity).
                    collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return Lists.newArrayList();
        }
    }

    @NotNull
    protected Vector getSpreadedInitialDirection(Location clone, int projectileSpread) {
        Vector initialDirection = clone.getDirection();
        double spread = Math.toRadians(projectileSpread);
        if (spread != 0) {
            initialDirection.rotateAroundX((Math.random() - 0.5) * spread);
            initialDirection.rotateAroundY((Math.random() - 0.5) * spread);
            initialDirection.rotateAroundZ((Math.random() - 0.5) * spread);
        }
        return initialDirection;
    }
}
