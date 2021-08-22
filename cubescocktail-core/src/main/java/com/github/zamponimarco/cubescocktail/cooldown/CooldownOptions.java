package com.github.zamponimarco.cubescocktail.cooldown;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.cooldown.bar.ActionBar;
import com.github.zamponimarco.cubescocktail.cooldown.bar.CooldownBar;
import com.github.zamponimarco.cubescocktail.cooldown.bar.NoBar;
import lombok.Getter;

import java.util.Map;

@Getter
public class CooldownOptions implements Model, Cloneable {
    protected static final int COOLDOWN_DEFAULT = 0;
    protected static final String COOLDOWN_MESSAGE_FORMAT_DEFAULT = "&2Cooldown &6[%bar&6]";
    protected static final String COOLDOWN_MESSAGE_BAR_DEFAULT = "|";
    protected static final int COOLDOWN_MESSAGE_BAR_COUNT_DEFAULT = 30;


    protected static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlOGNmZjc1ZjdkNDMzMjYwYWYxZWNiMmY3NzNiNGJjMzgxZDk1MWRlNGUyZWI2NjE0MjM3NzlhNTkwZTcyYiJ9fX0=";
    protected static final String COOLDOWN_MESSAGE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=";
    private static final String BAR_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFmMGM5NWNlYmEzNGM3ZmU2ZDMzNDA0ZmViODdiNDE4NGNjY2UxNDM5Nzg2MjJjMTY0N2ZlYWVkMmI2MzI3NCJ9fX0==";

    @Serializable(headTexture = COOLDOWN_HEAD, description = "gui.cooldown.cooldown")
    @Serializable.Number(minValue = 0, scale = 1)
    @Serializable.Optional(defaultValue = "COOLDOWN_DEFAULT")
    protected int cooldown;

    @Serializable(headTexture = BAR_HEAD, description = "gui.cooldown.bar.description",
            additionalDescription = {"gui.additional-tooltips.recreate"})
    protected CooldownBar bar;

    public CooldownOptions() {
        this(COOLDOWN_DEFAULT, new ActionBar());
    }

    public CooldownOptions(int cooldown, CooldownBar bar) {
        this.cooldown = cooldown;
        this.bar = bar;
    }

    public CooldownOptions(Map<String, Object> map) {
        this.cooldown = (int) map.getOrDefault("cooldown", COOLDOWN_DEFAULT);
        this.bar = (CooldownBar) map.getOrDefault("bar", new NoBar());
    }

    @Override
    public CooldownOptions clone() {
        return new CooldownOptions(cooldown, bar.clone());
    }
}