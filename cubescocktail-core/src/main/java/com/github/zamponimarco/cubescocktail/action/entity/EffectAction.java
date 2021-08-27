package com.github.zamponimarco.cubescocktail.action.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Enumerable.Displayable(name = "&c&lEffect", description = "gui.action.entity.effect.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzJhN2RjYmY3ZWNhNmI2ZjYzODY1OTFkMjM3OTkxY2ExYjg4OGE0ZjBjNzUzZmY5YTMyMDJjZjBlOTIyMjllMyJ9fX0=")
@Enumerable.Child
@Getter
@Setter
public class EffectAction extends EntityAction {

    private static final NumericValue DURATION_DEFAULT = new NumericValue(20);
    private static final NumericValue LEVEL_DEFAULT = new NumericValue(0);
    private static final boolean PARTICLES_DEFAULT = true;
    private static final boolean AMBIENT_DEFAULT = true;
    private static final boolean ICON_DEFAULT = true;
    private static final boolean REMOVE_DEFAULT = false;

    private static final String TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzJhN2RjYmY3ZWNhNmI2ZjYzODY1OTFkMjM3OTkxY2ExYjg4OGE0ZjBjNzUzZmY5YTMyMDJjZjBlOTIyMjllMyJ9fX0=";
    private static final String DURATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE2YWU1YjM0YzRmNzlhNWY5ZWQ2Y2NjMzNiYzk4MWZjNDBhY2YyYmZjZDk1MjI2NjRmZTFjNTI0ZDJlYjAifX19";
    private static final String LEVEL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIxNmVlNDA1OTNjMDk4MWVkMjhmNWJkNjc0ODc5NzgxYzQyNWNlMDg0MWI2ODc0ODFjNGY3MTE4YmI1YzNiMSJ9fX0=";
    private static final String PARTICLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY4NDczNWZjOWM3NjBlOTVlYWYxMGNlYzRmMTBlZGI1ZjM4MjJhNWZmOTU1MWVlYjUwOTUxMzVkMWZmYTMwMiJ9fX0=";
    private static final String AMBIENT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWY1NzE5MmIxOTRjNjU4YWFhODg4MTY4NDhjYmNlN2M3NDk0NjZhNzkyYjhhN2UxZDNmYWZhNDFjNDRmMzQxMiJ9fX0=";
    private static final String ICON_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU5MWYwNGJiNmQ1MjQ5MTFhZGRhNzc1YWYyNDRmODZhOTVkYjE4N2UzMWI4YTNiMTAzYWQ4MGZjNWIyMjU2MCJ9fX0=";
    private static final String REMOVE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThkMTVmZWNiNWY2ZGVmOTMxN2M5MzNiMzQ0ZTZiMTExZjU0NjE5ZGNlYmRmODJhNDY4YWVkNTU0ZTE3ZSJ9fX0=";

    @Serializable(headTexture = TYPE_HEAD, stringValue = true, description = "gui.action.entity.effect.type", fromList = "getPotionEffects", fromListMapper = "potionEffectsMapper")
    private PotionEffectType type;

    @Serializable(displayItem = "getDurationItem", description = "gui.action.entity.effect.duration", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, scale = 1)
    @Serializable.Optional(defaultValue = "DURATION_DEFAULT")
    private NumericValue duration;

    @Serializable(displayItem = "getLevelItem", description = "gui.action.entity.effect.level", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, scale = 1)
    @Serializable.Optional(defaultValue = "LEVEL_DEFAULT")
    private NumericValue level;

    @Serializable(displayItem = "getParticlesItem", description = "gui.action.entity.effect.particles")
    @Serializable.Optional(defaultValue = "PARTICLES_DEFAULT")
    private boolean particles;

    @Serializable(displayItem = "getAmbientItem", description = "gui.action.entity.effect.ambient")
    @Serializable.Optional(defaultValue = "AMBIENT_DEFAULT")
    private boolean ambient;

