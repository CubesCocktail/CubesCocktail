package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.MapperUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.MaterialValue;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lSet block", description = "gui.action.location.set-block.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ZmOTgxN2Q3NjdkMmVkZTcxODFhMDU3YWEyNmYwOGY3ZWNmNDY1MWRlYzk3ZGU1YjU0ZWVkZTFkZDJiNDJjNyJ9fX0=")
@Getter
@Setter
public class SetBlockAction extends LocationAction {

    private static final boolean ALLOW_MATERIALS_DEFAULT = false;

    private static final String MATERIAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ZmOTgxN2Q3NjdkMmVkZTcxODFhMDU3YWEyNmYwOGY3ZWNmNDY1MWRlYzk3ZGU1YjU0ZWVkZTFkZDJiNDJjNyJ9fX0";
    private static final String EXCLUDED_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZkMjQwMDAwMmFkOWZiYmJkMDA2Njk0MWViNWIxYTM4NGFiOWIwZTQ4YTE3OGVlOTZlNGQxMjlhNTIwODY1NCJ9fX0=";
    private static final String ALLOW_MATERIALS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U2MTdiZWQ4ZTk3ZDQwODc5OTNlYzBjODk4Zjg3NzJjNDUyYjk5ZDhiNGI5YTQ1ZTNlYzQzNDkzMDQwOWVlOSJ9fX0";
    @Serializable(headTexture = ALLOW_MATERIALS_HEAD, description = "gui.action.location.set-block.allow-materials")
    @Serializable.Optional(defaultValue = "NEGATE_DEFAULT")
    protected boolean allowMaterials;
    @Serializable(headTexture = MATERIAL_HEAD, stringValue = true, description = "gui.action.location.set-block.material",
            additionalDescription = {"gui.additional-tooltips.value"})
    private MaterialValue material;
    @Serializable(headTexture = EXCLUDED_HEAD, description = "gui.action.location.set-block.excluded-materials")
    private Set<Material> excludedMaterials;

    public SetBlockAction() {
        this(TARGET_DEFAULT, new MaterialValue(Material.STONE), Sets.newHashSet(Material.AIR), ALLOW_MATERIALS_DEFAULT);
    }

    public SetBlockAction(boolean target, MaterialValue material, Set<Material> excludedMaterials, boolean negate) {
        super(target);
        this.material = material;
        this.excludedMaterials = excludedMaterials;
        this.allowMaterials = negate;
    }

    public SetBlockAction(Map<String, Object> map) {
        super(map);
        this.excludedMaterials = ((List<String>) map.get("excludedMaterials")).stream().map(Material::valueOf).
                collect(Collectors.toSet());
        this.excludedMaterials.removeIf(Objects::isNull);
        this.allowMaterials = (boolean) map.getOrDefault("allowMaterials", ALLOW_MATERIALS_DEFAULT);
        this.material = (MaterialValue) map.get("material");
    }

    public static List<Object> materialList(ModelPath<?> path) {
        return MapperUtils.getMaterialList().stream().filter(m -> ((Material) m).isBlock()).collect(Collectors.toList());
    }

    public static Function<Object, ItemStack> materialListMapper() {
        return MapperUtils.getMaterialMapper();
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("excludedMaterials", excludedMaterials.stream().map(Material::name).collect(Collectors.toList()));
        return map;
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        Location location = getLocation(target, source);

        if (location == null) {
            return ActionResult.FAILURE;
        }

        if (source.getCaster() instanceof Player &&
                CubesCocktail.getInstance().getWorldGuardHook().
                        protectedLocation((Player) source.getCaster(), location)) {
            return ActionResult.FAILURE;
        }

        if (allowMaterials ^ excludedMaterials.contains(location.getBlock().getType())) {
            return ActionResult.FAILURE;
        }

        location.getBlock().setType(material.getRealValue(target, source));
        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new SetBlockAction(target, material.clone(), new HashSet<>(excludedMaterials), allowMaterials);
    }

    @Override
    public String getName() {
        return "&6&lSet Block: &c" + MessageUtils.capitalize(material.getName());
    }
}
