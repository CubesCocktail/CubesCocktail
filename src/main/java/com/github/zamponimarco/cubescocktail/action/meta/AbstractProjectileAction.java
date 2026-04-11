package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.github.zamponimarco.cubescocktail.entity.Entity;
import com.github.zamponimarco.cubescocktail.entity.NoEntity;
import com.github.zamponimarco.cubescocktail.function.Function;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractProjectileAction extends MetaAction {

    protected static final NumericValue INITIAL_DEFAULT = new NumericValue(10.0);
    protected static final NumericValue GRAVITY_DEFAULT = new NumericValue(0.1);
    protected static final NumericValue HIT_BOX_SIZE_DEFAULT = new NumericValue(0.5);
    protected static final NumericValue MAX_LIFE_DEFAULT = new NumericValue(100.0);


    @Serializable(headTexture = Textures.SPEED_HEAD, description = "gui.action.meta.projectile.initial-speed", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0)
    @Serializable.Optional(defaultValue = "INITIAL_DEFAULT")
    protected NumericValue initialSpeed;

    @Serializable(headTexture = Textures.GRAVITY_HEAD, description = "gui.action.meta.projectile.gravity", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Optional(defaultValue = "GRAVITY_DEFAULT")
    protected NumericValue gravity;

    @Serializable(headTexture = Textures.START_HIT_HEAD, description = "gui.action.meta.projectile.start-actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    protected List<Action> onStartActions;

    @Serializable(headTexture = Textures.EFFECT_HEAD, description = "gui.action.meta.projectile.entity-hit-actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    protected List<Action> onEntityHitActions;

    @Serializable(headTexture = Textures.BLOCK_HIT_HEAD, description = "gui.action.meta.projectile.block-hit-actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    protected List<Action> onBlockHitActions;

    @Serializable(headTexture = Textures.TICK_HEAD, description = "gui.action.meta.projectile.projectile-tick-actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    protected List<Action> onProjectileTickActions;

    @Serializable(headTexture = Textures.ENTITY_HEAD, description = "gui.action.meta.projectile.entity", additionalDescription = {"gui.additional-tooltips.recreate"})
    protected Entity entity;

    @Serializable(headTexture = Textures.HIT_BOX_HEAD, description = "gui.action.meta.projectile.hit-box", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0)
    @Serializable.Optional(defaultValue = "HIT_BOX_SIZE_DEFAULT")
    protected NumericValue hitBoxSize;

    @Serializable(headTexture = Textures.MAX_LIFE_HEAD, description = "gui.action.meta.projectile.max-distance", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, scale = 1)
    @Serializable.Optional(defaultValue = "MAX_LIFE_DEFAULT")
    protected NumericValue maxLife;

    public AbstractProjectileAction(boolean target, NumericValue initialSpeed, NumericValue gravity,
                                    List<Action> onStartActions, List<Action> onEntityHitActions, List<Action> onBlockHitActions,
                                    List<Action> onProjectileTickActions, Entity entity, NumericValue hitBoxSize,
                                    NumericValue maxLife) {
        super(target);
        this.initialSpeed = initialSpeed;
        this.gravity = gravity;
        this.onStartActions = onStartActions;
        this.onEntityHitActions = onEntityHitActions;
        this.onBlockHitActions = onBlockHitActions;
        this.onProjectileTickActions = onProjectileTickActions;
        this.entity = entity;
        this.hitBoxSize = hitBoxSize;
        this.maxLife = maxLife;
    }

    public AbstractProjectileAction(Map<String, Object> map) {
        super(map);
        this.onStartActions = (List<Action>) map.getOrDefault("onStartActions", Lists.newArrayList());
        this.onStartActions.removeIf(Objects::isNull);
        this.onEntityHitActions = (List<Action>) map.getOrDefault("onEntityHitActions", Lists.newArrayList());
        this.onEntityHitActions.removeIf(Objects::isNull);
        this.onBlockHitActions = (List<Action>) map.getOrDefault("onBlockHitActions", Lists.newArrayList());
        this.onBlockHitActions.removeIf(Objects::isNull);
        this.onProjectileTickActions = (List<Action>) map.getOrDefault("onProjectileTickActions", Lists.newArrayList());
        this.onProjectileTickActions.removeIf(Objects::isNull);
        this.entity = (Entity) map.getOrDefault("entity", new NoEntity());
        this.initialSpeed = (NumericValue) map.getOrDefault("initialSpeed", INITIAL_DEFAULT.clone());
        this.gravity = (NumericValue) map.getOrDefault("gravity", GRAVITY_DEFAULT.clone());
        this.hitBoxSize = (NumericValue) map.getOrDefault("hitBoxSize", HIT_BOX_SIZE_DEFAULT.clone());

        NumericValue maxDistance = (NumericValue) map.get("maxDistance");

        if (maxDistance == null) {
            this.maxLife = (NumericValue) map.getOrDefault("maxLife", MAX_LIFE_DEFAULT.clone());
        } else {
            this.maxLife = maxDistance;
        }
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }

    protected Location getRightSide(Location location) {
        float angle = location.getYaw() / 60;
        return location.clone().subtract(new Vector(Math.cos(angle), 0, Math.sin(angle)).normalize().multiply(0.5));
    }

    protected Location getLeftSide(Location location) {
        float angle = location.getYaw() / 60;
        return location.clone().add(new Vector(Math.cos(angle), 0, Math.sin(angle)).normalize().multiply(0.5));
    }

    @Override
    public Set<Function> getUsedSavedSkills() {
        return Stream.concat(Stream.concat(Stream.concat(onBlockHitActions.stream(), onEntityHitActions.stream()),
                onProjectileTickActions.stream()), onStartActions.stream()).reduce(Sets.newHashSet(), (list, action) -> {
            list.addAll(action.getUsedSavedSkills());
            return list;
        }, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
    }

}
