package com.github.zamponimarco.cubescocktail.placeholder.numeric.item;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lEnchant Level Placeholder", description = "gui.placeholder.double.item.enchant.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTc2MmExNWIwNDY5MmEyZTRiM2ZiMzY2M2JkNGI3ODQzNGRjZTE3MzJiOGViMWM3YTlmN2MwZmJmNmYifX19")
@Getter
@Setter
public class ItemEnchantLevelPlaceholder extends ItemNumericPlaceholder {

    private static final String ENCHANT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTc2MmExNWIwNDY5MmEyZTRiM2ZiMzY2M2JkNGI3ODQzNGRjZTE3MzJiOGViMWM3YTlmN2MwZmJmNmYifX19";

    @Serializable(headTexture = ENCHANT_HEAD, description = "gui.placeholder.double.item.enchant.enchantment",
            fromList = "getEnchants", fromListMapper = "getEnchantsMapper")
    private Enchantment enchantment;

    public ItemEnchantLevelPlaceholder() {
        this(TARGET_DEFAULT, Enchantment.DAMAGE_ALL);
    }

    public ItemEnchantLevelPlaceholder(boolean target, Enchantment enchantment) {
        super(target);
        this.enchantment = enchantment;
    }

    public ItemEnchantLevelPlaceholder(Map<String, Object> map) {
        super(map);
        this.enchantment = Enchantment.getByKey(NamespacedKey.minecraft((String) map.get("enchantment")));
    }

    public static List<Enchantment> getEnchants(ModelPath path) {
        return Lists.newArrayList(Enchantment.values());
    }

    public static Function<Object, ItemStack> getEnchantsMapper() {
        return obj -> {
            Enchantment enchantment = (Enchantment) obj;
            return ItemUtils.getNamedItem(new ItemStack(Material.ENCHANTED_BOOK),
                    MessageUtils.color("&6&l" + enchantment.getKey().getKey()), Lists.newArrayList());
        };
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("enchantment", enchantment.getKey().getKey());
        return map;
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        ItemStack item = getItem(target, source);
        if (item == null) {
            return 0.0;
        }

        if (!item.hasItemMeta()) {
            return 0.0;
        }

        return (double) item.getItemMeta().getEnchantLevel(enchantment);
    }

    @Override
    public String getName() {
        return String.format("%s Item Enchant Level %s", target ? "Target" : "Source", enchantment.getKey().getKey());
    }

    @Override
    public NumericPlaceholder clone() {
        return new ItemEnchantLevelPlaceholder(target, enchantment);
    }
}
