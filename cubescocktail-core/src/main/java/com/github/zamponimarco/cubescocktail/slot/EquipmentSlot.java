package com.github.zamponimarco.cubescocktail.slot;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.util.MapperUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lEquipment Slot", description = "gui.slot.armor.description",
        headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ5YjY4OTE1YjE0NzJkODllNWUzYTliYTZjOTM1YWFlNjAzZDEyYzE0NTRmMzgyMjgyNWY0M2RmZThhMmNhYyJ9fX0")
@ToString
@Getter
@Setter
public class EquipmentSlot extends Slot {

    @Serializable(headTexture = SLOTS_HEAD, description = "gui.slot.slot", stringValue = true)
    @EqualsAndHashCode.Include
    private org.bukkit.inventory.EquipmentSlot slot;

    public EquipmentSlot() {
        this(org.bukkit.inventory.EquipmentSlot.HAND);
    }

    public EquipmentSlot(org.bukkit.inventory.EquipmentSlot slot) {
        this.slot = slot;
    }

    public EquipmentSlot(Map<String, Object> map) {
        super(map);
        this.slot = org.bukkit.inventory.EquipmentSlot.valueOf((String) map.getOrDefault("slot", "HAND"));
    }

    @Override
    public ItemStack getItem(LivingEntity e) {
        EntityEquipment entityEquipment = e.getEquipment();
        if (entityEquipment == null)
            return null;

        return e.getEquipment().getItem(slot);
    }

    @Override
    public boolean isSlot(Inventory inventory, int slot) {
        if (inventory instanceof PlayerInventory) {
            return switch (this.slot) {
                case HAND -> slot == ((PlayerInventory) inventory).getHeldItemSlot();
                case FEET -> slot == inventory.getSize() - 5;
                case LEGS -> slot == inventory.getSize() - 4;
                case CHEST -> slot == inventory.getSize() - 3;
                case HEAD -> slot == inventory.getSize() - 2;
                case OFF_HAND -> slot == inventory.getSize() - 1;
            };
        }
        return false;
    }

    @Override
    public ItemStack getGUIItem() {
        return MapperUtils.getEquipmentSlotMapper().apply(slot);
    }

    @Override
    public Slot clone() {
        return new EquipmentSlot(slot);
    }
}