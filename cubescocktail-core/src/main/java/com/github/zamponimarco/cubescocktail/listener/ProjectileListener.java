package com.github.zamponimarco.cubescocktail.listener;

import com.github.zamponimarco.cubescocktail.projectile.Projectile;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class ProjectileListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if (Projectile.isProjectile(entity)) {
            e.getDrops().clear();
            e.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onFallingBlockLand(EntityChangeBlockEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType() == EntityType.FALLING_BLOCK) {
            if (Projectile.isProjectile(entity)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHopperCatch(InventoryPickupItemEvent e) {
        Item entity = e.getItem();
        if (Projectile.isProjectile(entity)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (Projectile.isProjectile(e.getDamager()) || Projectile.isProjectile(e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (Projectile.isProjectile(e.getEntity())) {
            e.setCancelled(true);
        }
    }

}
