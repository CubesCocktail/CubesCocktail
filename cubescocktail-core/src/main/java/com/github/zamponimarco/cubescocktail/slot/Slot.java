package com.github.zamponimarco.cubescocktail.slot;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import com.google.common.collect.Sets;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

@Enumerable.Parent(classArray = {EquipmentSlot.class, NumberedSlot.class})
@Enumerable.Displayable
public abstract class Slot implements Model, Cloneable {

    public static final List<Slot> DEFAULT_SLOTS = Arrays.stream(org.bukkit.inventory.EquipmentSlot.values()).
            map(EquipmentSlot::new).collect(Collectors.toList());

    public static Set<Integer> additionalSlots = Sets.newHashSet();
    public static List<Slot> slots = new ArrayList<>(DEFAULT_SLOTS);

    protected static final String SLOTS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ5YjY4OTE1YjE0NzJkODllNWUzYTliYTZjOTM1YWFlNjAzZDEyYzE0NTRmMzgyMjgyNWY0M2RmZThhMmNhYyJ9fX0=";

    public Slot() {

    }

    public Slot(Map<String, Object> map) {

    }

    public static Slot getSlotFromInventory(Inventory inventory, int slot) {
        return slots.stream().filter(s -> s.isSlot(inventory, slot)).findFirst().orElse(null);
    }

    public abstract ItemStack getItem(LivingEntity e);

    public abstract boolean isSlot(Inventory inventory, int slot);

    public abstract Slot clone();

}