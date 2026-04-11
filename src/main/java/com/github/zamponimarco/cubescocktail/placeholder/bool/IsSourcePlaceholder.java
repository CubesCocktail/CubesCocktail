package com.github.zamponimarco.cubescocktail.placeholder.bool;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lSource placeholder", description = "gui.placeholder.boolean.source.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM2Mzc5NjFmODQ1MWE1M2I2N2QyNTMxMmQzNTBjNjIwZjMyYjVmNjA4YmQ2YWRlMDY2MzdiZTE3MTJmMzY0ZSJ9fX0=")
@Getter
@Setter
public class IsSourcePlaceholder extends BooleanPlaceholder {

    public IsSourcePlaceholder() {
        this(TARGET_DEFAULT);
    }

    public IsSourcePlaceholder(boolean target) {
        super(target);
    }

    public IsSourcePlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Boolean computePlaceholder(ActionTarget target, ActionSource source) {
        return target.getLocation().equals(source.getLocation());
    }

    @Override
    public String getName() {
        return "IsSource";
    }

    @Override
    public BooleanPlaceholder clone() {
        return new IsSourcePlaceholder();
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }
}
