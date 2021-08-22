package com.github.zamponimarco.cubescocktail.action.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.cooldown.Cooldownable;
import com.github.zamponimarco.cubescocktail.key.Key;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lRelative Cooldown", description = "gui.action.entity.relative-teleport.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTU5MzM1ZDkxYmIzOWJhNjg1YmE1NjEyY2NmY2FlOGFhZGEzNDYxYTlkMDc4NjZjZDRlMGIyNjZjODY0ZTEwNyJ9fX0=")
public class SetCooldownAction extends EntityAction {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGViNDYxMzU0NDBjMmUwNDJhZWY4NzUyNGMzMTkyMDRhZjcwMWQzNWFiY2U3NDc4YWY2NGU4ZWY4ZTU5MGQzNSJ9fX0=";

    @Serializable(headTexture = HEAD, fromList = "getColdownables", fromListMapper = "cooldownablesMapper", description = "gui.placeholder.double.entity.cooldown.key")
    private Key key;
    @Serializable(headTexture = HEAD, description = "gui.placeholder.double.entity.cooldown.key")
    private NumericValue cooldown;

    public SetCooldownAction() {
        super(TARGET_DEFAULT);
        this.key = Cooldownable.cooldownableObjects.stream().findFirst().orElse(null);
        this.cooldown = new NumericValue();
    }

    public SetCooldownAction(boolean target, Key key, NumericValue cooldown) {
        super(target);
        this.key = key;
        this.cooldown = cooldown;
    }

    public SetCooldownAction(Map<String, Object> map) {
        super(map);
        this.key = (Key) map.getOrDefault("key", Cooldownable.cooldownableObjects.stream().findFirst().
                orElse(null));
        this.cooldown = (NumericValue) map.getOrDefault("cooldown", new NumericValue());
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
    public ActionResult execute(ActionTarget target, ActionSource source, Map<String, Object> map) {
        LivingEntity entity = getEntity(target, source);
        if (entity == null) {
            return ActionResult.FAILURE;
        }

        CubesCocktail.getInstance().getCooldownManager().addCooldown(entity, key,
                cooldown.getRealValue(target, source).intValue());

        return ActionResult.SUCCESS;
    }

    @Override
    public String getName() {
        return String.format("&6&lSet Cooldown: &c%s", cooldown.getName());
    }

    @Override
    public Action clone() {
        return new SetCooldownAction(target, key.clone(), cooldown.clone());
    }
}
