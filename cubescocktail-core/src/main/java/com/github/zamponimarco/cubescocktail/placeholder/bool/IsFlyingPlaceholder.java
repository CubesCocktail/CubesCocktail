package com.github.zamponimarco.cubescocktail.placeholder.bool;

import com.github.jummes.libs.annotation.Enumerable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lIs Flying Placeholder", description = "gui.placeholder.boolean.is-flying.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmIxZDYxOTI4NDJlMTkyODg3Y2I0YjMzNjI0NzVlYzdhYTlmZWEzY2JkMWMyMzMyMjhkNWU0Y2YzZmNkYWYzIn19fQ==")
@Getter
@Setter
public class IsFlyingPlaceholder extends PlayerPropertyBooleanPlaceholder {
    public IsFlyingPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public IsFlyingPlaceholder(boolean target) {
        super(target);
    }

    public IsFlyingPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public BooleanPlaceholder clone() {
        return new IsFlyingPlaceholder(target);
    }

    @Override
    public String getName() {
        return String.format("%s Is Flying", target ? "Target" : "Source");
    }

    @Override
    protected Boolean getPropertyValue(Player p) {
        return p.isFlying();
    }
}
