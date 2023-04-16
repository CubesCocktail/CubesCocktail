package com.github.zamponimarco.cubescocktail.placeholder.bool;

import com.github.jummes.libs.annotation.Enumerable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lIs Sprinting Placeholder", description = "gui.placeholder.boolean.is-sprinting.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzU3Mzk0YzEzNTgwM2FmYTliOTVjODBhYmVhOGVhNjlhZTc0MGE4NGZkOWMwNGZlMmU0ZjYzZmRhM2M0MjQwMiJ9fX0=")
@Getter
@Setter
public class IsSprintingPlaceholder extends PlayerPropertyBooleanPlaceholder {
    public IsSprintingPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public IsSprintingPlaceholder(boolean target) {
        super(target);
    }

    public IsSprintingPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public BooleanPlaceholder clone() {
        return new IsSprintingPlaceholder(target);
    }

    @Override
    public String getName() {
        return String.format("%s Is Sprinting", target ? "Target" : "Source");
    }

    @Override
    protected Boolean getPropertyValue(Player p) {
        return p.isSprinting();
    }
}
