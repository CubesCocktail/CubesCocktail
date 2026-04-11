package com.github.zamponimarco.cubescocktail.function;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class Function extends AbstractFunction implements Cloneable {

    private static final List<Action> ACTIONS_DEFAULT = Lists.newArrayList();


    private static int counter = 1;

    @Serializable(headTexture = Textures.ACTIONS_HEAD, description = "gui.function.actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    private List<Action> actions;
    @Serializable
    private ItemStackWrapper item;

    public Function() {
        super(nextAvailableName());
        this.actions = Lists.newArrayList();
        this.item = new ItemStackWrapper();
    }

    public Function(String name, List<Action> actions, ItemStackWrapper item) {
        super(name);
        this.actions = actions;
        this.item = item;
        counter++;
    }

    public Function(Map<String, Object> map) {
        super(map);
        this.actions = (List<Action>) map.getOrDefault("actions", Lists.newArrayList());
        this.actions.removeIf(Objects::isNull);
        this.item = (ItemStackWrapper) map.getOrDefault("item", new ItemStackWrapper());
        counter++;
    }

    private static String nextAvailableName() {
        String name;
        do {
            name = "function" + counter;
            counter++;
        } while (CubesCocktail.getInstance().getFunctionManager().getFunctionByName(name) != null);
        return name;
    }

    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(item.getWrapped(), MessageUtils.color("&6&lName: &c" + name),
                Libs.getLocale().getList("gui.function.description"));
    }

    @Override
    public void onModify(Field field) {
        if (field != null) {
            if (field.getName().equals("name")) {
                CubesCocktail.getInstance().getAddonManager().getLoadedAddons().forEach(addon -> addon.
                        renameFunction(getOldName(), getName()));
            }
        }
    }

    @Override
    public Function getByName(String name) {
        return this.name.equals(name) ? this : null;
    }

    @Override
    public List<Function> getExecutableFunctions() {
        return Lists.newArrayList(this);
    }

    @Override
    public Function clone() {
        return new Function(nextAvailableName(), actions.stream().map(Action::clone).collect(Collectors.toList()),
                item.clone());
    }
}
