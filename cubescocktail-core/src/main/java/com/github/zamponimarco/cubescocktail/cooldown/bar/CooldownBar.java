package com.github.zamponimarco.cubescocktail.cooldown.bar;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.cooldown.CooldownInfo;
import com.github.zamponimarco.cubescocktail.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Enumerable.Parent(classArray = {ActionBar.class, BossBar.class, NoBar.class})
public abstract class CooldownBar implements Model, Cloneable {

    public void switchCooldownContext(Player p, Key key, int maxCooldown) {
        switchCooldownContext(p, CubesCocktail.getInstance().getCooldownManager().getCooldownInfo(p, key), maxCooldown);
    }

    public abstract void switchCooldownContext(Player p, CooldownInfo info, int maxCooldown);

    @Override
    public abstract CooldownBar clone();
}
