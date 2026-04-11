package com.github.zamponimarco.cubescocktail.loot.drop;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import java.util.Collection;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lItem &cdrop", description = "gui.loot.drop.item.description")
@Getter
@Setter
public class ItemDrop extends Drop {


    @Serializable(displayItem = "getDisplayItem", description = "gui.loot.drop.item.item")
    private ItemStackWrapper item;
    @Serializable(headTexture = Textures.AMOUNT_HEAD, description = "gui.loot.drop.item.count", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue count;

    public ItemDrop() {
        this(new AlwaysTrueCondition(), new ItemStackWrapper(), new NumericValue(1));
    }

    public ItemDrop(Map<String, Object> map) {
        super(map);
        this.item = (ItemStackWrapper) map.getOrDefault("item", new ItemStackWrapper());
        this.count = (NumericValue) map.getOrDefault("count", new NumericValue(1));
    }

    public ItemDrop(Condition condition, ItemStackWrapper item, NumericValue count) {
        super(condition);
        this.item = item;
        this.count = count;
    }

    @Override
    public Collection<ItemStack> getLoot(ActionSource source, ActionTarget target, LootContext context) {
        ItemStack toReturn = item.getWrapped().clone();
        toReturn.setAmount(count.getRealValue(target, source).intValue());
        return Lists.newArrayList(toReturn);
    }

    public ItemStack getDisplayItem() {
        return item.getWrapped().clone();
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(getDisplayItem(), MessageUtils.color("&6&lItem: &c" + item.getWrapped().getType().name() +
                " x" + count.getName()), Libs.getLocale().getList("gui.additional-tooltips.delete"));
    }
}
