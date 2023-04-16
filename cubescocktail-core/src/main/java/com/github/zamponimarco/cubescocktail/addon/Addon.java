package com.github.zamponimarco.cubescocktail.addon;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

@Getter
@Setter
public abstract class Addon {

    private AddonClassLoader loader;
    private File dataFolder;
    private AddonDescription description;

    public static void init(Addon addon, File dataFolder, AddonDescription description, AddonClassLoader loader) {
        addon.setDataFolder(dataFolder);
        addon.setDescription(description);
        addon.setLoader(loader);
        addon.onEnable();
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public <T> T sendMessageToAddon(AddonMessage<T> message) {
        return null;
    }

    public abstract void renameFunction(String oldName, String newName);

    @SneakyThrows
    public void saveResource(String fileName) {
        fileName = fileName.replace('\\', '/');
        InputStream in = this.getResource(fileName);
        File outputFile = new File(this.dataFolder, fileName);
        OutputStream os = new FileOutputStream(outputFile);
        byte[] buf = new byte[1024];

        int len;
        while ((len = Objects.requireNonNull(in).read(buf)) > 0) {
            os.write(buf, 0, len);
        }

        os.close();
        in.close();
    }

    @SneakyThrows
    @Nullable
    public InputStream getResource(@NotNull String filename) {
        return loader.getResourceAsStream(filename);
    }
}
