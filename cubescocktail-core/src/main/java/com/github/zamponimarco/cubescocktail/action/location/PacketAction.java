package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class PacketAction extends LocationAction {

    private static final String CONDITION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMyNzEwNTI3MTllZjY0MDc5ZWU4YzE0OTg5NTEyMzhhNzRkYWM0YzI3Yjk1NjQwZGI2ZmJkZGMyZDZiNWI2ZSJ9fX0=";

    @Serializable(headTexture = CONDITION_HEAD, description = "gui.action.location.condition")
    protected Condition condition;

    public PacketAction(boolean target, Condition condition) {
        super(target);
        this.condition = condition;
    }

    public PacketAction(Map<String, Object> map) {
        super(map);
        this.condition = (Condition) map.getOrDefault("condition", new AlwaysTrueCondition());
    }

    protected Collection<Player> selectedPlayers(Location l, ActionSource source) {
        return selectedPlayers(l, source, Bukkit.getViewDistance() << 4);
    }

    protected Collection<Player> selectedPlayers(Location l, ActionSource source, int range) {
        Predicate<LivingEntity> select = e -> condition.checkCondition(new EntityTarget(e), source);
        BoundingBox box = new BoundingBox(l.getX() - range, l.getY() - 64, l.getZ() - range,
                l.getX() + range, l.getY() + 64, l.getZ() + range);

        World world = l.getWorld();

        if (world == null) {
            return Lists.newArrayList();
        }

        return l.getWorld().getPlayers().stream().filter(player -> box.contains(player.getBoundingBox()) &&
                select.test(player)).collect(Collectors.toList());
    }

}
