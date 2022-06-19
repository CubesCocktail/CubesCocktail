package com.github.zamponimarco.cubescocktail.manager;

import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.cooldown.CooldownInfo;
import com.github.zamponimarco.cubescocktail.cooldown.bar.CooldownBar;
import com.github.zamponimarco.cubescocktail.key.Key;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CooldownManager {

    private final Map<LivingEntity, List<CooldownInfo>> cooldowns;

    public CooldownManager() {
        cooldowns = new HashMap<>();
        Bukkit.getScheduler().runTaskTimer(CubesCocktail.getInstance(), getCooldownProcess(), 0, 1);
    }

    private Runnable getCooldownProcess() {
        return () -> {
            Iterator<Map.Entry<LivingEntity, List<CooldownInfo>>> i = cooldowns.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<LivingEntity, List<CooldownInfo>> entry = i.next();
                entry.getValue().forEach(info -> info.setRemainingCooldown(info.getRemainingCooldown() - 1));
                entry.getValue().removeIf(info -> info.getRemainingCooldown() == 0);
                if (entry.getValue().isEmpty()) {
                    i.remove();
                }
            }
        };
    }

    public void addCooldown(LivingEntity e, Key key, int cooldown) {
        addCooldown(e, key, cooldown, null);
    }

    public void addCooldown(LivingEntity e, Key key, int cooldown, CooldownBar bar) {
        CooldownInfo presentInfo = getCooldownInfo(e, key);
        if (presentInfo != null) {
            presentInfo.setRemainingCooldown(cooldown);
        } else {
            CooldownInfo info = new CooldownInfo(key, cooldown);
            cooldowns.computeIfAbsent(e, k -> Lists.newArrayList());
            cooldowns.get(e).add(info);
            if (bar != null && e instanceof Player) {
                bar.switchCooldownContext((Player) e, info, cooldown);
            }
        }
    }

    public void removeCooldown(LivingEntity e, Key key) {
        CooldownInfo info = getCooldownInfo(e, key);
        if (cooldowns.containsKey(e) && info != null) {
            cooldowns.get(e).remove(info);
        }
    }

    public CooldownInfo getCooldownInfo(LivingEntity e, Key key) {
        if (cooldowns.containsKey(e)) {
            return cooldowns.get(e).stream().filter(info -> info.getKey().equals(key)).
                    findFirst().orElse(null);
        }
        return null;
    }

    public int getCooldown(LivingEntity e, Key key) {
        CooldownInfo info = getCooldownInfo(e, key);
        return info == null ? 0 : info.getRemainingCooldown();
    }

}