    @Serializable(displayItem = "getIconItem", description = "gui.action.entity.effect.icon")
    @Serializable.Optional(defaultValue = "ICON_DEFAULT")
    private boolean icon;

    @Serializable(headTexture = REMOVE_HEAD, description = "gui.action.entity.effect.remove")
    @Serializable.Optional(defaultValue = "REMOVE_DEFAULT")
    private boolean remove;

    public EffectAction() {
        this(TARGET_DEFAULT, PotionEffectType.INCREASE_DAMAGE, DURATION_DEFAULT.clone(), LEVEL_DEFAULT.clone(),
                PARTICLES_DEFAULT, AMBIENT_DEFAULT, ICON_DEFAULT, REMOVE_DEFAULT);
    }

    public EffectAction(boolean target, PotionEffectType type, NumericValue duration, NumericValue level, boolean particles,
                        boolean ambient, boolean icon, boolean remove) {
        super(target);
        this.type = type;
        this.duration = duration;
        this.level = level;
        this.particles = particles;
        this.ambient = ambient;
        this.icon = icon;
        this.remove = remove;
    }

    public EffectAction(Map<String, Object> map) {
        super(map);
        this.type = PotionEffectType.getByName(((String) map.getOrDefault("type", "INCREASE_DAMAGE"))
                .replaceAll("[\\[\\]\\d, ]|PotionEffectType", ""));
        this.particles = (boolean) map.getOrDefault("particles", PARTICLES_DEFAULT);
        this.ambient = (boolean) map.getOrDefault("ambient", AMBIENT_DEFAULT);
        this.icon = (boolean) map.getOrDefault("icon", ICON_DEFAULT);
        this.duration = (NumericValue) map.getOrDefault("duration", DURATION_DEFAULT.clone());
        this.level = (NumericValue) map.getOrDefault("level", LEVEL_DEFAULT.clone());
        this.remove = (boolean) map.getOrDefault("remove", REMOVE_DEFAULT);
    }

    public static List<Object> getPotionEffects(ModelPath<?> path) {
        return Lists.newArrayList(PotionEffectType.values());
    }

    public static Function<Object, ItemStack> potionEffectsMapper() {
        return obj -> {
            PotionEffectType type = (PotionEffectType) obj;
            return ItemUtils.getNamedItem(new ItemStack(Material.POTION), MessageUtils.color(type.getName()), new ArrayList<>());
        };
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        LivingEntity e = getEntity(target, source);

        if (e == null) {
            return ActionResult.FAILURE;
        }

        if (remove)
            e.removePotionEffect(type);
        else
            e.addPotionEffect(new PotionEffect(type,
                    duration.getRealValue(target, source).intValue(), level.getRealValue(target, source).intValue(),
                    ambient, particles, icon));

        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new EffectAction(target, PotionEffectType.getByName(type.getName()), duration.clone(), level.clone(),
                particles, ambient, icon, remove);
    }

    @Override
    public String getName() {
        return "&6&lEffect: &c" + WordUtils.capitalize(type.toString());
    }

    public ItemStack getDurationItem() {
        if (!remove) {
            return Libs.getWrapper().skullFromValue(DURATION_HEAD);
        }
        return null;
    }

    public ItemStack getParticlesItem() {
        if (!remove) {
            return Libs.getWrapper().skullFromValue(PARTICLE_HEAD);
        }
        return null;
    }

    public ItemStack getLevelItem() {
        if (!remove) {
            return Libs.getWrapper().skullFromValue(LEVEL_HEAD);
        }
        return null;
    }

    public ItemStack getIconItem() {
        if (!remove) {
            return Libs.getWrapper().skullFromValue(ICON_HEAD);
        }
        return null;
    }

    public ItemStack getAmbientItem() {
        if (!remove) {
            return Libs.getWrapper().skullFromValue(AMBIENT_HEAD);
        }
        return null;
    }

}
