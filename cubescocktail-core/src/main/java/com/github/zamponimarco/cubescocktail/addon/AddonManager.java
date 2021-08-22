package com.github.zamponimarco.cubescocktail.addon;

import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.InvalidDescriptionException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

@Getter
public class AddonManager {

    private final File addonsFolder = new File(CubesCocktail.getInstance().getDataFolder(), "addons");

    private List<AddonClassLoader> addonClassLoaders;

    public AddonManager() {
        this.addonClassLoaders = Lists.newArrayList();
        loadAddons();
    }

    public void loadAddons() {
        if (!addonsFolder.exists() && !addonsFolder.mkdirs()) {
            return;
        }

        File[] addons = addonsFolder.listFiles((file, name) -> name.endsWith(".jar"));
        if (addons == null) {
            return;
        }

        Arrays.stream(addons).forEach(file -> this.addonClassLoaders.add(loadAddon(file)));
    }

    @SneakyThrows
    public AddonClassLoader loadAddon(File file) {
        Validate.notNull(file, "File cannot be null");
        JarFile jar = new JarFile(file);
        JarEntry entry = jar.getJarEntry("addon.yml");
        if (entry == null) {
            throw new InvalidDescriptionException(new FileNotFoundException("Jar does not contain plugin.yml"));
        }

        InputStream stream = jar.getInputStream(entry);
        AddonDescription description = new AddonDescription(new InputStreamReader(stream));
        AddonClassLoader loader = new AddonClassLoader(getClass().getClassLoader(), file, description);
        Addon newAddon = loader.getAddon();
        File dataFolder = new File(addonsFolder, description.getName());
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        Addon.init(newAddon, dataFolder, description, loader);
        return loader;
    }

    public void unloadAddons() {
        addonClassLoaders.forEach(addonClassLoader -> addonClassLoader.getAddon().onDisable());
        addonClassLoaders.forEach(addonClassLoader -> {
            try {
                addonClassLoader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addonClassLoaders.clear();
    }

    public List<Addon> getLoadedAddons() {
        return addonClassLoaders.stream().map(AddonClassLoader::getAddon).collect(Collectors.toList());
    }
}
