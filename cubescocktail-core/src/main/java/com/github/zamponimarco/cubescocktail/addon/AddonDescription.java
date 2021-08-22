package com.github.zamponimarco.cubescocktail.addon;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.Reader;

@Getter
@Setter
public class AddonDescription {

    private YamlConfiguration yaml = new YamlConfiguration();

    private String name;
    private String version;
    private String main;

    public AddonDescription(Reader reader) throws IOException, InvalidConfigurationException {
        this.yaml.load(reader);
        this.name = yaml.getString("name");
        this.version = yaml.getString("version");
        this.main = yaml.getString("main");
    }
}