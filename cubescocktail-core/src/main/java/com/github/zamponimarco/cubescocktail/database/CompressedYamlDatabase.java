package com.github.zamponimarco.cubescocktail.database;

import com.github.jummes.libs.database.Database;
import com.github.zamponimarco.cubescocktail.addon.Addon;
import com.github.zamponimarco.cubescocktail.util.CompressUtils;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class CompressedYamlDatabase<T extends NamedModel> extends Database<T> {

    private static final String FILE_SUFFIX = ".yml";

    private final File dataFile;
    private final List<String> usedNames;
    private YamlConfiguration yamlConfiguration;

    public CompressedYamlDatabase(@NonNull Class<T> classObject, @NonNull JavaPlugin plugin, Map<String, Object> args) {
        super(classObject, plugin, args);
        this.usedNames = new ArrayList<>();
        String name = (String) args.getOrDefault("name", classObject.getSimpleName().toLowerCase());
        String fileName = (String) args.getOrDefault("fileName", name.concat(FILE_SUFFIX));
        Addon addon = (Addon) args.get("addon");
        if (addon == null) {
            this.dataFile = new File(plugin.getDataFolder(), fileName);
            if (!this.dataFile.exists()) {
                plugin.saveResource(fileName, false);
            }
        } else {
            this.dataFile = new File(addon.getDataFolder(), fileName);
            if (!this.dataFile.exists()) {
                addon.saveResource(fileName);
            }
        }
    }

    @Override
    public void openConnection() {
        this.yamlConfiguration = new YamlConfiguration();
    }

    @Override
    public void closeConnection() {
        try {
            yamlConfiguration.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public List<T> loadObjects() {
        loadConfiguration();

        List<T> list = new ArrayList<>();

        try {
            yamlConfiguration.getKeys(false).forEach(key -> {
                        String string = yamlConfiguration.getString(key);
                        T obj = (T) NamedModel.fromSerializedString(new String(CompressUtils.
                                decompress(Base64.getDecoder().decode(string)), Charset.defaultCharset()));
                        if (obj != null) {
                            list.add(obj);
                            saveObject(obj);
                        }
                    }
            );
        } catch (IllegalArgumentException e) {
            legacyTransition(list);
        }

        return list;
    }

    @Deprecated
    private void legacyTransition(List<T> list) {
        yamlConfiguration.getList(classObject.getSimpleName().toLowerCase()).stream().map(obj -> {
            YamlConfiguration config = new YamlConfiguration();
            config.set("model", obj);
            return (T) NamedModel.fromSerializedString(config.saveToString());
        }).forEach(list::add);
        yamlConfiguration.set(classObject.getSimpleName().toLowerCase(), null);
        list.forEach(this::saveObject);
    }

    @SneakyThrows
    @Override
    public void saveObject(@NonNull T t) {
        if (!t.getName().equals(t.getOldName())) {
            yamlConfiguration.set(t.getOldName(), null);
            usedNames.remove(t.getOldName());
        }
        yamlConfiguration.set(t.getName(), new String(Base64.getEncoder().encode(CompressUtils.
                compress(t.toSerializedString().getBytes())), Charset.defaultCharset()));
        yamlConfiguration.save(dataFile);

        usedNames.add(t.getName());
        t.setOldName(t.getName());
    }

    private void validateDeserializedName(@NonNull T t) {
        String newName;
        while (usedNames.contains(t.getName())) {
            newName = t.getName() + "-copy";
            t.setName(newName);
            t.setOldName(newName);
        }
    }

    @SneakyThrows
    @Override
    public void deleteObject(@NonNull T t) {
        usedNames.remove(t.name);
        yamlConfiguration.set(t.name, null);
        yamlConfiguration.save(dataFile);
    }

    @SneakyThrows
    private void loadConfiguration() {
        this.yamlConfiguration.load(dataFile);
    }
}
