package com.github.zamponimarco.cubescocktail.loot;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.loot.LootTable;

import java.util.Map;

@Enumerable.Parent(classArray = {SimpleDropTable.class, MinecraftDropTable.class})
@Getter
@Setter
public abstract class DropTable implements Model, LootTable {


    protected static final boolean DROPS_DEFAULT = true;

    @Serializable(headTexture = Textures.DEFAULT_HEAD, description = "gui.loot.default")
    private boolean defaultDropsEnabled;

    public DropTable(boolean defaultDropsEnabled) {
        this.defaultDropsEnabled = defaultDropsEnabled;
    }

    public DropTable(Map<String, Object> map) {
        this.defaultDropsEnabled = (boolean) map.getOrDefault("defaultDropsEnabled", DROPS_DEFAULT);
    }

}
