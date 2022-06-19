package com.github.zamponimarco.cubescocktail.slot;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lNumbered Slot", description = "gui.slot.numbered.description",
        condition = "additionalSlotsEnabled", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ5YjY4OTE1YjE0NzJkODllNWUzYTliYTZjOTM1YWFlNjAzZDEyYzE0NTRmMzgyMjgyNWY0M2RmZThhMmNhYyJ9fX0")
@Getter
@ToString
public class NumberedSlot extends Slot {

    @Serializable(headTexture = SLOTS_HEAD, description = "gui.slot.slot", fromList = "getSlots",
            fromListMapper = "slotsMapper")
    @EqualsAndHashCode.Include
    private int slot;

    public NumberedSlot() {
        this(additionalSlots.iterator().next());
    }

    public NumberedSlot(int slot) {
        this.slot = slot;
    }

    public NumberedSlot(Map<String, Object> map) {
        super(map);
        this.slot = (int) map.getOrDefault("slot", additionalSlots.iterator().next());
    }

    @Override
    public ItemStack getItem(LivingEntity e) {
        if (e instanceof Player){
            return ((Player) e).getInventory().getItem(this.slot);
        }
        return null;
    }

    public static List<Object> getSlots(ModelPath<?> path) {
        return new ArrayList<>(additionalSlots);
    }

    public static Function<Object, ItemStack> slotsMapper() {
        return obj -> {
            int slot = (int) obj;
            return getItem(slot);
        };
    }

    private static ItemStack getItem(int slot) {
        ItemStack item = ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(SLOTS_HEAD),
                MessageUtils.color("&6&lSlot: &c" + slot), Lists.newArrayList());
        item.setAmount(slot);
        return item;
    }

    public static boolean additionalSlotsEnabled(ModelPath path) {
        return !additionalSlots.isEmpty();
    }

    @Override
    public boolean isSlot(Inventory inventory, int slot) {
        return inventory instanceof PlayerInventory && slot == this.slot;
    }

    @Override
    public ItemStack getGUIItem() {
        return getItem(slot);
    }

    @Override
    public Slot clone() {
        return new NumberedSlot(slot);
    }
}