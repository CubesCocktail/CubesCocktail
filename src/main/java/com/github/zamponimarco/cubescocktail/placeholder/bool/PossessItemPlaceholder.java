package com.github.zamponimarco.cubescocktail.placeholder.bool;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lPossess Item Placeholder", description = "gui.placeholder.boolean.possess-item.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVlMmUwOTU5NzEyZGNkMzM1N2NjM2NlYTg1Zjk5YjNmZDgwOTc4NTVjNzU0YjliMTcxZjk2MzUxNDIyNWQifX19")
@Getter
@Setter
public class PossessItemPlaceholder extends BooleanPlaceholder {

    private static final NumericValue AMOUNT_DEFAULT = new NumericValue(1);
    private static final NumericValue SLOT_DEFAULT = new NumericValue(-1);

    private static final String AMOUNT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";
    private static final String SLOT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThiYzhmYTcxNmNhZGQwMDRiODI4Y2IyN2NjMGY2ZjZhZGUzYmU0MTUxMTY4OGNhOWVjZWZmZDE2NDdmYjkifX19";

    @Serializable(displayItem = "getFlatItem", description = "gui.placeholder.boolean.possess-item.item",
            additionalDescription = {"gui.additional-tooltips.item"})
    private ItemStackWrapper item;

    @Serializable(headTexture = AMOUNT_HEAD, description = "gui.placeholder.boolean.possess-item.amount")
    @Serializable.Number(minValue = 1, scale = 1)
    @Serializable.Optional(defaultValue = "AMOUNT_DEFAULT")
    private NumericValue amount;

    @Serializable(headTexture = SLOT_HEAD, description = "gui.placeholder.boolean.possess-item.slot")
    @Serializable.Number(minValue = -1, maxValue = 45, scale = 1)
    @Serializable.Optional(defaultValue = "SLOT_DEFAULT")
    private NumericValue slot;

    public PossessItemPlaceholder() {
        this(TARGET_DEFAULT, new ItemStackWrapper(), AMOUNT_DEFAULT.clone(), SLOT_DEFAULT.clone());
    }

    public PossessItemPlaceholder(boolean target, ItemStackWrapper item, NumericValue amount, NumericValue slot) {
        super(target);
        this.item = item;
        this.amount = amount;
        this.slot = slot;
    }

    public PossessItemPlaceholder(Map<String, Object> map) {
        super(map);
        this.item = (ItemStackWrapper) map.getOrDefault("item", new ItemStackWrapper());
        this.slot = (NumericValue) map.getOrDefault("slot", SLOT_DEFAULT.clone());
        try {
            this.amount = (NumericValue) map.getOrDefault("amount", AMOUNT_DEFAULT.clone());
        } catch (ClassCastException e) {
            this.amount = new NumericValue((int) map.getOrDefault("amount", AMOUNT_DEFAULT));
        }
    }

    @Override
    public Boolean computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = null;
        if (this.target) {
            if (target instanceof EntityTarget) {
                entity = ((EntityTarget) target).getTarget();
            }
        } else {
            entity = source.getCaster();
        }

        if (!(entity instanceof InventoryHolder holder)) {
            return false;
        }

        int slot = this.slot.getRealValue(target, source).intValue();
        int amount = this.amount.getRealValue(target, source).intValue();
        if (slot == -1) {
            return holder.getInventory().containsAtLeast(item.getWrapped(), amount);
        } else {
            ItemStack item = holder.getInventory().getItem(slot);
            return ItemUtils.isSimilar(this.item.getWrapped(), item) && item.getAmount() >= amount;
        }
    }

    @Override
    public String getName() {
        return String.format("%s Possesses %s", target ? "Target" : "Source", item.getWrapped().toString());
    }

    public ItemStack getFlatItem() {
        return item.getWrapped().clone();
    }

    @Override
    public BooleanPlaceholder clone() {
        return new PossessItemPlaceholder(target, new ItemStackWrapper(item.getWrapped(), true),
                amount.clone(), slot.clone());
    }
}
