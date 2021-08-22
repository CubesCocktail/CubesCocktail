package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
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

    private static final String INITIAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTc2OWUyYzEzNGVlNWZjNmRhZWZlNDEyZTRhZjNkNTdkZjlkYmIzY2FhY2Q4ZTM2ZTU5OTk3OWVjMWFjNCJ9fX0=";
    private static final String GRAVITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY4ZGZiYzk1YWRiNGY2NDhjMzYxNjRhMTVkNjhlZjVmOWM3Njk3ZDg2Zjg3MjEzYzdkN2E2NDU1NzdhYTY2In19fQ==";
    private static final String ENTITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNjOGFhM2ZkZTI5NWZhOWY5YzI3ZjczNGJkYmFiMTFkMzNhMmU0M2U4NTVhY2NkNzQ2NTM1MjM3NzQxM2IifX19";
    private static final String ENTITY_HIT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVlMTE4ZWRkYWVlMGRmYjJjYmMyYzNkNTljMTNhNDFhN2Q2OGNjZTk0NWU0MjE2N2FhMWRjYjhkMDY3MDUxNyJ9fX0=";
    private static final String START_HIT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgzODVhNWE0Njk4MjFiOGIzM2U0N2E1YjVjNDJhZWE1OTY2MzQ2NTQ2OTM4OGExYTRkNGU1MjNlNWE4ZGRkMiJ9fX0=";
    private static final String BLOCK_HIT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODhiNzY3YzhhMWVhOGU0MDRiM2NjYTg1MzQ5ZjY1M2I1N2IwYzNmNDY0MjdmYmVjZWFjY2YzNjAyYmMyOWUifX19";
    private static final String TICK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FhMjVhZTFiOGU2ZTgxY2FkMTU3NTdjMzk3YmYwYzk5M2E1ZDg3NmQ5N2NiZWFlNGEyMGYyNDMzYzMyM2EifX19";
    private static final String HIT_BOX_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjMzYzBiYjM3ZWJlMTE5M2VlNDYxODEwMzQ2MGE3ZjEyOTI3N2E4YzdmZDA4MWI2YWVkYjM0YTkyYmQ1In19fQ==";
    private static final String MAX_LIFE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI0NjFiNGQ2YWIxYzE0MThlZWFiZDQ2N2Q4OTNmMGU4OWEyMWE0MjM2OTFiN2UxZjYwNWQ2Njk3ZDBhOGU1MSJ9fX0=";

    @Serializable(headTexture = INITIAL_HEAD, description = "gui.action.meta.projectile.initial-speed", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0)
    @Serializable.Optional(defaultValue = "INITIAL_DEFAULT")
    protected NumericValue initialSpeed;

    @Serializable(headTexture = GRAVITY_HEAD, description = "gui.action.meta.projectile.gravity", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Optional(defaultValue = "GRAVITY_DEFAULT")
    protected NumericValue gravity;

    @Serializable(headTexture = START_HIT_HEAD, description = "gui.action.meta.projectile.start-actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    protected List<Action> onStartActions;

    @Serializable(headTexture = ENTITY_HIT_HEAD, description = "gui.action.meta.projectile.entity-hit-actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    protected List<Action> onEntityHitActions;

    @Serializable(headTexture = BLOCK_HIT_HEAD, description = "gui.action.meta.projectile.block-hit-actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    protected List<Action> onBlockHitActions;

    @Serializable(headTexture = TICK_HEAD, description = "gui.action.meta.projectile.projectile-tick-actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    protected List<Action> onProjectileTickActions;

    @Serializable(headTexture = ENTITY_HEAD, description = "gui.action.meta.projectile.entity", additionalDescription = {"gui.additional-tooltips.recreate"})
    protected Entity entity;

    @Serializable(headTexture = HIT_BOX_HEAD, description = "gui.action.meta.projectile.hit-box", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0)
    @Serializable.Optional(defaultValue = "HIT_BOX_SIZE_DEFAULT")
    protected NumericValue hitBoxSize;

    @Serializable(headTexture = MAX_LIFE_HEAD, description = "gui.action.meta.projectile.max-distance", additionalDescription = {"gui.additional-tooltips.value"})
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

    protected Location getRightSide(Location location, double distance) {
        float angle = location.getYaw() / 60;
        return location.clone().subtract(new Vector(Math.cos(angle), 0, Math.sin(angle)).normalize().multiply(distance));
    }

    protected Location getLeftSide(Location location, double distance) {
        float angle = location.getYaw() / 60;
        return location.clone().add(new Vector(Math.cos(angle), 0, Math.sin(angle)).normalize().multiply(distance));
    }

    @Override
    public Set<Function> getUsedSavedSkills() {
        return Stream.concat(Stream.concat(onBlockHitActions.stream(), onEntityHitActions.stream()),
                onProjectileTickActions.stream()).reduce(Sets.newHashSet(), (list, action) -> {
            list.addAll(action.getUsedSavedSkills());
            return list;
        }, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
    }

}
