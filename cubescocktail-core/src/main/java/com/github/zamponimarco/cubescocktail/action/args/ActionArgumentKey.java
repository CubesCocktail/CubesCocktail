package com.github.zamponimarco.cubescocktail.action.args;

import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public interface ActionArgumentKey<Z> {

    ActionArgumentKey<Boolean> CANCELLED = new ActionArgumentKeyImpl<>("cancelled");
    ActionArgumentKey<LivingEntity> CASTER = new ActionArgumentKeyImpl<>("caster");
    ActionArgumentKey<LivingEntity> DAMAGED = new ActionArgumentKeyImpl<>("damaged");
    ActionArgumentKey<LivingEntity> DAMAGER = new ActionArgumentKeyImpl<>("damager");
    ActionArgumentKey<LivingEntity> INTERACTOR = new ActionArgumentKeyImpl<>("interactor");
    ActionArgumentKey<ItemStack> ITEM = new ActionArgumentKeyImpl<>("item");
    ActionArgumentKey<LivingEntity> KILLER = new ActionArgumentKeyImpl<>("killer");
    ActionArgumentKey<Location> LOCATION = new ActionArgumentKeyImpl<>("location");
    ActionArgumentKey<AbstractProjectile> PROJECTILE = new ActionArgumentKeyImpl<>("projectile");
    ActionArgumentKey<LivingEntity> SPAWNED = new ActionArgumentKeyImpl<>("spawned");
    ActionArgumentKey<LivingEntity> SELECTED_ENTITY = new ActionArgumentKeyImpl<>("selected_entity");
    ActionArgumentKey<Location> SELECTED_BLOCK = new ActionArgumentKeyImpl<>("selected_block");

    @Getter
    @EqualsAndHashCode
    class ActionArgumentKeyImpl<Z> implements ActionArgumentKey<Z> {
        private final NamespacedKey key;

        public ActionArgumentKeyImpl(String key) {
            this.key = new NamespacedKey(CubesCocktail.getInstance(), key);
        }
    }

}
