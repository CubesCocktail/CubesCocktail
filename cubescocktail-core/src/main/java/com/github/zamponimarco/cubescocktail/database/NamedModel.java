package com.github.zamponimarco.cubescocktail.database;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class NamedModel implements Model {

    private static final String NAME_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=";
    @Serializable(headTexture = NAME_HEAD, description = "gui.name")
    @EqualsAndHashCode.Include
    protected String name;
    private String oldName;

    public NamedModel(String name) {
        this.name = name;
        this.oldName = name;
    }

    public NamedModel(Map<String, Object> map) {
        this((String) map.getOrDefault("name", MessageUtils.getRandomString(6)));
    }

    public static NamedModel fromSerializedString(String string) {
        YamlConfiguration config = new YamlConfiguration();
        NamedModel toReturn;
        try {
            config.loadFromString(string);
            toReturn = (NamedModel) config.get("model");
        } catch (ClassCastException | InvalidConfigurationException e) {
            CubesCocktail.getInstance().getLogger().warning("Is this model legacy? Trying to convert files.");
            string = convertOldYamlString(string);
            try {
                config.loadFromString(string);
                toReturn = (NamedModel) config.get("model");
            } catch (InvalidConfigurationException invalidConfigurationException) {
                CubesCocktail.getInstance().getLogger().
                        warning("Conversion did not succeed. Please contact the plugin developer");
                CubesCocktail.getInstance().getLogger().warning(invalidConfigurationException.getMessage());
                invalidConfigurationException.printStackTrace();
                return null;
            }
        }
        return toReturn;
    }

    public String toSerializedString() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("model", this);
        return config.saveToString();
    }

    protected abstract boolean isAlreadyPresent(String name);

    @Override
    public Object beforeModify(Field field, Object value) {
        if ("name".equals(field.getName())) {
            String stringValue = (String) value;
            if (isAlreadyPresent(stringValue)) {
                return stringValue.concat("-copy");
            }
        }
        return null;
    }

    @NotNull
    private static String convertOldYamlString(String string) {
        string = string.replaceAll("com.github.jummes.supremeitem",
                "com.github.zamponimarco.cubescocktail");
        string = string.replaceAll("com.github.jummes.suprememob",
                "com.github.zamponimarco.cubescocktail");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.skill",
                "com.github.zamponimarco.itemdrink.skill");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.item",
                "com.github.zamponimarco.itemdrink.item");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.goal",
                "com.github.zamponimarco.cubescocktail.ai.goal");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.targetted",
                "com.github.zamponimarco.cubescocktail.ai.trgt");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.mob",
                "com.github.zamponimarco.mobdrink.mob");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.spawner",
                "com.github.zamponimarco.mobdrink.spawner");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.savedskill",
                "com.github.zamponimarco.cubescocktail.function");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.action.meta.SkillAction",
                "com.github.zamponimarco.cubescocktail.action.meta.FunctionAction");
        string = string.replaceAll("com.github.zamponimarco.cubescocktail.function.SavedSkill",
                "com.github.zamponimarco.cubescocktail.function.Function");
        string = string.replaceAll("actuator.DamageActuator", "trigger.DamageEntityTrigger");
        string = string.replaceAll("actuator.HitActuator", "trigger.HitEntityTrigger");
        string = string.replaceAll("actuator.DeathActuator", "trigger.EntityDeathTrigger");
        string = string.replaceAll("actuator.InteractActuator", "trigger.EntityInteractedTrigger");
        string = string.replaceAll("actuator.SpawnActuator", "trigger.EntitySpawnTrigger");
        return string;
    }
}
