package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.EntitySource;
import com.github.zamponimarco.cubescocktail.action.source.LocationSource;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.entity.Entity;
import com.github.zamponimarco.cubescocktail.entity.NoEntity;
import com.github.zamponimarco.cubescocktail.projectile.HomingProjectile;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.MainHand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lHoming Projectile", description = "gui.action.meta.projectile.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE1ZmVjNjUxOGE0MWYxNjYxMzFlNjViMTBmNDZmYjg3ZTk3YzQ5MmI0NmRiYzI1ZGUyNjM3NjcyMWZhNjRlMCJ9fX0=")
public class HomingProjectileAction extends AbstractProjectileAction {

    private static final NumericValue PROJECTILE_SPREAD_DEFAULT = new NumericValue(0);
    private static final NumericValue TURN_SPEED_DEFAULT = new NumericValue(0.1);
    private static final boolean SHOOT_FROM_HAND_DEFAULT = false;

    private static final String SHOOT_FROM_HAND_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0=";
    private static final String SPREAD_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTAxMWU3NTE2ZGFhYzVmMjMyMGY2N2I5N2FkNTMwNGY5MDY2Zjg2NDA3YjU4YTUzMGY4MGY4ZmM5N2IzZTg2ZSJ9fX0=";
    private static final String TURN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjNhYzUxNDc3YThkNDI3MTRjMjU2OTI1ZjQzNmYyYzRkNThhOTUyOWNlOGMxNmExZTM3ZjI2NWU1YzQ4OWEifX19";

    @Serializable(headTexture = SHOOT_FROM_HAND_HEAD, description = "gui.action.meta.projectile.shoot-hand")
    @Serializable.Optional(defaultValue = "SHOOT_FROM_HAND_DEFAULT")
    private boolean shootFromHand;

    @Serializable(headTexture = SPREAD_HEAD, description = "gui.action.meta.projectile.spread", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, maxValue = 180, scale = 1)
    @Serializable.Optional(defaultValue = "PROJECTILE_SPREAD_DEFAULT")
    private NumericValue projectileSpread;

    @Serializable(headTexture = TURN_HEAD, description = "gui.action.meta.projectile.turn-speed", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, maxValue = 1)
    @Serializable.Optional(defaultValue = "TURN_SPEED_DEFAULT")
    private NumericValue turnSpeed;

    public HomingProjectileAction() {
        this(TARGET_DEFAULT, INITIAL_DEFAULT.clone(), GRAVITY_DEFAULT.clone(), Lists.newArrayList(), Lists.newArrayList(),
                Lists.newArrayList(), Lists.newArrayList(), new NoEntity(), HIT_BOX_SIZE_DEFAULT.clone(), MAX_LIFE_DEFAULT.clone(),
                SHOOT_FROM_HAND_DEFAULT, PROJECTILE_SPREAD_DEFAULT.clone(), TURN_SPEED_DEFAULT.clone());
    }

    public HomingProjectileAction(boolean target, NumericValue initialSpeed, NumericValue gravity,
                                  List<Action> onStartActions, List<Action> onEntityHitActions, List<Action> onBlockHitActions,
                                  List<Action> onProjectileTickActions, Entity entity, NumericValue hitBoxSize,
                                  NumericValue maxDistance, boolean shootFromHand, NumericValue projectileSpread,
                                  NumericValue turnSpeed) {
        super(target, initialSpeed, gravity, onStartActions, onEntityHitActions, onBlockHitActions, onProjectileTickActions, entity,
                hitBoxSize, maxDistance);
        this.shootFromHand = shootFromHand;
        this.projectileSpread = projectileSpread;
        this.turnSpeed = turnSpeed;
    }

    public HomingProjectileAction(Map<String, Object> map) {
        super(map);
        this.shootFromHand = (boolean) map.getOrDefault("shootFromHand", SHOOT_FROM_HAND_DEFAULT);
        this.projectileSpread = (NumericValue) map.getOrDefault("projectileSpread", PROJECTILE_SPREAD_DEFAULT.clone());
        this.turnSpeed = (NumericValue) map.getOrDefault("turnSpeed", TURN_SPEED_DEFAULT.clone());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, Map<String, Object> map) {
        Location l = null;

        if (!(target instanceof EntityTarget)) {
            return ActionResult.FAILURE;
        }

        if (source.getCaster().equals(((EntityTarget) target).getTarget())) {
            return ActionResult.FAILURE;
        }

        if (source instanceof EntitySource) {
            EntitySource entitySource = (EntitySource) source;
            l = entitySource.getCaster().getEyeLocation().clone();
            if (shootFromHand) {
                if (entitySource.getHand().equals(MainHand.RIGHT)) {
                    l = getRightSide(l, 0.5);
                } else {
                    l = getLeftSide(l, 0.5);
                }
            }
        } else if (source instanceof LocationSource) {
            l = source.getLocation();
        }

        if (l != null) {
            LivingEntity entity = ((EntityTarget) target).getTarget();
            l.setDirection(entity.getEyeLocation().clone().toVector().subtract(l.toVector()).normalize());
            new HomingProjectile(target, source, l, gravity.getRealValue(target, source), initialSpeed.getRealValue(target, source),
                    onStartActions, onEntityHitActions, onBlockHitActions, onProjectileTickActions,
                    this.entity, this.hitBoxSize.getRealValue(target, source), maxLife.getRealValue(target, source).intValue(),
                    projectileSpread.getRealValue(target, source).intValue(), entity, turnSpeed.getRealValue(target, source)).
                    run();
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new HomingProjectileAction(TARGET_DEFAULT, initialSpeed.clone(), gravity.clone(),
                onStartActions.stream().map(Action::clone).collect(Collectors.toList()),
                onEntityHitActions.stream().map(Action::clone).collect(Collectors.toList()),
                onBlockHitActions.stream().map(Action::clone).collect(Collectors.toList()),
                onProjectileTickActions.stream().map(Action::clone).collect(Collectors.toList()),
                entity.clone(), hitBoxSize.clone(), maxLife.clone(), shootFromHand,
                projectileSpread.clone(), turnSpeed.clone());
    }

    @Override
    public String getName() {
        return "&6&lHoming Projectile";
    }

}
