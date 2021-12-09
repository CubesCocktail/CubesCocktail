package com.github.zamponimarco.cubescocktail.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lMobDrink", description = "gui.entity.supreme.description", condition = "mobDrinkEnabled", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==")
// TODO Everything
public class MobDrinkEntity extends Entity {

    private static final String MOB_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ";
    private static final String LEVEL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjUwNTU2YTg0MTY0MDY5ZGYzYjg5NTkzOWQwYWI1MDhmZmE4ZTE0MDQ3MTA2OTM4YjU1OWY1ODg5ZTViZmJlNCJ9fX0=";

    @Serializable(headTexture = MOB_HEAD, description = "gui.entity.supreme.mob", fromList = "getMobs", fromListMapper = "mobsMapper")
    private String mob;
    @Serializable(headTexture = LEVEL_HEAD, description = "gui.entity.supreme.level", additionalDescription = {"gui.additional-tooltips.value"})
    private NumericValue level;

    public MobDrinkEntity() {
        this("", new NumericValue(1));
    }

    public MobDrinkEntity(String mob, NumericValue level) {
        this.mob = mob;
        this.level = level;
    }

    public MobDrinkEntity(Map<String, Object> map) {
        super(map);
        this.mob = (String) map.getOrDefault("mob", "");
        this.level = (NumericValue) map.getOrDefault("level", new NumericValue(1));
    }

    public static boolean mobDrinkEnabled(ModelPath path) {
        return CubesCocktail.getInstance().getMobDrinkHook().isEnabled();
    }

    public static List<String> getMobs(ModelPath path) {
        return CubesCocktail.getInstance().getMobDrinkHook().getMobs();
    }

    public static Function<Object, ItemStack> mobsMapper() {
        return obj -> {
            String mobStr = (String) obj;
            ItemStack gui = CubesCocktail.getInstance().getMobDrinkHook().getGUIItem(mobStr);
            if (gui == null) {
                return new ItemStack(Material.CARROT);
            }

            return ItemUtils.getNamedItem(gui, gui.getItemMeta().displayName(),
                    Lists.newArrayList());
        };
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location l, ActionTarget target, ActionSource source) {
        if (!CubesCocktail.getInstance().getMobDrinkHook().isEnabled()) {
            return null;
        }

        return CubesCocktail.getInstance().getMobDrinkHook().spawn(l, mob, level.getRealValue(target, source).intValue(),
                source);
    }

    @Override
    public Entity clone() {
        return new MobDrinkEntity(mob, level.clone());
    }

    @Override
    public EntityType getType() {
        EntityType type = CubesCocktail.getInstance().getMobDrinkHook().getType(mob);
        return type != null ? type : EntityType.UNKNOWN;
    }
}
