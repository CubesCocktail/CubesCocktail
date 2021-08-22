package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.cooldown.Cooldownable;
import com.github.zamponimarco.cubescocktail.key.Key;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lCooldown Placeholder", description = "gui.placeholder.double.entity.cooldown.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlOGNmZjc1ZjdkNDMzMjYwYWYxZWNiMmY3NzNiNGJjMzgxZDk1MWRlNGUyZWI2NjE0MjM3NzlhNTkwZTcyYiJ9fX0=")
public class CooldownPlaceholder extends EntityNumericPlaceholder {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGViNDYxMzU0NDBjMmUwNDJhZWY4NzUyNGMzMTkyMDRhZjcwMWQzNWFiY2U3NDc4YWY2NGU4ZWY4ZTU5MGQzNSJ9fX0=";

    @Serializable(headTexture = HEAD, fromList = "getColdownables", fromListMapper = "cooldownablesMapper", description = "gui.placeholder.double.entity.cooldown.key")
    private Key key;

    public CooldownPlaceholder() {
        super(TARGET_DEFAULT);
        this.key = Cooldownable.cooldownableObjects.stream().findFirst().orElse(null);
    }

    public CooldownPlaceholder(boolean target, Key key) {
        super(target);
        this.key = key;
    }

    public CooldownPlaceholder(Map<String, Object> map) {
        super(map);
        this.key = (Key) map.getOrDefault("key", Cooldownable.cooldownableObjects.stream().findFirst().
                orElse(null));
    }

    public static List<Key> getColdownables(ModelPath<?> path) {
        return Cooldownable.cooldownableObjects;
    }

    public static Function<Object, ItemStack> cooldownablesMapper() {
        return obj -> {
            Key key = (Key) obj;
            return key.getGUIItem();
        };
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);
        if (entity == null || key == null) {
            return 0.0;
        }

        return (double) CubesCocktail.getInstance().getCooldownManager().getCooldown(entity, key);
    }

    @Override
    public String getName() {
        return String.format("%s Cooldown for %s", target ? "Target" : "Source", key.getName());
    }

    @Override
    public NumericPlaceholder clone() {
        return new CooldownPlaceholder(target, key.clone());
    }
}
