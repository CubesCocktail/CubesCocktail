package com.github.zamponimarco.cubescocktail.util;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.slot.Slot;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Utils {

    public static MetadataValue getMetadata(List<MetadataValue> value, Object defaultValue) {
        return value.stream().filter(metadataValue -> Objects.equals(metadataValue.getOwningPlugin(),
                CubesCocktail.getInstance())).findFirst().orElse(new FixedMetadataValue(CubesCocktail.getInstance(), defaultValue));
    }

    public static List<ItemStack> getEntityItems(LivingEntity e) {
        return Slot.slots.stream().map(slot -> slot.getItem(e)).collect(Collectors.toList());
    }

    public static void registerSerializable(Class<? extends Model> clazz) {
        ConfigurationSerialization.registerClass(clazz);
        Enumerable.Parent enumerable = clazz.getAnnotation(Enumerable.Parent.class);
        if (enumerable != null) {
            Arrays.stream(enumerable.classArray()).forEach(Utils::registerSerializable);
        }
    }
}
