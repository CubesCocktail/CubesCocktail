package com.github.zamponimarco.cubescocktail.manager;

import com.github.jummes.libs.model.ModelManager;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.savedplaceholder.SavedPlaceholder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

@Getter
public class SavedPlaceholderManager extends ModelManager<SavedPlaceholder> {

    List<SavedPlaceholder> placeholders;

    public SavedPlaceholderManager(Class<SavedPlaceholder> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin, new HashMap<>());
        this.placeholders = fetchModels();
    }

    public SavedPlaceholder getByName(String name) {
        return placeholders.stream().filter(savedPlaceholder -> savedPlaceholder.getName().equals(name)).findFirst().
                orElse(null);
    }

    public String computePlaceholders(String input, ActionSource source, ActionTarget target) {
        return placeholders.stream().reduce(input, (string, placeholder) -> string.replaceAll(
                "%" + placeholder.getName(), placeholder.getPlaceholder().computePlaceholder(target, source).
                        toString()), (s1, s2) -> s1);
    }
}
