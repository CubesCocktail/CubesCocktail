package com.github.zamponimarco.cubescocktail.cooldown.bar;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.cooldown.CooldownInfo;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lAction Cooldown Bar", description = "gui.cooldown.bar.action.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjAyYWYzY2EyZDVhMTYwY2ExMTE0MDQ4Yjc5NDc1OTQyNjlhZmUyYjFiNWVjMjU1ZWU3MmI2ODNiNjBiOTliOSJ9fX0")
@Getter
@Setter
public class ActionBar extends CooldownBar {

    protected static final String COOLDOWN_MESSAGE_FORMAT_DEFAULT = "&2Cooldown &6[%bar&6]";
    protected static final String COOLDOWN_MESSAGE_BAR_DEFAULT = "|";
    protected static final int COOLDOWN_MESSAGE_BAR_COUNT_DEFAULT = 30;

    private static final String FORMAT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZlMTk3MmYyY2ZhNGQzMGRjMmYzNGU4ZDIxNTM1OGMwYzU3NDMyYTU1ZjZjMzdhZDkxZTBkZDQ0MTkxYSJ9fX0=";
    private static final String BAR_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjcxYjVjYTNhNjFiZWYyOTE2NWViMTI2NmI0MDVhYzI1OTE1NzJjMTZhNGIzOWNiMzZlZGFmNDZjODZjMDg4In19fQ==";
    private static final String BAR_COUNT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";

    private static final Map<Player, Integer> cooldownMessagesMap = new HashMap<>();

    @Serializable(headTexture = FORMAT_HEAD, description = "gui.skill.cooldown.bar.action.format")
    @Serializable.Optional(defaultValue = "COOLDOWN_MESSAGE_FORMAT_DEFAULT")
    private String cooldownMessageFormat;

    @Serializable(headTexture = BAR_HEAD, description = "gui.skill.cooldown.bar.action.bar")
    @Serializable.Optional(defaultValue = "COOLDOWN_MESSAGE_BAR_DEFAULT")
    private String cooldownMessageBar;

    @Serializable(headTexture = BAR_COUNT_HEAD, description = "gui.skill.cooldown.bar.action.bar-count")
    @Serializable.Optional(defaultValue = "COOLDOWN_MESSAGE_BAR_COUNT_DEFAULT")
    private int cooldownMessageBarCount;

    public ActionBar() {
        this(COOLDOWN_MESSAGE_FORMAT_DEFAULT, COOLDOWN_MESSAGE_BAR_DEFAULT, COOLDOWN_MESSAGE_BAR_COUNT_DEFAULT);
    }

    public ActionBar(String cooldownMessageFormat, String cooldownMessageBar, int cooldownMessageBarCount) {
        this.cooldownMessageFormat = cooldownMessageFormat;
        this.cooldownMessageBar = cooldownMessageBar;
        this.cooldownMessageBarCount = cooldownMessageBarCount;
    }

    public ActionBar(Map<String, Object> map) {
        this.cooldownMessageFormat = (String) map.getOrDefault("cooldownMessageFormat", COOLDOWN_MESSAGE_FORMAT_DEFAULT);
        this.cooldownMessageBar = (String) map.getOrDefault("cooldownMessageBar", COOLDOWN_MESSAGE_BAR_DEFAULT);
        this.cooldownMessageBarCount = (int) map.getOrDefault("cooldownMessageBarCount", COOLDOWN_MESSAGE_BAR_COUNT_DEFAULT);
    }

    public BukkitRunnable sendProgress(Player player, CooldownInfo info, int maxCooldown) {
        return new BukkitRunnable() {
            @Override
            public void run() {

                if (info.getRemainingCooldown() == 0) {
                    this.cancel();
                }

                int progress = Math.max((int) Math.floor((info.getRemainingCooldown() / (double) maxCooldown) *
                        cooldownMessageBarCount), 0);

                String sb = "&a" +
                        repeat(30 - progress) +
                        "&c" +
                        repeat(progress) +
                        "&f";
                player.sendActionBar(MessageUtils.color(cooldownMessageFormat.replaceAll("%bar", sb)));
            }
        };
    }

    private String repeat(int count) {
        return new String(new char[count]).replace("\0", cooldownMessageBar);
    }

    @Override
    public void switchCooldownContext(Player p, CooldownInfo info, int maxCooldown) {
        if (cooldownMessagesMap.containsKey(p)) {
            Bukkit.getScheduler().cancelTask(cooldownMessagesMap.get(p));
        }
        cooldownMessagesMap.put(p, sendProgress(p, info, maxCooldown).
                runTaskTimer(CubesCocktail.getInstance(), 0, 1).getTaskId());
    }

    @Override
    public CooldownBar clone() {
        return new ActionBar(cooldownMessageFormat, cooldownMessageBar, cooldownMessageBarCount);
    }
}
