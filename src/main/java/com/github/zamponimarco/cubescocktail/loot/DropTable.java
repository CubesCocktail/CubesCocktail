package com.github.zamponimarco.cubescocktail.loot;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.loot.LootTable;

import java.util.Map;

@Enumerable.Parent(classArray = {SimpleDropTable.class, MinecraftDropTable.class})
@Getter
@Setter
public abstract class DropTable implements Model, LootTable {

    private static final String DEFAULT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODllNzAxNjIxNDNjN2NhYTIwZTMwM2VlYTMxNGE5YWVkNWRiOWNjNjg0MzVlNzgzYjNjNTlhZjQzYmY0MzYzNSJ9fX0=";

    protected static final boolean DROPS_DEFAULT = true;

    @Serializable(headTexture = DEFAULT_HEAD, description = "gui.loot.default")
    private boolean defaultDropsEnabled;

    public DropTable(boolean defaultDropsEnabled) {
        this.defaultDropsEnabled = defaultDropsEnabled;
    }

    public DropTable(Map<String, Object> map) {
        this.defaultDropsEnabled = (boolean) map.getOrDefault("defaultDropsEnabled", DROPS_DEFAULT);
    }

}
