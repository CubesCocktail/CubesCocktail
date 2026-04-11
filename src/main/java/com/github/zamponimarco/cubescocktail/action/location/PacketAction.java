package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.constant.Textures;
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


    @Serializable(headTexture = Textures.CONDITION_HEAD, description = "gui.action.location.condition")
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
