package com.github.zamponimarco.cubescocktail.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MapperUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lGeneric Entity", description = "gui.entity.generic-entity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNjOGFhM2ZkZTI5NWZhOWY5YzI3ZjczNGJkYmFiMTFkMzNhMmU0M2U4NTVhY2NkNzQ2NTM1MjM3NzQxM2IifX19")
@Setter
@Getter
public class GenericEntity extends Entity {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";

    @Serializable(headTexture = HEAD, stringValue = true, fromList = "getSpawnableEntities", fromListMapper = "spawnableEntitiesMapper", description = "gui.entity.generic-entity.type")
    private EntityType type;

    public GenericEntity() {
        this(EntityType.ARROW);
    }

    public GenericEntity(Map<String, Object> map) {
        super(map);
        this.type = EntityType.valueOf((String) map.get("type"));
    }

    public static List<Object> getSpawnableEntities(ModelPath<?> path) {
        return Arrays.stream(EntityType.values()).filter(EntityType::isSpawnable).collect(Collectors.toList());
    }

    public static Function<Object, ItemStack> spawnableEntitiesMapper() {
        return MapperUtils.getEntityTypeMapper();
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location l, ActionTarget target, ActionSource source) {
        return l.getWorld().spawn(l, type.getEntityClass());
    }

    @Override
    public Entity clone() {
        return new GenericEntity(type);
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(new ItemStack(Material.BAT_SPAWN_EGG), MessageUtils.color("Entity"), Lists.newArrayList());
    }
}
