package com.github.zamponimarco.cubescocktail.placeholder;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.block.BlockPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.bool.BooleanPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.material.MaterialPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.string.StringPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Parent(classArray = {NumericPlaceholder.class, BooleanPlaceholder.class, StringPlaceholder.class,
        BlockPlaceholder.class, MaterialPlaceholder.class, VectorPlaceholder.class})
public abstract class Placeholder<S> implements Model, Cloneable {

    protected static final String TARGET_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0==";
    protected static final boolean TARGET_DEFAULT = true;
    @Serializable(displayItem = "targetItem", description = "gui.placeholder.target")
    @Serializable.Optional(defaultValue = "TARGET_DEFAULT")
    protected boolean target;

    public Placeholder(boolean target) {
        this.target = target;
    }

    public Placeholder(Map<String, Object> map) {
        this.target = (boolean) map.getOrDefault("target", TARGET_DEFAULT);
    }

    public abstract S computePlaceholder(ActionTarget target, ActionSource source);

    public abstract String getName();

    public ItemStack targetItem() {
        return Libs.getVersionWrapper().skullFromValue(TARGET_HEAD);
    }

    public abstract Placeholder clone();

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(getClass().getAnnotation(Enumerable.Displayable.class).headTexture()),
                MessageUtils.color(String.format("&6&l%s", getName())), Libs.getLocale().getList("gui.placeholder.description"));
    }

}
