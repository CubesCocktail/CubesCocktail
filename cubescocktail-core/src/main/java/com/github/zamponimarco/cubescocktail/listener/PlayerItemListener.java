package com.github.zamponimarco.cubescocktail.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.loot.DropTable;
import com.github.zamponimarco.cubescocktail.slot.EquipmentSlot;
import com.github.zamponimarco.cubescocktail.trigger.*;
import com.github.zamponimarco.cubescocktail.util.Utils;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerItemListener implements Listener {

    private static <S extends Trigger> List<TriggerListener> getListeners(Class<S> type, Predicate<S> predicate) {
        return Trigger.getListeners().stream().filter(triggerListener ->
                type.isAssignableFrom(triggerListener.getTrigger().getClass()) &&
                        predicate.test((S) triggerListener.getTrigger())).collect(Collectors.toList());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Map<String, Object> args = new HashMap<>();
        args.put("caster", player);
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            getListeners(RightClickTrigger.class, trigger -> true).forEach(listener ->
                    listener.onTrigger(args));
        } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Objects.equals(e.getHand(), org.bukkit.inventory.EquipmentSlot.HAND))
                getListeners(RightClickTrigger.class, RightClickTrigger::isActivateOnBlock).
                        forEach(listener -> listener.onTrigger(args));
        } else if (e.getAction().equals(Action.LEFT_CLICK_AIR)) {
            getListeners(LeftClickTrigger.class, trigger -> true).forEach(listener ->
                    listener.onTrigger(args));
        } else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            getListeners(LeftClickTrigger.class, LeftClickTrigger::isActivateOnBlock).forEach(listener ->
                    listener.onTrigger(args));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity damaged = (LivingEntity) e.getEntity();
            LivingEntity damager;

            if (e.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity) {
                damager = (LivingEntity) e.getDamager();
            } else if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof
                    LivingEntity) {
                Projectile projectile = (Projectile) e.getDamager();
                damager = (LivingEntity) projectile.getShooter();
            } else {
                return;
            }

            Map<String, Object> args = new HashMap<>();
            args.put("damager", damager);
            args.put("damaged", damaged);
            args.put("damage", e.getDamage());
            args.put("damageCause", e.getCause().name());

            if (e.getEntity().getMetadata("siattack").stream().noneMatch(metadataValue ->
                    Objects.equals(metadataValue.getOwningPlugin(), CubesCocktail.getInstance()))) {
                args.put("caster", damager);
                getListeners(HitEntityTrigger.class, trigger -> true).forEach(listener -> listener.onTrigger(args));
                args.put("caster", damaged);
                getListeners(DamageEntityTrigger.class, trigger -> true).forEach(listener -> listener.onTrigger(args));
            } else {
                e.getEntity().removeMetadata("siattack", CubesCocktail.getInstance());
            }
            if ((boolean) args.getOrDefault("cancelled", false)) {
                e.setCancelled(true);
            }

            double damage = (double) args.get("damage");
            e.setDamage(damage);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        Map<String, Object> args = new HashMap<>();
        args.put("caster", player);
        getListeners(EntitySneakTrigger.class, trigger -> e.isSneaking() == trigger.isOnActivate()).
                forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerSprint(PlayerToggleSprintEvent e) {
        Player player = e.getPlayer();
        Map<String, Object> args = new HashMap<>();
        args.put("caster", player);
        getListeners(EntitySprintTrigger.class, trigger -> e.isSprinting() == trigger.isOnActivate()).
                forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerShoot(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) e.getEntity().getShooter();
            Map<String, Object> args = new HashMap<>();
            args.put("caster", entity);
            getListeners(EntityShootProjectileTrigger.class, trigger -> true).
                    forEach(listener -> listener.onTrigger(args));
            if ((boolean) args.getOrDefault("cancelled", false)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockPlaced(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Map<String, Object> args = new HashMap<>();
        args.put("location", e.getBlock().getLocation().clone().add(.5, .5, .5));
        args.put("caster", player);
        getListeners(BlockPlaceTrigger.class, trigger -> true).
                forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockBroken(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Map<String, Object> args = new HashMap<>();
        args.put("location", e.getBlock().getLocation().clone().add(.5, .5, .5));
        args.put("caster", player);
        getListeners(BlockBreakTrigger.class, trigger -> true).
                forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        Map<String, Object> args = new HashMap<>();
        args.put("location", e.getHook().getLocation());
        args.put("caster", player);
        getListeners(EntityFishTrigger.class, trigger -> trigger.getState().equals(e.getState())).
                forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerConsumeItem(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        Map<String, Object> args = new HashMap<>();
        args.put("caster", player);
        getListeners(EntityItemConsumeTrigger.class, trigger -> true).forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerJump(PlayerJumpEvent e) {
        if (e.getPlayer().getMetadata("jump").stream().anyMatch(metadataValue ->
                Objects.equals(metadataValue.getOwningPlugin(), CubesCocktail.getInstance()))) {
            e.setCancelled(true);
        }

        LivingEntity player = e.getPlayer();
        Map<String, Object> args = new HashMap<>();
        args.put("caster", player);
        getListeners(EntityJumpTrigger.class, trigger -> true).forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onCrossbowLoad(EntityLoadCrossbowEvent e) {
        LivingEntity entity = e.getEntity();
        Map<String, Object> args = new HashMap<>();
        args.put("caster", entity);
        getListeners(EntityCrossbowLoadTrigger.class, trigger -> true).forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        Map<String, Object> args = new HashMap<>();
        args.put("caster", e.getEntity());
        args.put("killer", e.getEntity().getKiller());
        getListeners(EntityDeathTrigger.class, trigger -> true).forEach(listener -> listener.onTrigger(args));
        if ((boolean) args.getOrDefault("cancelled", false)) {
            e.setCancelled(true);
        }

        DropTable dropTable = (DropTable) Utils.getMetadata(e.getEntity().getMetadata("drops"), null).
                value();

        if (dropTable != null) {
            modifyDrops(e, dropTable);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityInteract(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof LivingEntity) ||
                e.getHand().equals(org.bukkit.inventory.EquipmentSlot.OFF_HAND)) {
            return;
        }
        Map<String, Object> args = new HashMap<>();
        args.put("caster", e.getRightClicked());
        args.put("interactor", e.getPlayer());
        getListeners(EntityInteractedTrigger.class, trigger -> true).forEach(listener -> listener.onTrigger(args));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerChangeArmor(PlayerArmorChangeEvent e) {
        Player entity = e.getPlayer();
        executeEquipSkill(entity, e.getOldItem(), e.getNewItem(), new EquipmentSlot(
                org.bukkit.inventory.EquipmentSlot.valueOf(e.getSlotType().name())));
    }


    private void executeEquipSkill(LivingEntity entity, ItemStack oldItem, ItemStack newItem, EquipmentSlot slot) {
        Map<String, Object> args = new HashMap<>();
        args.put("caster", entity);

        if (!oldItem.getType().equals(Material.AIR)) {
            args.put("item", oldItem);
            getListeners(EntityEquipArmorTrigger.class, trigger -> !trigger.isOnEquip()).
                    forEach(listener -> listener.onTrigger(args));
        }

        if (!newItem.getType().equals(Material.AIR)) {
            args.put("item", newItem);
            getListeners(EntityEquipArmorTrigger.class, EntityEquipArmorTrigger::isOnEquip).
                    forEach(listener -> listener.onTrigger(args));
        }
    }

    private void modifyDrops(EntityDeathEvent e, DropTable dropTable) {
        List<ItemStack> drops = e.getDrops();

        Player killer = e.getEntity().getKiller();

        LootContext.Builder builder = new LootContext.Builder(e.getEntity().getLocation()).lootedEntity(e.getEntity());
        if (killer != null) {
            builder.killer(killer);
            PotionEffect luck = killer.getPotionEffect(PotionEffectType.LUCK);
            if (luck != null) {
                builder.luck(luck.getAmplifier());
            }
            EntityEquipment equipment = killer.getEquipment();
            if (equipment != null) {
                builder.lootingModifier(equipment.getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS));
            }
        }

        if (!dropTable.isDefaultDropsEnabled()) {
            drops.clear();
            e.setDroppedExp(0);
        }

        drops.addAll(dropTable.populateLoot(new Random(), builder.build()));
    }
}
