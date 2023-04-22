package com.github.zamponimarco.cubescocktail.function;

import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.jummes.libs.model.NamedModel;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public abstract class AbstractFunction extends NamedModel {

    public AbstractFunction(String name) {
        super(name);
    }

    public AbstractFunction(Map<String, Object> map) {
        super(map);
    }

    protected ItemStack getCorruptedItem() {
        return ItemUtils.getNamedItem(new ItemStack(Material.BARRIER), MessageUtils.color("&4&lCorrupted"), Lists.
                newArrayList(MessageUtils.color("&cThe display item is corrupted"),
                        MessageUtils.color("&cTry to set it again.")));
    }

    @Override
    protected boolean isAlreadyPresent(String name) {
        return CubesCocktail.getInstance().getFunctionManager().getFunctionByName(name) != null;
    }

    abstract public Function getByName(String name);

    abstract public List<Function> getExecutableFunctions();

}
