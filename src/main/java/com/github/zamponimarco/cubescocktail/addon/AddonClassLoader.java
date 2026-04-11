package com.github.zamponimarco.cubescocktail.addon;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.jar.JarFile;

@Getter
public class AddonClassLoader extends URLClassLoader {

    private final AddonDescription description;
    private final Addon addon;
    private final JarFile jar;

    public AddonClassLoader(ClassLoader parent, File file, AddonDescription description)
            throws Exception {
        super(new URL[]{file.toURI().toURL()}, parent);
        this.description = description;
        this.jar = new JarFile(file);
        this.addon = Class.forName(description.getMain(), true, this).asSubclass(Addon.class).
                getConstructor().newInstance();
    }

    @Nullable
    @Override
    public URL getResource(String name) {
        return findResource(name);
    }

    @SneakyThrows
    @Override
    public InputStream getResourceAsStream(String name) {
        return Objects.requireNonNull(getResource(name)).openConnection().getInputStream();
    }
}
