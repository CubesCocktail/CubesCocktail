package com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.player;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.MaxHealthPlaceholder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lHunger Placeholder", description = "gui.placeholder.double.entity.player.hunger.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y5ZWQxMjM0NjYyYjg5OWNhMDhhMGRmZTc2YTk4ZTRhMjdkYTVkM2Q3NDY1NDM0ZjhiMzM1MGZmYjY1Njc3ZiJ9fX0=")
public class HungerPlaceholder extends PlayerNumericPlaceholder {

    public HungerPlaceholder() {
        this(TARGET_DEFAULT);
    }

    public HungerPlaceholder(boolean target) {
        super(target);
    }

    public static HungerPlaceholder deserialize(Map<String, Object> map) {
        boolean target = (boolean) map.getOrDefault("target", TARGET_DEFAULT);
        return new HungerPlaceholder(target);
    }

    @Override
    public NumericPlaceholder clone() {
        return new MaxHealthPlaceholder(target);
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        LivingEntity entity = getEntity(target, source);
        if (!(entity instanceof Player)) {
            return 0.0;
        }

        return (double) ((Player) entity).getFoodLevel();
    }

    @Override
    public String getName() {
        return String.format("%s Hunger", target ? "Target" : "Source");
    }
}
