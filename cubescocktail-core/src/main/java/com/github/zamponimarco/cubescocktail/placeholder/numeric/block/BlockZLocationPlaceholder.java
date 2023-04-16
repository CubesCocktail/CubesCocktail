package com.github.zamponimarco.cubescocktail.placeholder.numeric.block;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.placeholder.block.BlockPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.block.LocationBlockPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.NumericPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lBlock Z Placeholder", description = "gui.placeholder.double.block.z.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA1ZjE4ZDQxNmY2OGU5YmQxOWQ1NWRmOWZhNzQyZWRmYmYxYTUyNWM4ZTI5ZjY1OWFlODMzYWYyMTdkNTM1In19fQ")
@Getter
@Setter
public class BlockZLocationPlaceholder extends NumericBlockPlaceholder {

    public BlockZLocationPlaceholder() {
        this(TARGET_DEFAULT, new LocationBlockPlaceholder());
    }

    public BlockZLocationPlaceholder(boolean target, BlockPlaceholder placeholder) {
        super(target, placeholder);
    }

    public BlockZLocationPlaceholder(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Double getDoubleValueFromBlock(Block b) {
        return (double) b.getZ();
    }

    @Override
    public String getName() {
        return String.format("%s Z", placeholder.getName());
    }

    @Override
    public NumericPlaceholder clone() {
        return new BlockZLocationPlaceholder(TARGET_DEFAULT, placeholder.clone());
    }
}
