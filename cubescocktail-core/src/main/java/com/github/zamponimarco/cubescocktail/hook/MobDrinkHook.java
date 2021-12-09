package com.github.zamponimarco.cubescocktail.hook;

import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.addon.Addon;
import com.github.zamponimarco.cubescocktail.addon.AddonMessage;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import scala.Int;

import java.util.List;

public class MobDrinkHook implements ExternalHook {

    private Addon mobDrink;

    public MobDrinkHook() {
        this.mobDrink = CubesCocktail.getInstance().getAddonManager().getAddon("MobDrink");
    }

    @Override
    public boolean isEnabled() {
        return this.mobDrink != null;
    }

    public List<String> getMobs() {
        AddonMessage<List<String>> message = new AddonMessage<>("getMobs", Lists.newArrayList());
        return mobDrink.sendMessageToAddon(message);
    }

    public Entity spawn(Location location, String name, int level, ActionSource source) {
        AddonMessage<Entity> message = new AddonMessage<>("spawn", Lists.newArrayList(location, name, level, source));
        return mobDrink.sendMessageToAddon(message);
    }

    public EntityType getType(String name) {
        AddonMessage<EntityType> message = new AddonMessage<>("getType", Lists.newArrayList(name));
        return mobDrink.sendMessageToAddon(message);
    }

    public ItemStack getGUIItem(String name) {
        AddonMessage<ItemStack> message = new AddonMessage<>("getGUIItem", Lists.newArrayList(name));
        return mobDrink.sendMessageToAddon(message);
    }

    public Integer getLevel(Entity entity) {
        AddonMessage<Integer> message = new AddonMessage<>("getLevel", Lists.newArrayList(entity));
        return mobDrink.sendMessageToAddon(message);
    }
}
