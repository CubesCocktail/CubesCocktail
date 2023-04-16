package com.github.zamponimarco.cubescocktail.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Map;

@AllArgsConstructor
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lItem entity", description = "gui.entity.item-entity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTJiMzViZGE1ZWJkZjEzNWY0ZTcxY2U0OTcyNmZiZWM1NzM5ZjBhZGVkZjAxYzUxOWUyYWVhN2Y1MTk1MWVhMiJ9fX0=")
@Getter
@Setter
public class ItemEntity extends Entity {

    private final static NumericValue DEFAULT_PICKUP = new NumericValue(37687);
    private final static boolean DEFAULT_GRAVITY = false;

    private static final String PICKUP_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlOGNmZjc1ZjdkNDMzMjYwYWYxZWNiMmY3NzNiNGJjMzgxZDk1MWRlNGUyZWI2NjE0MjM3NzlhNTkwZTcyYiJ9fX0=";
    private static final String GRAVITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTJiYWViNGEzNWRhOGE4NWQxNGJkY2NmNzE4NGY1NTQ1MDg4Zjk1NGRhNTUxNDRmMjM1YzI5ODNmZGI4ZTA1YiJ9fX0=";

    @Serializable(displayItem = "getFlatItem", description = "gui.entity.item-entity.item",
            additionalDescription = {"gui.additional-tooltips.item"})
    private ItemStackWrapper item;

    @Serializable(headTexture = GRAVITY_HEAD, description = "gui.entity.item-entity.gravity")
    @Serializable.Optional(defaultValue = "DEFAULT_GRAVITY")
    private boolean gravity;

    @Serializable(headTexture = PICKUP_HEAD, description = "gui.entity.item-entity.pickup", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, maxValue = 37687, scale = 1)
    @Serializable.Optional(defaultValue = "DEFAULT_PICKUP")
    private NumericValue pickupDelay;

    public ItemEntity() {
        this(new ItemStackWrapper(), DEFAULT_GRAVITY, DEFAULT_PICKUP.clone());
    }

    public ItemEntity(Map<String, Object> map) {
        super(map);
        this.item = (ItemStackWrapper) map.get("item");
        this.gravity = (boolean) map.getOrDefault("gravity", DEFAULT_GRAVITY);
        this.pickupDelay = (NumericValue) map.getOrDefault("pickupDelay", DEFAULT_PICKUP.clone());
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location l, ActionTarget target, ActionSource source) {
        Item item = l.getWorld().dropItem(l, this.item.getWrapped());
        item.setVelocity(new Vector());
        item.setGravity(gravity);
        item.setPickupDelay(pickupDelay.getRealValue(target, source).intValue());
        return item;
    }

    public ItemStack getFlatItem() {
        return item.getWrapped().clone();
    }

    @Override
    public Entity clone() {
        return new ItemEntity(new ItemStackWrapper(item.getWrapped().clone(), true), gravity,
                pickupDelay.clone());
    }

    @Override
    public EntityType getType() {
        return EntityType.DROPPED_ITEM;
    }
}
