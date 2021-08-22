package com.github.zamponimarco.cubescocktail.action.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lConsume Item", description = "gui.action.entity.consume-item.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjFiYjJjM2JkNjVjZGQ4NGE4MDRlY2E5OGI3YTQ2NzM1ZjAxZTZhMWM5MTk5ZDY2NjE2NjNkYmRiNGY1YjQifX19")
@Getter
@Setter
public class ConsumeItemAction extends EntityAction {

    private static final NumericValue AMOUNT_DEFAULT = new NumericValue(1);

    private static final String AMOUNT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";

    @Serializable(displayItem = "getFlatItem", description = "gui.action.entity.consume-item.item", additionalDescription = {"gui.additional-tooltips.item"})
    private ItemStackWrapper item;

    @Serializable(headTexture = AMOUNT_HEAD, description = "gui.action.entity.consume-item.amount", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Optional(defaultValue = "AMOUNT_DEFAULT")
    @Serializable.Number(minValue = 1, scale = 1)
    private NumericValue amount;

    @SneakyThrows
    public ConsumeItemAction() {
        this(TARGET_DEFAULT, new ItemStackWrapper(), AMOUNT_DEFAULT.clone());
    }

    public ConsumeItemAction(boolean target, ItemStackWrapper item, NumericValue amount) {
        super(target);
        this.item = item;
        this.amount = amount;
    }

    public ConsumeItemAction(Map<String, Object> map) {
        super(map);
        this.item = (ItemStackWrapper) map.get("item");
        this.amount = (NumericValue) map.getOrDefault("amount", AMOUNT_DEFAULT.clone());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, Map<String, Object> map) {
        LivingEntity e = getEntity(target, source);
        if (e instanceof InventoryHolder) {
            int toRemove = amount.getRealValue(target, source).intValue();
            Inventory inventory = ((InventoryHolder) e).getInventory();
            List<ItemStack> toConsume = Arrays.stream(inventory.getStorageContents()).filter(item -> ItemUtils.isSimilar(item,
                    this.item.getWrapped())).collect(Collectors.toList());
            while (toRemove > 0 && !toConsume.isEmpty()) {
                ItemStack consumed = toConsume.remove(0);
                if (consumed != null) {
                    int removed = Math.min(toRemove, consumed.getAmount());
                    consumed.setAmount(consumed.getAmount() - removed);
                    toRemove -= removed;
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAILURE;
    }

    public ItemStack getFlatItem() {
        return item.getWrapped().clone();
    }

    @Override
    public Action clone() {
        return new ConsumeItemAction(target, new ItemStackWrapper(item.getWrapped().clone(), true), amount.clone());
    }

    @Override
    public String getName() {
        return "&6&lConsume item";
    }
}
