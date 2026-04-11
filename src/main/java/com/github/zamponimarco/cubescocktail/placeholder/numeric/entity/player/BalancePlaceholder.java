package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.player;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lBalance Placeholder", condition = "vaultEnabled", description = "gui.placeholder.double.entity.player.balance.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWUyNWRiZTQ3NjY3ZDBjZTIzMWJhYTIyM2RlZTk1M2JiZmM5Njk2MDk3Mjc5ZDcyMzcwM2QyY2MzMzk3NjQ5ZSJ9fX0")
public class BalancePlaceholder extends PlayerNumericPlaceholder {

    public BalancePlaceholder() {
        this(TARGET_DEFAULT);
    }

    public BalancePlaceholder(boolean target) {
        super(target);
    }

    public static BalancePlaceholder deserialize(Map<String, Object> map) {
        boolean target = (boolean) map.getOrDefault("target", TARGET_DEFAULT);
        return new BalancePlaceholder(target);
    }

    public static boolean vaultEnabled(ModelPath path) {
        return CubesCocktail.getInstance().getVaultHook().isEnabled();
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        if (!CubesCocktail.getInstance().getVaultHook().isEnabled()) {
            return 0.0;
        }

        LivingEntity entity = getEntity(target, source);
        if (!(entity instanceof Player)) {
            return 0.0;
        }

        return CubesCocktail.getInstance().getVaultHook().getBalance((Player) entity);
    }

    @Override
    public String getName() {
        return String.format("%s Balance", target ? "Target" : "Source");
    }

    @Override
    public NumericPlaceholder clone() {
        return new BalancePlaceholder(target);
    }
}
